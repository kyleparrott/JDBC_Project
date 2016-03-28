package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String query = String.format("INSERT INTO Purchase values (%d, '%s', %.2f, %d, %d);", purchase.getId(),
				purchase.getPurchaseDate(), purchase.getPurchaseAmount(), purchase.getCustomerID(), purchase.getProductID());
		statement.executeUpdate(query);
		return purchase;
	}

	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Customer where id = %d", id);
		ResultSet set = statement.executeQuery(query);
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
		// TODO Auto-generated method stub
		return null;
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
