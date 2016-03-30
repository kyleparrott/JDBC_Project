package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
		String query = String.format("INSERT INTO simple_company.Purchase values (%d, '%s', %.2f, %d, %d);", purchase.getId(),
				purchase.getPurchaseDate(), purchase.getPurchaseAmount(), purchase.getCustomerID(), purchase.getProductID());
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.executeUpdate();
		ResultSet set = statement.getGeneratedKeys();
		set.next();
		int id = set.getInt(1);
		purchase.setId((long) id);
		connection.commit();
		return purchase;
	}

	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Purchase where id = %d", id);
		ResultSet set = statement.executeQuery(query);		
		set.next();
		Purchase result = new Purchase();		
		result.setCustomerID((long) set.getInt("customerId"));
		result.setId((long) set.getInt("id"));
		result.setProductID((long) set.getInt("productId"));
		result.setPurchaseAmount(set.getDouble("purchaseAmount"));
		result.setPurchaseDate(set.getDate("purchaseDate"));
		
		return result;
	}

	@Override
	public int update(Connection connection, Purchase purchase) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("UPDATE Purchase SET id = %d, customerId = %d, productId = %d, purchaseDate = '%s', purchaseAmount = %.2f WHERE id = %d", 
				purchase.getId(), purchase.getCustomerID(), purchase.getProductID(), purchase.getPurchaseDate(), purchase.getPurchaseAmount(), purchase.getId());
		System.out.println("update query: " + query);
		int numRows = statement.executeUpdate(query);
		connection.commit();
		return numRows;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		
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

		Statement statement = connection.createStatement();
		String query = String.format("SELECT id, purchaseDate, purchaseAmount, customerId, productId FROM simple_company.Purchase where productID = %d", productID);
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
	public PurchaseSummary retrievePurchaseSummary(Connection connection, Long customerID)
			throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT id, purchaseDate, purchaseAmount, customerId, productId FROM simple_company.Purchase where customerID = %d", customerID);
		System.out.println("Sql: " + query);
		ResultSet resSet = statement.executeQuery(query);

		ArrayList<Float> amounts = new ArrayList<Float>();
		float total = 0,
			  min = Float.MAX_VALUE,
			  max = 0,
			  size = 0,
			  current;
		
		while (resSet.next()) {
			current = resSet.getFloat("purchaseAmount");
			if (current > max) max = current;
			if (current < min) min = current;
			total += current;
			size++;
		}
	
		PurchaseSummary summary = new PurchaseSummary();
		summary.avgPurchase = total/size;
		summary.maxPurchase = max;
		summary.minPurchase = min;
		return summary;
	}
	
}
