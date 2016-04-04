package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private DataSource dataSource;

	public ProductPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	//methodss
	@Override
	public Product create(Product product) throws SQLException, DAOException {
		ProductDaoImpl productDAO = new ProductDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Product prod = productDAO.create(connection, product);
			Long prodId = prod.getId();
			
			if(prodId == null) {
				throw new DAOException("An error occurred assigning an ID during the insert");
			}
			
			if (prod.getProdUPC() == null) { 
				throw new DAOException("Product must have an associated UPC.");
			}
			
		
			connection.commit();
			return prod;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public Product retrieve(Long id) throws SQLException, DAOException {
		ProductDaoImpl productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
			Product prod = productDAO.retrieve(connection, id);
			
			if (prod == null) {
				System.out.println("null product");
				return null;
			} 
			connection.commit();
			return prod;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public int update(Product product) throws SQLException, DAOException {
		ProductDaoImpl productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
			if (product.getProdUPC() == null) {
				throw new DAOException("Product must have an associated UPC");
			}
			
			if (product.getProdCategory() == 0) {
				throw new DAOException("Product must have an associated Category");
			}
			
			if (product.getId() <= 0){
				throw new DAOException("Invalid Value for id: " + product.getId());
			}
			
			int numRows = productDAO.update(connection, product);
			
			if (numRows <= 0) {
				throw new DAOException("There was an error with the insert, update failed");
			}
			
			connection.commit();
			return numRows;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		ProductDaoImpl productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
		
			if (id <= 0){
				throw new DAOException("Invalid Value for id: " + id);
			}
			
			int numRows = productDAO.delete(connection, id);
			
			if (numRows <= 0) {
				throw new DAOException("There was an error with the deletion, update failed");
			}
			
			connection.commit();
			return numRows;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public Product retrieveByUPC(String upc) throws SQLException, DAOException {
		ProductDaoImpl productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
		
			
			Product result = productDAO.retrieveByUPC(connection, upc);
			
			
			connection.commit();
			return result;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		
	}

	@Override
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException {
		ProductDaoImpl productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try {
			connection.setAutoCommit(false);
		
			if (category <= 0){
				throw new DAOException("Invalid Value for category: " + category);
			}
			
			List<Product> result = productDAO.retrieveByCategory(connection, category);
			
			if (result.size() == 0) {
				throw new DAOException("There was an error with the deletion, update failed");
			}
			
			connection.commit();
			return result;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		
	}

}}