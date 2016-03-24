package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.*;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{

	@Override
	public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		Statement state = connection.createStatement();
		String sql = String.format("INSERT INTO Address VALUES ('%s', '%s', '%s', '%s', '%s', %d);", address.getAddress1(), address.getAddress2(),
				address.getCity(), address.getState(), address.getZipcode(), customerID);
		state.executeUpdate(sql);
		return address;
	}

	@Override
	public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		
	}
}
