package entity;

public class ProductDetailMapping {
  private int id;
  private int productId;
  private int productDetailId;
  private String value;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getProductDetailId() {
    return productDetailId;
  }

  public void setProductDetailId(int productDetailId) {
    this.productDetailId = productDetailId;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
