package cs4347.jdbcProject.ecomm.unitTesting.dao;

import static org.junit.Assert.*;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.testing.DataSourceManager;

public class AddressDaoTest
{
	// NOTE: This must be a valid CustomerID
	Long customerID = 2L; 
	
	@Test
	public void testCreate()  throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit changes made by this test.
		connection.setAutoCommit(false);
		
		Address addr = buildAddress();
		AddressDAO dao = new AddressDaoImpl();

		Address addr2 = dao.create(connection, addr, customerID);
		assertNotNull(addr2);
		
		// Do not commit changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	@Test
	public void testRetrieveForCustomerID () throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		
		AddressDAO dao = new AddressDaoImpl();

		Address addr = dao.retrieveForCustomerID(connection, customerID);
		assertNotNull(addr);
		assertNotNull(addr.getAddress1());
		assertNotNull(addr.getCity());
		assertNotNull(addr.getState());
		assertNotNull(addr.getZipcode());

		connection.close();
	}

	@Test
	public void testDeleteForCustomerID() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit changes made by this test.
		connection.setAutoCommit(false);
		
		AddressDAO dao = new AddressDaoImpl();

		Address addr = dao.retrieveForCustomerID(connection, customerID);
		assertNotNull(addr);
		dao.deleteForCustomerID(connection, customerID);
		Address addr2 = dao.retrieveForCustomerID(connection, customerID);
		assertNull(addr2);
		
		// Do not commit changes made by this test.
		connection.rollback();
		connection.setAutoCommit(true);
		connection.close();
	}

	private Address buildAddress()
    {
		Address addr = new Address();
		addr.setAddress1("address one");
		addr.setAddress2("address two");
		addr.setCity("city");
		addr.setState("IL");
		addr.setZipcode("32345");
		return addr;
    }
}
