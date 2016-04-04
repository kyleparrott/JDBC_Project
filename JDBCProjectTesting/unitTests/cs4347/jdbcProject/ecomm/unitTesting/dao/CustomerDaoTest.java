package cs4347.jdbcProject.ecomm.unitTesting.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoTest
{
	@Test
	public void testCreate() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		
		Customer cust = buildCustomer();
		CustomerDAO dao = new CustomerDaoImpl();
		
		Customer cust2 = dao.create(connection, cust);
		assertNotNull(cust2);
		assertNotNull(cust2.getId());
		System.out.println("Generated Key: " + cust2.getId());

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	/**
	 * Test a failed creation due to attempting to insert a 
	 * Customer with a non=null ID. Expects to catch DAOException. 
	 */
	@Test (expected=DAOException.class)
	public void testCreateFailed() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);

		CustomerDAO dao = new CustomerDaoImpl();

		Customer cust = buildCustomer();
		cust.setId(System.currentTimeMillis()); // This will cause the create to fail
		dao.create(connection, cust);

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	//@Test
	// TODO Update the DAO interfaces to include count() methods.
	public void testCountCustomers() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		int count = countCustomer(connection);
		connection.close();
		assertTrue(count > 0);
	}
	
	private int countCustomer(Connection connection) throws Exception
	{
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from customer");
		int count = 0;
		if(rs.next()) {
			count = rs.getInt(1); 
		}
		statement.close();
		return count;
	}
	
	//@Test
	// TODO Update the DAO interfaces to include count() methods.
	public void testCountAddress() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		int custCount = countCustomer(connection);
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from address");
		int count = 0;
		if(rs.next()) {
			count = rs.getInt(1); 
		}
		statement.close();
		connection.close();
		assertEquals(custCount, count);
	}
	
	//@Test
	// TODO Update the DAO interfaces to include count() methods.
	public void testCountCreditCard() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		int custCount = countCustomer(connection);
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) from CREDIT_CARD");
		int count = 0;
		if(rs.next()) {
			count = rs.getInt(1); 
		}
		statement.close();
		connection.close();
		assertEquals(custCount, count);
	}
	
	@Test
	public void testRetrieve() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		Customer cust = buildCustomer();
		CustomerDAO dao = new CustomerDaoImpl();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		
		Customer cust2 = dao.create(connection, cust);
		Long id = cust2.getId();
		
		Customer cust3 = dao.retrieve(connection, id);
		assertNotNull(cust3);
		assertEquals(cust2.getId(), cust3.getId());
		assertEquals(cust2.getFirstName(), cust3.getFirstName());
		assertEquals(cust2.getLastName(), cust3.getLastName());
		assertEquals(cust2.getGender(), cust3.getGender());
		assertEquals(cust2.getEmail(), cust3.getEmail());

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	@Test
	public void testRetrieveFailed() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		CustomerDAO dao = new CustomerDaoImpl();

		Long id = System.currentTimeMillis();
		Customer cust3 = dao.retrieve(connection, id);
		assertNull(cust3);
		connection.close();
	}
	
	@Test
	public void testUpdate() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);

		Customer cust = buildCustomer();
		CustomerDAO dao = new CustomerDaoImpl();
		
		Customer cust2 = dao.create(connection, cust);
		Long id = cust2.getId();
		
		String newEmail = "fred@gmail" + System.currentTimeMillis();
		cust2.setEmail(newEmail);
		int rows = dao.update(connection, cust2);
		assertEquals(1, rows);
		
		Customer cust3 = dao.retrieve(connection, id);
		assertEquals(newEmail, cust3.getEmail());

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	@Test
	public void testDelete() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);

		Customer cust = buildCustomer();
		CustomerDAO dao = new CustomerDaoImpl();
		
		Customer cust2 = dao.create(connection, cust);
		Long id = cust2.getId();
		
		int rows = dao.delete(connection, id);
		assertEquals(1, rows);

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	/**
	 * Tests deleting a customer and associated purchases. 
	 * Note: This test assumes that cascade delete was set on the foreign key.
	 */
	public void testDelete2() throws Exception
	{
		Long custID = 609l; // Need customer ID with attached Purchases

		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);

		CustomerDAO custDao = new CustomerDaoImpl();
		PurchaseDAO purcDao = new PurchaseDaoImpl();
		
		Customer cust1 = custDao.retrieve(connection, custID);
		assertNotNull(cust1);
		
		List<Purchase> purchases = purcDao.retrieveForCustomerID(connection, custID);
		assertTrue(purchases.size() > 1);
		
		int rows = custDao.delete(connection, custID);
		assertEquals(1, rows);

		cust1 = custDao.retrieve(connection, custID);
		assertNull(cust1);
		
		// Assumes cascade delete
		purchases = purcDao.retrieveForCustomerID(connection, custID);
		assertTrue(purchases.size() == 0);

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	@Test
	public void testRetrieveByZipCode() throws Exception 
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		CustomerDAO custDao = new CustomerDaoImpl();
		
		List<Customer> custs = custDao.retrieveByZipCode(connection, "76953-7323"); // Need a customer with this address
		assertTrue(custs.size() > 0);
		connection.close();
	}
	
	//List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException;
	@Test
	public void testRetrieveByDOB() throws Exception 
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		CustomerDAO custDao = new CustomerDaoImpl();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = new Date(sdf.parse("1970-01-01").getTime());
		Date end = new Date(sdf.parse("1990-01-01").getTime());
		
		List<Customer> custs = custDao.retrieveByDOB(connection, start, end); // Need a customer with this address
		System.out.println("ssss " + custs.size());
		assertTrue(custs.size() > 0);
		connection.close();
	}
	

	private Customer buildCustomer() {
	
		Customer result = new Customer();
		result.setFirstName("fred");
		result.setLastName("flintstone");
		result.setDob(new java.sql.Date(System.currentTimeMillis()));
		result.setEmail("fred@gmail" + System.currentTimeMillis());
		result.setGender('M');
		return result;
	}
}
