package bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ojdbc.Repository;

import entity.Products;

@ManagedBean
@SessionScoped
public class ProductCollectionBean {
  private List<Products> products;
  private int categoryId;

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public List<Products> getProducts() {
    return products;
  }

  public void setProducts(List<Products> products) {
    this.products = products;
  }

  public String menuAction() throws IOException, SQLException {

    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

    Integer id = Integer.parseInt(params.get("id"));

    Repository repo = new Repository();

    try {

      setProducts(repo.getProducts(id));

      // ExternalContext ec =
      // FacesContext.getCurrentInstance().getExternalContext();
      // ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());

    } catch (SQLException e) {

      e.printStackTrace();
    }
    return "ProductsList";
  }

}
