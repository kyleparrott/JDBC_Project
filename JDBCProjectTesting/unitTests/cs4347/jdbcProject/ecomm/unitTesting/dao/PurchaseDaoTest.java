package cs4347.jdbcProject.ecomm.unitTesting.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;

public class PurchaseDaoTest
{
	// Need current customer ID from table CUSTOMER
	Long customerID = 1l;
	// Need current product ID from table PRODUCT
	Long productID = 1l;
	
	@Test
	public void testRetrieveForCustID() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		PurchaseDAO pdao = new PurchaseDaoImpl();
		
		List<Purchase> purchases = pdao.retrieveForCustomerID(connection, customerID);
		assertTrue(purchases.size() >= 1);

		connection.close();
	}
	
	@Test
	public void testCreatePurchase() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);

		PurchaseDAO pdao = new PurchaseDaoImpl();
		
		Purchase purchase = buildPurchase(customerID, productID);
		assertNull(purchase.getId());
		Purchase purchase2 = pdao.create(connection, purchase);
		assertNotNull(purchase2.getId());

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}
	
	@Test
	public void testUpdatePurchase() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);

		PurchaseDAO pdao = new PurchaseDaoImpl();
		
		Purchase purchase = buildPurchase(customerID, productID);
		Purchase purchase2 = pdao.create(connection, purchase);
		purchase2.setPurchaseAmount(123.0);
		pdao.update(connection, purchase2);
		Purchase purchase3 = pdao.retrieve(connection, purchase2.getId());
		assertEquals(123.0, purchase3.getPurchaseAmount(), 0);

		// Do not commit any changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}
	
	@Test
	public void testPurchaseSummary() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		PurchaseDAO pdao = new PurchaseDaoImpl();
		
		PurchaseSummary psummary = pdao.retrievePurchaseSummary(connection, customerID);
		assertTrue(psummary.minPurchase >= 0);
		assertTrue(psummary.maxPurchase > 0);
		assertTrue(psummary.avgPurchase > 0);

		connection.close();
	}

	Random rng = new Random();
	
	Purchase buildPurchase(Long customerID, Long productID)
	{
		Purchase result = new Purchase();
		result.setPurchaseDate(new java.sql.Date(System.currentTimeMillis()));
		result.setPurchaseAmount(rng.nextDouble() + 100.0);
		result.setCustomerID(customerID);
		result.setProductID(productID);
		return result;
	}
}
