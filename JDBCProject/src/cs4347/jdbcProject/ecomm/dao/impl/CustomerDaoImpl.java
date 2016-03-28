package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{

	@Override
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("INSERT INTO Customer values(%d, '%s', '%s', '%s', '%s', '%s');", customer.getId(), customer.getFirstName(),
				customer.getLastName(), customer.getGender(), customer.getDob(), customer.getEmail());
		statement.executeUpdate(query);
		return customer;
	}

	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Customer where id = %d;", id);
		ResultSet set = statement.executeQuery(query);
		//cast it back to a customer object and return it	
		return null;
	}

	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate)
			throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
