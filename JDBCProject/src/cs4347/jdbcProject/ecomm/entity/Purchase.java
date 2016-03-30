package cs4347.jdbcProject.ecomm.entity;

import java.sql.Date;

public class Purchase
{
	private static long purchaseCount = 0;
	private Long id;
	private Long customerID;
	private Long productID;
	private Date purchaseDate;
	private double purchaseAmount;

	public Long getCustomerID()
	{
		return customerID;
	}

	public void setCustomerID(Long customerID)
	{
		this.customerID = customerID;
	}

	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	public double getPurchaseAmount()
	{
		return purchaseAmount;
	}

	public void setPurchaseAmount(double purchaseAmount)
	{
		this.purchaseAmount = purchaseAmount;
	}

	public Long getProductID()
	{
		return productID;
	}

	public void setProductID(Long productID)
	{
		this.productID = productID;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
