package cs4347.jdbcProject.ecomm.testing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

/**
 * This class provides an example of the error checking 
 * and the method of obtaining and assigning the auto-increment
 * primary key that was assigned to the Customer when it was
 * inserted into the DBMS. These must be implemented in your 
 * CUSTOMER and PURCHASE DAO implementation. 
 */
public class SampleCreateMethod
{
	private DataSource dataSource; 
	
	public SampleCreateMethod(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	private static final String insertSQL = 
			"INSERT INTO CUSTOMER (first_name, last_name, dob, gender, email) VALUES (?, ?, ?, ?, ?);";

	public Customer create(Customer customer) throws SQLException, DAOException
	{
		if (customer.getId() != null) {
			throw new DAOException("Trying to insert Customer with NON-NULL ID");
		}

		Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getFirstName());
			// Set other statement attributes here...
			ps.executeUpdate();

			// REQUIREMENT: Copy the auto-increment primary key to the customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			customer.setId((long) lastKey);
			
			return customer;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}
