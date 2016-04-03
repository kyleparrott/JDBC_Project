package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{

	@Override
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
		if(customer.getId() != null) {
			throw new DAOException("Customer cannot have a non-null id");
		} else {
			String query = String.format("INSERT INTO simple_company.Customer values(NULL, '%s', '%s', '%s', '%s', '%s');", customer.getFirstName(),
					customer.getLastName(), customer.getGender(), customer.getDob(), customer.getEmail());
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();
			ResultSet set = statement.getGeneratedKeys();
			set.next();
			long id = set.getLong(1);
			customer.setId(id);
			return customer;
		}
	}

	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Customer where id = %d", id);
		ResultSet set = statement.executeQuery(query);
		if (!set.next()) {
			return null;
		} else {
			Customer result = new Customer();		
			result.setEmail(set.getString("email"));
			result.setDob(set.getDate("dob"));
			result.setId(set.getLong("id"));
			result.setGender(set.getString("gender").charAt(0));
			result.setFirstName(set.getString("firstName"));
			result.setLastName(set.getString("lastName"));
			return result;
		}
	}

	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("UPDATE simple_company.Customer SET firstName = '%s', lastName = '%s', dob = '%s', gender = '%c', email = '%s' WHERE id = %d;", 
				customer.getFirstName(), customer.getLastName(), customer.getDob(), customer.getGender(), customer.getEmail(), customer.getId());		
		System.out.println("update query: " + query);
		int numRows = statement.executeUpdate(query);
		return numRows;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("DELETE FROM simple_company.Customer where id=%d", id);
		System.out.println("delete query: " + query);
		int numRows = statement.executeUpdate(query);
		return numRows;
	}

	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("Select * from Customer Left Join Address on Customer.id=Address.customerId where zipcode = '%s';", zipCode);
		System.out.println("Sql: " + query);
		ResultSet resSet = statement.executeQuery(query);

		ArrayList<Customer> result = new ArrayList<Customer>();
		int index = 0;
		while (resSet.next()) {
			result.add(new Customer());
			result.get(index).setId(resSet.getLong("id"));
			result.get(index).setFirstName(resSet.getString("firstName"));
			result.get(index).setLastName(resSet.getString("lastName"));
			result.get(index).setEmail(resSet.getString("email"));
			result.get(index).setGender(resSet.getString("gender").charAt(0));
			result.get(index).setDob(resSet.getDate("dob"));
			index++;
		}
		return result;
	}

	@Override
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate)
			throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("Select * from Customer where dob >= '%s' and dob < '%s';", startDate, endDate);
		System.out.println("Sql: " + query);
		ResultSet resSet = statement.executeQuery(query);

		ArrayList<Customer> result = new ArrayList<Customer>();
		int index = 0;
		while (resSet.next()) {
			result.add(new Customer());
			result.get(index).setId(resSet.getLong("id"));
			result.get(index).setFirstName(resSet.getString("firstName"));
			result.get(index).setLastName(resSet.getString("lastName"));
			result.get(index).setEmail(resSet.getString("email"));
			result.get(index).setGender(resSet.getString("gender").charAt(0));
			result.get(index).setDob(resSet.getDate("dob"));
			index++;
		}
		return result;
	}
	
}
