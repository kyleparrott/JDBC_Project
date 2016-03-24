package cs4347.jdbcProject.ecomm.entity;

public class Product
{
	static private long productCount = 0;
	private Long id;
	private String prodName;
	private String prodDescription;
	private int prodCategory;
	private String prodUPC;
	
	public Product() {
		this.setId(getCurrId());
	}
	
	private long getCurrId() {
		productCount++;
		return productCount;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getProdName()
	{
		return prodName;
	}

	public void setProdName(String prodName)
	{
		this.prodName = prodName;
	}

	public String getProdDescription()
	{
		return prodDescription;
	}

	public void setProdDescription(String prodDescription)
	{
		this.prodDescription = prodDescription;
	}

	public int getProdCategory()
	{
		return prodCategory;
	}

	public void setProdCategory(int prodCategory)
	{
		this.prodCategory = prodCategory;
	}

	public String getProdUPC()
	{
		return prodUPC;
	}

	public void setProdUPC(String prodUPC)
	{
		this.prodUPC = prodUPC;
	}

}
