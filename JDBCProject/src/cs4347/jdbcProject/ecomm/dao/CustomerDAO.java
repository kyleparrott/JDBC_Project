package cs4347.jdbcProject.ecomm.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

/**
 * The CustomerDAO exclusively updates the CUSTOMER table.
 * That is, this DAO should not return Customer instances
 * with their associated Address and CreditCard assigned. 
 * Supporting those relationships is the responsibility of 
 * the CustomerPersistenceService. 
 */
public interface CustomerDAO
{
	/**
	 * The create method must throw a DAOException if the 
	 * given Customer has a non-null ID.The create method must 
	 * return the same Customer with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Customer has a non-null id.
	 */
	Customer create(Connection connection, Customer customer) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	Customer retrieve(Connection connection, Long id) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * Customer has a NULL id. 
	 */
	int update(Connection connection, Customer customer) throws SQLException, DAOException;
	
	/**
	 * The delete method must throw DAOException if the provided 
	 * ID is null. 
	 * 
	 * Note the implementation of this method can 
	 * make use of CASCADE_DELETE option on the FK relations for
	 * Address, CreditCard, and Purchase. If the schema includes cascade delete,
	 * the Customer delete operation will automatically remove the associated
	 * Address, CreditCard, and Purchase rows for their tables. 
	 */
	int delete(Connection connection, Long id) throws SQLException, DAOException;
	
	/**
	 * Retrieve customers in the given address.zipcode
	 */
	List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException;

	/**
	 * Retrieve customers with a DOB in the given start / end date range.
	 */
	List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate) throws SQLException, DAOException;
}
