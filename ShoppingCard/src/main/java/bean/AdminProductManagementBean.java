package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.primefaces.event.RowEditEvent;

import com.ojdbc.HibernateHelper;

import entity.Products;

@ManagedBean
@SessionScoped
public class AdminProductManagementBean {
  private String addPhoto = "Hayir";
  // private Products product = null;
  // Tek tek de�erleri get set yapmaktansa bu �ekilde nesne i�inden sayfadaki
  // de�erle e�le�tirmeyi d���nd�m

  // public Products getProduct() {
  // return product;
  // }
  //
  // public void setProduct(Products product) {
  // this.product = product;
  // }
  private int id;
  private String name;
  private String price;
  private int supplierId;
  private int categoryId;
  // Bu dataTable i�in �stteki add i�indi
  private List<Products> products;

  private int selectedProductId = -1;

  public int getSelectedProductId() {
    return selectedProductId;
  }

  private Products selectedProductForImage;

  public Products getSelectedProductForImage() {
    return selectedProductForImage;
  }

  public void setSelectedProductForImage(Products selectedProductForImage) {
    this.selectedProductForImage = selectedProductForImage;
  }

  public void setSelectedProductId(int selectedProductId) {
    this.selectedProductId = selectedProductId;
    SelectedProductImage();
  }

  public void SelectedProductImage() {

    int id = getSelectedProductId();
    for (Products product : products) {
      if (product.getId() == id) {
        setSelectedProductForImage(product);
        ;
      }
    }
  }

  public List<Products> getProducts() {
    return products;
  }

  public void setProducts(List<Products> products) {
    this.products = products;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public int getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddPhoto() {
    return addPhoto;
  }

  public void setAddPhoto(String addPhoto) {
    this.addPhoto = addPhoto;
  }

  @PostConstruct
  public void init() {
    Session session = null;
    try {
      session = HibernateHelper.getSession();

      setProducts((List<Products>) session.createCriteria(Products.class).list());
    } finally {
      session.close();
    }
    // byte[] b = products.get(0).getImages().get(0).getImage2();
    // System.out.println(Arrays.toString(b));
  }

  public void AddProduct() {
    Float productPrice = null;
    try {
      productPrice = Float.parseFloat(getPrice());
    } catch (Exception e) {

    }
    if (productPrice == null) {
      // Bir kere de�er yanl�� girince s�rekli tekrar ediyor hat�rlad���m konu
      setPrice(null);
      FacesMessage msg = new FacesMessage("Uyar�", "Fiyat de�erini kontrol edin");
      FacesContext.getCurrentInstance().addMessage(null, msg);
    } else {
      Products product = new Products();
      // product.setId(getId());
      product.setId(4);
      product.setName(getName());
      // selectonemenu de value de�erini ba�lamay� unutmu�um bi saatten fazla
      // u�ra�t�rd�
      product.setSupplierid(getSupplierId());
      product.setPrice(productPrice);
      product.setCategoryid(getCategoryId());

      Session session = null;
      Boolean hata = false;
      try {
        session = HibernateHelper.getSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();

      } catch (Exception e) {
        session.getTransaction().rollback();
        hata = true;
      } finally {
        session.close();
      }
      if (hata == false) {
        FacesMessage msg = new FacesMessage("Tebrikler!", "�r�n ba�ar�yla eklendi");
        FacesContext.getCurrentInstance().addMessage(null, msg);
      } else {
        FacesMessage msg = new FacesMessage("Hata!", "�r�n eklenmesi s�ras�nda sorun olu�tu");
        FacesContext.getCurrentInstance().addMessage(null, msg);
      }

    }

  }

  public void SiraDuzenle(RowEditEvent rowEditEvent) {

    // FacesMessage msg = new FacesMessage("Car Edited", "" + ((Products)
    // rowEditEvent.getObject()).getId());
    // FacesContext.getCurrentInstance().addMessage(null, msg);

    Products selectedProduct = (Products) rowEditEvent.getObject();
    Session session = null;
    Boolean hata = false;
    try {
      session = HibernateHelper.getSession();
      session.beginTransaction();
      session.saveOrUpdate(selectedProduct);
      session.beginTransaction().commit();
    } catch (Exception e) {
      session.beginTransaction().rollback();
      hata = true;
    } finally {
      session.close();
    }
    if (hata == false) {
      FacesMessage msg = new FacesMessage("�r�n g�ncellendi", "" + ((Products) rowEditEvent.getObject()).getName());
      FacesContext.getCurrentInstance().addMessage(null, msg);
    } else {
      FacesMessage msg = new FacesMessage("Hata",
          "Bu �r�n g�ncellenemedi: " + ((Products) rowEditEvent.getObject()).getName());
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }

  }

  public void SiraDuzenleVazgec(RowEditEvent rowEditEvent) {
    FacesMessage msg = new FacesMessage("�ptal",
        ((Products) rowEditEvent.getObject()).getId() + " nolu �r�n�n g�ncellemesinden vagze�tiniz ");
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  @SuppressWarnings("unchecked")
  public void BringProducts() {
    Session session = null;
    Boolean hata = false;
    try {
      session = HibernateHelper.getSession();
      setProducts((List<Products>) session.createCriteria(Products.class).list());

    } catch (Exception e) {
      hata = true;

    } finally {
      session.close();

    }
    if (hata == false) {
      FacesMessage msg = new FacesMessage("Ba�ar�l�", "G�ncel �r�nler Listelendi");
      FacesContext.getCurrentInstance().addMessage(null, msg);
    } else {
      FacesMessage msg = new FacesMessage("Uyar�", "G�ncel �r�nler Listelenemedi");
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
  }

}
