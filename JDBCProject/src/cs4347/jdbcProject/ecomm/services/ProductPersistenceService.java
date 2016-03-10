package cs4347.jdbcProject.ecomm.services;

import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

/** 
 */
public interface ProductPersistenceService
{
	/**
	 * The create method must throw a DAOException if the 
	 * given Product has a non-null ID. The create method must 
	 * return the same Product with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Purchase has a non-null id.
	 */
	Product create(Product product) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	Product retrieve(Long id) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * Product has a NULL id. 
	 */
	int update(Product product) throws SQLException, DAOException;
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	int delete(Long id) throws SQLException, DAOException;
	
	/**
	 * Retrieve a product by its unique UPC
	 */
	Product retrieveByUPC(String upc) throws SQLException, DAOException;

	/**
	 * Retrive products in the given category
	 */
	List<Product> retrieveByCategory(int category) throws SQLException, DAOException;
	
}