package cs4347.jdbcProject.ecomm.dao;

import java.sql.Connection;
import java.sql.SQLException;

import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;

/**
 * DAO that exclusively updates the CREDIT_CARD table. 
 */
public interface CreditCardDAO
{
	CreditCard create(Connection connection, CreditCard creditCard, Long customerID) throws SQLException, DAOException;
	
	CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException;
	
	void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException;
}
