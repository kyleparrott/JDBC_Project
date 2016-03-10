package cs4347.jdbcProject.ecomm.unitTesting.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.services.impl.ProductPersistenceServiceImpl;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;

public class ProductPersistenceServiceTest
{

	@Test
	public void testCreate() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(dataSource);
		
		Product product = buildProduct();
		assertNull(product.getId());
		Product prod2 = ppService.create(product);
		assertNotNull(prod2.getId());
	}

	@Test
	public void testRetrieve() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(dataSource);
		
		Product product = buildProduct();
		product = ppService.create(product);
		Long newProdID = product.getId();
		
		Product prod2 = ppService.retrieve(newProdID);
		assertNotNull(prod2);
		assertEquals(product.getProdCategory(), prod2.getProdCategory());
		assertEquals(product.getProdDescription(), prod2.getProdDescription());
		assertEquals(product.getProdName(), prod2.getProdName());
		assertEquals(product.getProdUPC(), prod2.getProdUPC());
	}

	@Test
	public void testUpdate() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(dataSource);
		
		Product product = buildProduct();
		Product prod2 = ppService.create(product);
		Long id = prod2.getId();
		
		String newUPC = "3333333333";
		prod2.setProdUPC(newUPC);
		int rows = ppService.update(prod2);
		assertEquals(1, rows);

		Product prod3 = ppService.retrieve(id);
		assertEquals(prod2.getId(), prod3.getId());
		assertEquals(prod2.getProdName(), prod3.getProdName());
		assertEquals(newUPC, prod3.getProdUPC());
	}

	@Test
	public void testDelete() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(dataSource);
		
		Product product = buildProduct();
		Product prod2 = ppService.create(product);
		Long id = prod2.getId();
		
		int num = ppService.delete(id);
		assertEquals(1, num);
		
		Product prod3 = ppService.retrieve(id);
		assertNull(prod3);
	}

	@Test
	public void testRetrieveByUPC() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(dataSource);
		Product product = ppService.retrieveByUPC("576236786900");
		assertNotNull(product);
	}

	@Test
	public void testRetrieveByCategory() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(dataSource);
		List<Product> products = ppService.retrieveByCategory(2);
		assertTrue(products.size() > 1);
	}

	private Product buildProduct()
    {
		Product product = new Product();
		product.setProdCategory(1);
		product.setProdDescription("Product Description");
		product.setProdName("Product Name");
		product.setProdUPC("1112223333");
	    return product;
    }

}
