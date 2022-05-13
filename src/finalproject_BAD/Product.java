package finalproject_BAD;

public class Product {
	
	private String productTypeId;
	private String productName;
	private String productPrice;
	private int ProductQuantity;
	
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductQuantity() {
		return ProductQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		ProductQuantity = productQuantity;
	}
	
	public Product(String productTypeId, String productName, String productPrice, int productQuantity) {
		super();
		this.productTypeId = productTypeId;
		this.productName = productName;
		this.productPrice = productPrice;
		ProductQuantity = productQuantity;
	}

}
