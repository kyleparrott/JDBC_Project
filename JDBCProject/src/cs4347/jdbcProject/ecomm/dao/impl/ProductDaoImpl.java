package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		String query = String.format("INSERT INTO Product values (%d, '%s', '%s', %d, '%s');", product.getId(), product.getProdName(),
				product.getProdDescription(), product.getProdCategory(), product.getProdUPC());
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.executeUpdate();
		ResultSet set = statement.getGeneratedKeys();
		set.next();
		int id = set.getInt(1);
		product.setId((long) id);
		return product;
	}

	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Product where id = %d", id);
		ResultSet set = statement.executeQuery(query);		
		set.next();
		Product result = new Product();		
		result.setProdUPC(set.getString("prodUPC"));
		result.setId((long) set.getInt("id"));
		result.setProdCategory(set.getInt("prodCategory"));
		result.setProdName(set.getString("prodName"));
		result.setProdDescription(set.getString("prodDescription"));
		return result;
	}

	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("UPDATE Product SET id = %d, prodCategory = %d, prodName = '%s', prodDescription = '%s', prodUPC = %d WHERE id = %d", 
				product.getId(), product.getProdCategory(), product.getProdName(), product.getProdDescription(), product.getProdUPC());
		System.out.println("update query: " + query);
		int numRows = statement.executeUpdate(query);
		connection.commit();
		return numRows;
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
