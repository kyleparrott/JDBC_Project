package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.text.DefaultEditorKit.CutAction;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchasePersistenceService;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchasePersistenceServiceImpl implements PurchasePersistenceService
{
	private DataSource dataSource;

	public PurchasePersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Purchase create(Purchase purchase) throws SQLException, DAOException {
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Purchase purch = purchaseDAO.create(connection, purchase);
			Long purchId = purch.getId();
			
			if(purchId == null) {
				throw new DAOException("An error occurred assigning an ID during the insert");
			}
			
			if (purch.getCustomerID() == null) { //Dont know if these if's are needed.
				throw new DAOException("Purchases must have an associated CustomerId.");
			}
			
			if (purch.getProductID() == null) {
				throw new DAOException("Purchases must have an associated ProductId.");
			}
			connection.commit();
			return purch;
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
	public Purchase retrieve(Long id) throws SQLException, DAOException {
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
			Purchase purch = purchaseDAO.retrieve(connection, id);
			
			if (purch == null) {
				System.out.println("null purchase");
				return null;
			} 
			connection.commit();
			return purch;
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
	public int update(Purchase purchase) throws SQLException, DAOException {
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
			if (purchase.getCustomerID() == null) {
				throw new DAOException("each purchase must have an associated CustomerID");
			}
			
			if (purchase.getProductID() == null) {
				throw new DAOException("Each purchase must have an associated Product ID");
			}
			
			if (purchase.getId() <= 0){
				throw new DAOException("Invalid Value for id: " + purchase.getId());
			}
			
			int numRows = purchaseDAO.update(connection, purchase);
			
			if (numRows <= 0) {
				throw new DAOException("There was an error with the insert, update failed");
			}
			
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
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
		
			if (id <= 0){
				throw new DAOException("Invalid Value for id: " + id);
			}
			
			int numRows = purchaseDAO.delete(connection, id);
			
			if (numRows <= 0) {
				throw new DAOException("There was an error with the deletion, update failed");
			}
			
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
	public List<Purchase> retrieveForCustomerID(Long customerID) throws SQLException, DAOException {
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
		
			if (customerID <= 0){
				throw new DAOException("Invalid Value for customerID: " + customerID);
			}
			
			List<Purchase> result = purchaseDAO.retrieveForCustomerID(connection, customerID);
			
			if (result.size() == 0) {
				throw new DAOException("There was an error with the deletion, update failed");
			}
			
			connection.commit();
			return result;
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
	public PurchaseSummary retrievePurchaseSummary(Long customerID) throws SQLException, DAOException {
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
		
			if (customerID <= 0){
				throw new DAOException("Invalid Value for customerID: " + customerID);
			}
			
			PurchaseSummary psum = purchaseDAO.retrievePurchaseSummary(connection, customerID);
			
			connection.commit();
			return psum;
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
	public List<Purchase> retrieveForProductID(Long productID) throws SQLException, DAOException {
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
		
			if (productID <= 0){
				throw new DAOException("Invalid Value for customerID: " + productID);
			}
			
			List<Purchase> result = purchaseDAO.retrieveForCustomerID(connection, productID);
			
			if (result.size() == 0) {
				throw new DAOException("There was an error with the deletion, update failed");
			}
			
			connection.commit();
			return result;
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
