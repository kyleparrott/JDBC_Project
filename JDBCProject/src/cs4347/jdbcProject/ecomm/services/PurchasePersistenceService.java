package cs4347.jdbcProject.ecomm.services;

import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.util.DAOException;

/** 
 * 
 */
public interface PurchasePersistenceService
{
	/**
	 * The create method must throw a DAOException if the 
	 * given Purchase has a non-null ID. The create method must 
	 * return the same Purcahse with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Purchase has a non-null id.
	 */
	Purchase create(Purchase purchase) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	Purchase retrieve(Long id) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * Purchase has a NULL id. 
	 */
	int update(Purchase purchase) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	int delete(Long id) throws SQLException, DAOException;
	
	/**
	 * Retrieve purchases made by the given customer.
	 */
	List<Purchase> retrieveForCustomerID(Long customerID) throws SQLException, DAOException;
	
	/**
	 * Produce a purchase summary report for the given customer.
	 */
	PurchaseSummary retrievePurchaseSummary(Long customerID) throws SQLException, DAOException;

	/**
	 * Retrieve purchases made for the given product.
	 */
	List<Purchase> retrieveForProductID(Long productID) throws SQLException, DAOException;
}