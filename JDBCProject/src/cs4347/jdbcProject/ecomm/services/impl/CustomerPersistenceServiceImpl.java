package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.services.CustomerPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService
{
	private DataSource dataSource;

	public CustomerPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * This method provided as an example of transaction support across multiple inserts.
	 * 
	 * Persists a new Customer instance by inserting new Customer, Address, 
	 * and CreditCard instances. Notice the transactional nature of this 
	 * method which inludes turning off autocommit at the start of the 
	 * process, and rolling back the transaction if an exception 
	 * is caught. 
	 */
	@Override
	public Customer create(Customer customer) throws SQLException, DAOException
	{
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Customer cust = customerDAO.create(connection, customer);
			Long custID = cust.getId();

			if (cust.getAddress() == null) {
				throw new DAOException("Customers must include an Address instance.");
			}
			Address address = cust.getAddress();
			addressDAO.create(connection, address, custID);

			if (cust.getCreditCard() == null) {
				throw new DAOException("Customers must include an CreditCard instance.");
			}
			CreditCard creditCard = cust.getCreditCard();
			creditCardDAO.create(connection, creditCard, custID);

			connection.commit();
			return cust;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public Customer retrieve(Long id) throws SQLException, DAOException {
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			
			if (id <= 0) {
				throw new DAOException("Id out of bounds: " + id);
			}
			Customer cust = customerDAO.retrieve(connection, id);
			Long custID = cust.getId();

			if (cust.getId() == null) {
				throw new DAOException("Customers must include a proper ID.");
			}
			
			Address address = addressDAO.retrieveForCustomerID(connection, custID);
			cust.setAddress(address);
			
			CreditCard creditCard = creditCardDAO.retrieveForCustomerID(connection, custID);
			cust.setCreditCard(creditCard);
			connection.commit();
			return cust;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public int update(Customer customer) throws SQLException, DAOException {
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);

			if (customer.getId() == null) {
				throw new DAOException("Invalid null customer ID");
			}
			if (customer.getAddress() == null) {
				throw new DAOException("Customers must include an Addres instance.");
			}
			if (customer.getCreditCard() == null) {
				throw new DAOException("Customers must include an CreditCard instance.");
			}
			long customerID = customer.getId();
			addressDAO.deleteForCustomerID(connection, customerID);
			creditCardDAO.deleteForCustomerID(connection, customerID);
			
			addressDAO.create(connection, customer.getAddress(), customerID);
			creditCardDAO.create(connection, customer.getCreditCard(), customerID);
			int numRows = customerDAO.update(connection, customer);
			
			connection.commit();
			return numRows;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		CustomerDAO customerDAO = new CustomerDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			if (id <= 0) {
				throw new DAOException("Id out of bounds: " + id);
			}
			
			int numRows = customerDAO.delete(connection, id);
			
			connection.commit();
			return numRows;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public List<Customer> retrieveByZipCode(String zipCode) throws SQLException, DAOException {
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			
			if (!zipCode.matches("\\d{5}-\\d{4}")) {
				throw new DAOException("Invalid zipcode format " + zipCode);
			}
			
			List<Customer> res = customerDAO.retrieveByZipCode(connection, zipCode);
			for (Customer cust : res) {
				CreditCard cc = creditCardDAO.retrieveForCustomerID(connection, cust.getId());
				Address addr = addressDAO.retrieveForCustomerID(connection, cust.getId());
				cust.setAddress(addr);
				cust.setCreditCard(cc);
			}
			connection.commit();
			return res;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		
	}

	@Override
	public List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException {
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			
			
			if (startDate.after(endDate)) {
				throw new DAOException("Invalid Dates, Start date must be before end date");
			}
			
			List<Customer> res = customerDAO.retrieveByDOB(connection, startDate, endDate);
			for (Customer cust : res) {
				CreditCard cc = creditCardDAO.retrieveForCustomerID(connection, cust.getId());
				Address addr = addressDAO.retrieveForCustomerID(connection, cust.getId());
				cust.setAddress(addr);
				cust.setCreditCard(cc);
			}
			connection.commit();
			return res;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}
