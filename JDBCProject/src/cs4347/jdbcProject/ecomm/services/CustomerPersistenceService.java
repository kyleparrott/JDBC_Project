package cs4347.jdbcProject.ecomm.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

/** 
 * Although this interface is identical to CustomerDAO, the 
 * difference is that this interface will expect Customer instances
 * to maintain their associated Address and CreditCard entities. 
 */
public interface CustomerPersistenceService
{
	/**
	 * The create method must throw a DAOException if the 
	 * given Customer has a non-null ID.
	 * @throws DAOException if the given Customer has a non-null id.
	 */
	Customer create(Customer customer) throws SQLException, DAOException;
	
	/**
	 * The remaining RUD methods expect the customer to be persisted
	 * i.e. to maintain an ID. 
	 */
	Customer retrieve(Long id) throws SQLException, DAOException;
	int update(Customer customer) throws SQLException, DAOException;
	int delete(Long id) throws SQLException, DAOException;
	
	/**
	 * Retrieve all Customers whose address is in the given zipcode.
	 */
	List<Customer> retrieveByZipCode(String zipCode) throws SQLException, DAOException;
	
	/**
	 * Retrieve all Customer whose DOB is in the given date range.
	 */
	List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException;
}