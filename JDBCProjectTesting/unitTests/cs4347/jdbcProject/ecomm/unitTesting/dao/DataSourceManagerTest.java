package cs4347.jdbcProject.ecomm.unitTesting.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;

import cs4347.jdbcProject.ecomm.testing.DataSourceManager;

public class DataSourceManagerTest
{

	@Test
	public void testGetPropertiesFromClasspath() throws Exception
	{
		Properties props = DataSourceManager.getPropertiesFromClasspath();
		assertNotNull(props);
		assertNotNull(props.getProperty("url"));
		assertNotNull(props.getProperty("id"));
		assertNotNull(props.getProperty("passwd"));
	}


	@Test
	public void testGetDataSource() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		assertNotNull(ds);
	}
	
	/**
	 * NOTE: This test will execute only if the DBMS is running 
	 * at the url in the configuration file. 
	 * @throws Exception
	 */
	@Test
	public void testGetConnection() throws Exception
	{
		DataSource ds = DataSourceManager.getDataSource();
		Connection conn = ds.getConnection();
		assertNotNull(conn);
	}
}
