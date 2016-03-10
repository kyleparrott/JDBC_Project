package cs4347.jdbcProject.ecomm.dao;

import java.sql.Connection;
import java.sql.SQLException;

import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

/**
 * DAO that exclusively updates the ADDRESS table. 
 */
public interface AddressDAO
{
	Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException;
	
	Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException;
	
	void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException;
}
