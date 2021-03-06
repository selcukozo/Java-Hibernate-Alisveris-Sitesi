package bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RateEvent;

import com.ojdbc.Repository;
import entity.ProductDetail;
import entity.Products;

@ManagedBean
@SessionScoped
public class ProductDetailBean {

  @ManagedProperty(value = "#{productCollectionBean}")
  private ProductCollectionBean productListBean;

  private Products selectedProducts;
  private ArrayList<ProductDetail> productDetail;

  private Integer productRating;

  public Integer getProductRating() throws SQLException {
    Repository repo = new Repository();
    productRating = repo.getProductRating(selectedProducts.getId());
    return productRating;
  }

  public void setProductRating(Integer productRating) throws SQLException {
    this.productRating = productRating;
    Repository repo = new Repository();
    repo.setProductRating(selectedProducts.getId(), 1, productRating);
  }

  public void onrate(RateEvent rateEvent) {
    int degerlendirme = ((Integer) rateEvent.getRating()).intValue();
    if (degerlendirme == 1) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Puan verildi!",
          "Sizin değerlendirmeniz:" + degerlendirme + " Yıldız :(");
      FacesContext.getCurrentInstance().addMessage(null, message);
    } else if (degerlendirme == 5) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Puan verildi!",
          "Sizin değerlendirmeniz:" + degerlendirme + " Yıldız!!! :)");
      FacesContext.getCurrentInstance().addMessage(null, message);
    } else {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Puan verildi!",
          "Sizin değerlendirmeniz:" + degerlendirme + " Yıldız");
      FacesContext.getCurrentInstance().addMessage(null, message);
    }
  }

  public void oncancel() {
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

  //
  // public String getProductRating(int id) throws SQLException {
  // Repository repo = new Repository();
  //
  // return repo.getProductRating(id).toString();
  // }

  // public void setProductRating(Integer id) throws SQLException {
  // Repository repo = new Repository();
  // // repo.setProductRating(productId, userId, ((Integer)
  // // rateEvent.getRating()).intValue());
  //
  // }

  public Products getSelectedProducts() {
    return selectedProducts;
  }

  public void setSelectedProducts(Products selectedProducts) {
    this.selectedProducts = selectedProducts;
  }

  public ProductCollectionBean getProductListBean() {
    return productListBean;
  }

  public void setProductListBean(ProductCollectionBean productListBean) {
    this.productListBean = productListBean;
  }

  public ArrayList<ProductDetail> getProductDetail() {
    return productDetail;
  }

  public void setProductDetail(ArrayList<ProductDetail> productDetail) {
    this.productDetail = productDetail;
  }

  public String productDetailShow(String id) throws SQLException, IOException {

    Repository repo = new Repository();
    setProductDetail(repo.getProductDetail(Integer.parseInt(id)));

    List<Products> productList = getProductListBean().getProducts();

    for (Products products : productList) {
      if (products.getId() == Integer.parseInt(id)) {
        selectedProducts = products;
      }
    }

    // setRoles(repo.getRoles());

    return "Product-Detail";
  }
}