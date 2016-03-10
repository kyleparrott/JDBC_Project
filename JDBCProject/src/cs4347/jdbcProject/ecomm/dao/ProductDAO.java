package cs4347.jdbcProject.ecomm.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

/**
 * DAO that exclusively updates the PRODUCT table. 
 */
public interface ProductDAO
{
	/**
	 * The create method must throw a DAOException if the 
	 * given Product has a non-null ID. The create method must 
	 * return the same Product with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Product has a non-null id.
	 */
	Product create(Connection connection, Product product) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	Product retrieve(Connection connection, Long id) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * Product has a NULL id. 
	 */
	int update(Connection connection, Product product) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	int delete(Connection connection, Long id) throws SQLException, DAOException;
	
	/**
	 * Retrieve products in the given product category
	 */
	List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException;

	/**
	 * Retrieve the product with the given UPC. UPC is unique across all product. 
	 */
	Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException;

}
