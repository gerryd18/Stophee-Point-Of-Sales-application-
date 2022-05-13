package finalproject_BAD;

public class Productcategorytype {

	private String id;
	private String productCategoryTypeName;
	
	public Productcategorytype(String id, String productCategoryTypeName) {
		super();
		this.id = id;
		this.productCategoryTypeName = productCategoryTypeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductCategoryTypeName() {
		return productCategoryTypeName;
	}

	public void setProductCategoryTypeName(String productCategoryTypeName) {
		this.productCategoryTypeName = productCategoryTypeName;
	}
	
	
	

}
