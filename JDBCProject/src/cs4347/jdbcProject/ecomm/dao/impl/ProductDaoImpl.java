package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{

	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("INSERT INTO Product values (%d, '%s', '%s', %d, '%s');", product.getId(), product.getProdName(),
				product.getProdDescription(), product.getProdCategory(), product.getProdUPC());
		statement.executeUpdate(query);
		return product;
	}

	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
