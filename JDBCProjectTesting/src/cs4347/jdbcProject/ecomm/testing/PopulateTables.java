package cs4347.jdbcProject.ecomm.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.entity.Purchase;

public class PopulateTables
{
	private File custFile;
	private File addrFile;
	private File ccardFile;
	private File prodFile;

	private void initialize()
	{
		custFile = new File("csvData/customers.csv");
		addrFile = new File("csvData/addresses.csv");
		ccardFile = new File("csvData/creditCards.csv");
		prodFile = new File("csvData/products.csv");
	}

	public static void main(String args[])
	{
		try {
			DataSource dataSource = DataSourceManager.getDataSource();
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			
			PopulateTables app = new PopulateTables();
			app.initialize();

			Map<Long, Customer> custMap = app.buildCustomers();
			app.addAddress(custMap);
			app.addCreditCards(custMap);
			System.out.println("Finished building customers: " + custMap.size());
			app.insertCustomers(connection, custMap); 
			System.out.println("Finished inserting customers");

			Map<Long, Product> products = app.buildProducts();
			System.out.println("Finished building products: " + products.size());
			app.insertProducts(connection, products);
			System.out.println("Finished inserting products");

			List<Purchase> purchases = app.buildPurchases(custMap.values(), products.values().toArray(new Product[0]));
			System.out.println("Finished building purchases: " + purchases.size());	
			app.insertPurchases(connection, purchases);
			System.out.println("Finished inserting purchases");

			connection.commit();
			System.out.println("Finished Initializing Database");
			connection.close();
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private List<Purchase> buildPurchases(Collection<Customer> customers, Product[] products)
	{
		Random random = new Random();
		List<Purchase> result = new ArrayList<Purchase>();
		for (Customer customer : customers) {
			for (int idx = 0; idx < 10; idx++) {
				Product product = products[random.nextInt(products.length)];
				Purchase purchase = buildPurchase(customer.getId(), product.getId());
				result.add(purchase);
			}
		}
		return result;
	}

	Random rnGen = new Random();

	private Purchase buildPurchase(Long custID, Long prodID)
	{
		Purchase purchase = new Purchase();
		purchase.setCustomerID(custID);
		purchase.setProductID(prodID);

		double purchaseAmount = rnGen.nextDouble() * 100.0;
		purchase.setPurchaseAmount(purchaseAmount);

		Calendar cal = GregorianCalendar.getInstance();
		int dateOffset = rnGen.nextInt(10 * 365);
		cal.add(Calendar.DAY_OF_YEAR, dateOffset);
		purchase.setPurchaseDate(new java.sql.Date(cal.getTimeInMillis()));

		return purchase;
	}

	private void addCreditCards(Map<Long, Customer> custMap) throws Exception
	{
		FileReader fr = new FileReader(ccardFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object items[] = parseCreditCard(line);
			Customer cust = custMap.get(items[0]);
			cust.setCreditCard((CreditCard) items[1]);
		}
		br.close();
	}

	private void addAddress(Map<Long, Customer> custMap) throws Exception
	{
		FileReader fr = new FileReader(addrFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object items[] = parseAddress(line);
			Customer cust = custMap.get(items[0]);
			cust.setAddress((Address) items[1]);
		}
		br.close();
	}

	private Map<Long, Product> buildProducts() throws Exception
	{
		Map<Long, Product> prodMap = new HashMap<Long, Product>();
		FileReader fr = new FileReader(prodFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object item[] = parseProduct(line);
			prodMap.put((Long) item[0], (Product) item[1]);
		}
		br.close();
		return prodMap;
	}

	private Map<Long, Customer> buildCustomers() throws Exception
	{
		Map<Long, Customer> custMap = new HashMap<Long, Customer>();
		FileReader fr = new FileReader(custFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object item[] = parseCustomer(line);
			custMap.put((Long) item[0], (Customer) item[1]);
		}
		br.close();
		return custMap;
	}

	private void insertCustomers(Connection connection, Map<Long, Customer> custMap) throws Exception
	{
		for (Customer customer : custMap.values()) {
			CustomerDAO custDAO = new CustomerDaoImpl();
			custDAO.create(connection, customer);

			AddressDAO addrDAO = new AddressDaoImpl();
			Address addr = customer.getAddress();
			addrDAO.create(connection, addr, customer.getId());

			CreditCardDAO ccDAO = new CreditCardDaoImpl();
			CreditCard ccard = customer.getCreditCard();
			ccDAO.create(connection, ccard, customer.getId());
		}
	}

	private void insertProducts(Connection connection, Map<Long, Product> prodMap) throws Exception
	{
		for (Product product : prodMap.values()) {
			ProductDAO prodDAO = new ProductDaoImpl();
			prodDAO.create(connection, product);
		}
	}

	private void insertPurchases(Connection connection, List<Purchase> purchases) throws Exception
	{
		PurchaseDAO purDAO = new PurchaseDaoImpl();
		for (Purchase purchase : purchases) {
			purDAO.create(connection, purchase);
		}
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Object[] parseCustomer(String line) throws ParseException
	{
		StringTokenizer st = new StringTokenizer(line, ",");
		Customer customer = new Customer();
		Long id = Long.parseLong(st.nextToken());
		customer.setFirstName(st.nextToken());
		customer.setLastName(st.nextToken());
		Character gender = ((String) st.nextToken()).charAt(0);
		customer.setGender(gender);
		Date dob = new java.sql.Date(sdf.parse(st.nextToken()).getTime());
		customer.setDob(dob);
		customer.setEmail(st.nextToken());

		Object[] result = { id, customer };
		return result;
	}

	private Object[] parseProduct(String line) throws ParseException
	{
		StringTokenizer st = new StringTokenizer(line, ",");

		Product product = new Product();
		Long id = Long.parseLong(st.nextToken());
		product.setProdName(st.nextToken());
		product.setProdDescription(st.nextToken());
		product.setProdCategory(Integer.parseInt(st.nextToken()));
		product.setProdUPC(st.nextToken());

		Object[] result = { id, product };
		return result;
	}

	private Object[] parseAddress(String line)
	{
		StringTokenizer st = new StringTokenizer(line, ",");

		Address address = new Address();
		address.setAddress1(st.nextToken());
		address.setCity(st.nextToken());
		address.setState(st.nextToken());
		address.setZipcode(st.nextToken());

		Long id = Long.parseLong(st.nextToken());
		return new Object[] { id, address };
	}

	private Object[] parseCreditCard(String line)
	{
		StringTokenizer st = new StringTokenizer(line, ",");

		CreditCard ccard = new CreditCard();
		ccard.setName(st.nextToken());
		ccard.setCcNumber(st.nextToken());
		ccard.setExpDate(st.nextToken());
		ccard.setSecurityCode(st.nextToken());

		Long id = Long.parseLong(st.nextToken());
		return new Object[] { id, ccard };
	}

}
