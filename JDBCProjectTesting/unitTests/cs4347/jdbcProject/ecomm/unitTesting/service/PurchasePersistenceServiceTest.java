package cs4347.jdbcProject.ecomm.unitTesting.service;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchasePersistenceService;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.services.impl.PurchasePersistenceServiceImpl;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;

public class PurchasePersistenceServiceTest
{
	// Must be existing Customer and Product IDs
	Long customerID = 2l;
	Long productID = 2l;

	@Test
	public void testCreate() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(dataSource);

		Purchase purchase = buildPurchase(customerID, productID);
		assertNull(purchase.getId());
		Purchase purch2 = ppService.create(purchase);
		assertNotNull(purch2.getId());
	}

	@Test
	public void testRetrieve() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(dataSource);

		Purchase purchase = buildPurchase(customerID, productID);
		purchase = ppService.create(purchase);
		Long newID = purchase.getId();

		Purchase purch2 = ppService.retrieve(newID);
		assertNotNull(purch2);
		assertEquals(purchase.getId(), purch2.getId());
		assertEquals(purchase.getPurchaseAmount(), purch2.getPurchaseAmount(), 0.1);
		assertEquals(purchase.getCustomerID(), purch2.getCustomerID());
		assertEquals(purchase.getProductID(), purch2.getProductID());

		// Elminate time component from date comparison
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		String d1 = formatter.format(purchase.getPurchaseDate());
		String d2 = formatter.format(purch2.getPurchaseDate());
		assertEquals(d1, d2);
	}

	@Test
	public void testUpdate() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(dataSource);

		Purchase purchase = buildPurchase(customerID, productID);
		Purchase purchase2 = ppService.create(purchase);
		Long newID = purchase.getId();

		Double newPrice = 2222.00;
		purchase2.setPurchaseAmount(newPrice);
		int rows = ppService.update(purchase2);
		assertEquals(1, rows);

		Purchase purchase3 = ppService.retrieve(newID);
		assertEquals(purchase2.getId(), purchase3.getId());
		assertEquals((double) newPrice, purchase.getPurchaseAmount(), 0.1);
	}

	@Test
	public void testDelete() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(dataSource);

		Purchase purchase = buildPurchase(customerID, productID);
		Purchase purchase2 = ppService.create(purchase);
		Long id = purchase2.getId();

		int num = ppService.delete(id);
		assertEquals(1, num);

		Purchase purchase3 = ppService.retrieve(id);
		assertNull(purchase3);
	}

	@Test
	public void testRetrieveForCustomerID() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(dataSource);
		List<Purchase> purchases = ppService.retrieveForCustomerID(customerID);
		assertTrue(purchases.size() > 0);
	}

	@Test
	public void testRetrievePurchaseSummary() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(dataSource);
		PurchaseSummary summary = ppService.retrievePurchaseSummary(customerID);
		assertNotNull(summary);
		assertTrue(summary.avgPurchase > 0);
		assertTrue(summary.minPurchase > 0);
		assertTrue(summary.maxPurchase > 0);
	}

	@Test
	public void testRetrieveForProductID() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(dataSource);
		List<Purchase> purchases = ppService.retrieveForProductID(productID);
		assertTrue(purchases.size() > 0);
	}

	private Purchase buildPurchase(Long customerID, Long productID)
	{
		Purchase purchase = new Purchase();
		purchase.setPurchaseAmount(100.00);
		purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
		purchase.setCustomerID(customerID);
		purchase.setProductID(productID);
		return purchase;
	}

}
