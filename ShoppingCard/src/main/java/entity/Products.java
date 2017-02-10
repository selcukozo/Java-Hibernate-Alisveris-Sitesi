package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Products")
public class Products {

  public Products() {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G2")
  @SequenceGenerator(name = "G2", sequenceName = "PRODUCT_SEQ")
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "supplierid")
  private int supplierid;

  @Column(name = "price")
  private float price;

  @Column(name = "categoryid")
  private int categoryid;

  @Transient
  private String showName;

  // Eski çalýþan kod
  // Ekran görüntüsü için açtýk

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "products")
  List<ProductImage> images;

  public List<ProductImage> getImages() {
    return images;
  }

  public void setImages(List<ProductImage> images) {
    this.images = images;
  }
  // Ekran görüntüsü için kapattýk
  // private List<ProductImage> images;
  //
  // public List<ProductImage> getImages() {
  // return images;
  // }
  //
  // public void setImages(List<ProductImage> images) {
  // this.images = images;
  // }

  public String getShowName() {
    return showName;
  }

  public void setShowName(String showName) {
    this.showName = showName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSupplierid() {
    return supplierid;
  }

  public void setSupplierid(int suplierid) {
    this.supplierid = suplierid;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getCategoryid() {
    return categoryid;
  }

  public void setCategoryid(int categoryid) {
    this.categoryid = categoryid;
  }
}
