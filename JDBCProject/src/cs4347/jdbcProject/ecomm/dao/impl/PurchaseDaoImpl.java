package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchaseDaoImpl implements PurchaseDAO
{

	@Override
	public Purchase create(Connection connection, Purchase purchase) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("INSERT INTO simple_company.Purchase values (%d, '%s', %.2f, %d, %d);", purchase.getId(),
				purchase.getPurchaseDate(), purchase.getPurchaseAmount(), purchase.getCustomerID(), purchase.getProductID());
		statement.executeUpdate(query);
		return purchase;
	}

	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Purchase where id = %d", id);
		ResultSet set = statement.executeQuery(query);
		Purchase result = new Purchase();
		result.setId((long) 1234);
//		result.setCustomerID();
		return null;
	}

	@Override
	public int update(Connection connection, Purchase purchase) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Purchase> retrieveForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT id, purchaseDate, purchaseAmount, customerId, productId FROM simple_company.Purchase where id = %d", customerID);
		System.out.println("Sql: " + query);
		ResultSet resSet = statement.executeQuery(query);

		ArrayList<Purchase> result = new ArrayList<Purchase>();
		int index = 0;
		while (resSet.next()) {
			result.add(new Purchase());
			result.get(index).setId((long) resSet.getInt("id"));
			result.get(index).setPurchaseAmount(resSet.getFloat("purchaseAmount"));
			result.get(index).setPurchaseDate(resSet.getDate("purchaseDate"));
			result.get(index).setProductID((long) resSet.getInt("productId"));
			result.get(index).setCustomerID((long) resSet.getInt("customerId"));
			index++;
		}
		return result;
	}

	@Override
	public List<Purchase> retrieveForProductID(Connection connection, Long productID)
			throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseSummary retrievePurchaseSummary(Connection connection, Long customerID)
			throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
