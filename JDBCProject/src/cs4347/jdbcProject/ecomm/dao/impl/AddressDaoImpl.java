package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.*;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{

	@Override
	public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
		Statement state = connection.createStatement();
		String sql = String.format("INSERT INTO address values ('%s', '%s', '%s', '%s', '%s', %d);", address.getAddress1(), address.getAddress2(),
				address.getCity(), address.getState(), address.getZipcode(), customerID);
		state.executeUpdate(sql);
		return address;
	}

	@Override
	public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
		Statement statement = connection.createStatement();
		String query = String.format("SELECT address1, address2, city, state, zipcode FROM simple_company.address where customerId = %d", customerID);
		ResultSet resSet = statement.executeQuery(query);
		
		if (!resSet.next()){
			return null;
		}
		
		Address result = new Address();
		result.setAddress1(resSet.getString("address1"));
		result.setAddress2(resSet.getString("address2"));
		result.setCity(resSet.getString("city"));
		result.setState(resSet.getString("state"));
		result.setZipcode(resSet.getString("zipcode"));
		return result;
	}

	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("DELETE FROM simple_company.address where customerId = %d", customerID);
		statement.executeUpdate(query);
	}
}
