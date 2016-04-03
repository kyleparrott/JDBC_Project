package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;


public class CreditCardDaoImpl implements CreditCardDAO
{

	@Override
	public CreditCard create(Connection connection, CreditCard creditCard, Long customerID) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("INSERT INTO CreditCard values ('%s', '%s', '%s', '%s', %d);", creditCard.getName(),
				creditCard.getCcNumber(), creditCard.getExpDate(), creditCard.getSecurityCode(), customerID);
		statement.executeUpdate(query);
		return creditCard;
	}

	@Override
	public CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.creditcard where customerID = %d", customerID);
		ResultSet set = statement.executeQuery(query);		
		if (!set.next()){
			return null;
		} 
		CreditCard result = new CreditCard();
		
		result.setName(set.getString("name"));
		result.setCcNumber(set.getString("ccNumber"));
		result.setExpDate(set.getString("expDate"));
		result.setSecurityCode(set.getString("securityCode"));
		return result;
	}

	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		Statement statement = connection.createStatement();
		String query = String.format("DELETE FROM simple_company.creditcard where customerID = %d", customerID);
		int numRows = statement.executeUpdate(query);
		return;
	}

}
