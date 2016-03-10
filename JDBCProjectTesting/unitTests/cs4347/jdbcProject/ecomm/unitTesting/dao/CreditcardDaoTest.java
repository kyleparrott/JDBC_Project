package cs4347.jdbcProject.ecomm.unitTesting.dao;

import static org.junit.Assert.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;

public class CreditcardDaoTest
{
	// NOTE: This must be a valid CustomerID
	Long customerID = 2L;

	@Test
	public void testCreate() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			// Do not commit changes made by this test.
			connection.setAutoCommit(false);

			CreditCard ccard = buildCreditcard();
			CreditCardDAO dao = new CreditCardDaoImpl();

			CreditCard ccard2 = dao.create(connection, ccard, customerID);
			assertNotNull(ccard2);
		}
		finally {
			// Do not commit changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testRetrieveForCustomerID() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();

		CreditCardDAO dao = new CreditCardDaoImpl();

		CreditCard ccard = dao.retrieveForCustomerID(connection, customerID);
		assertNotNull(ccard);
		assertNotNull(ccard.getCcNumber());
		assertNotNull(ccard.getExpDate());
		assertNotNull(ccard.getName());
		assertNotNull(ccard.getSecurityCode());

		connection.close();
	}

	@Test
	public void testDeleteForCustomerID() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit changes made by this test.
		connection.setAutoCommit(false);

		CreditCardDAO dao = new CreditCardDaoImpl();

		CreditCard ccard = dao.retrieveForCustomerID(connection, customerID);
		assertNotNull(ccard);
		dao.deleteForCustomerID(connection, customerID);
		CreditCard ccard2 = dao.retrieveForCustomerID(connection, customerID);
		assertNull(ccard2);

		// Do not commit changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	private CreditCard buildCreditcard()
	{
		CreditCard ccard = new CreditCard();
		ccard.setCcNumber("222233334444");
		ccard.setExpDate("2/13");
		ccard.setName("Josh Josh");
		ccard.setSecurityCode("222");
		return ccard;
	}
}
