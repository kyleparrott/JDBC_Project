package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{

	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException {
		if (product.getId() != null){
			throw new DAOException("Non-null product ID");
		}
		String query = String.format("INSERT INTO simple_company.Product values (%d, '%s', '%s', %d, '%s');", product.getId(), product.getProdName(),
				product.getProdDescription(), product.getProdCategory(), product.getProdUPC());
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.executeUpdate();
		ResultSet set = statement.getGeneratedKeys();
		set.next();
		product.setId(set.getLong(1));
		return product;
	}

	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Product where id = %d", id);
		System.out.println("query: " + query);
		ResultSet set = statement.executeQuery(query);
		if (!set.next()) { //Null result set
			 return null;
		} else {
			Product result = new Product();		
			result.setProdUPC(set.getString("prodUPC"));
			result.setId(set.getLong("id"));
			result.setProdCategory(set.getInt("prodCategory"));
			result.setProdName(set.getString("prodName"));
			result.setProdDescription(set.getString("prodDescription"));
			return result;
		}
	}

	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("UPDATE Product SET prodCategory = %d, prodName = '%s', prodDescription = '%s', prodUPC = '%s' WHERE id = %d", 
				 product.getProdCategory(), product.getProdName(), product.getProdDescription(), product.getProdUPC(), product.getId());
		System.out.println("update query: " + query);
		int numRows = statement.executeUpdate(query);
		return numRows;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("DELETE FROM simple_company.Product where id=%d", id);
		System.out.println("delete query: " + query);
		int numRows = statement.executeUpdate(query);
		return numRows;
	}

	@Override
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Product where prodCategory = %d", category);
		System.out.println("Sql: " + query);
		ResultSet resSet = statement.executeQuery(query);

		ArrayList<Product> result = new ArrayList<Product>();
		int index = 0;
		while (resSet.next()) {
			result.add(new Product());
			result.get(index).setId((long) resSet.getInt("id"));
			result.get(index).setProdCategory(resSet.getInt("prodCategory"));
			result.get(index).setProdDescription(resSet.getString("prodDescription"));
			result.get(index).setProdUPC(resSet.getString("prodUPC"));
			result.get(index).setProdName(resSet.getString("prodName"));
			index++;
		}
		return result;
	}

	@Override
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException {
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM simple_company.Product where prodUPC = '%s'", upc);
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

}
