package com.ojdbc;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import entity.Category;
import entity.ProductDetail;
import entity.ProductDetailMapping;
import entity.ProductImage;
import entity.Products;
import entity.Role;
import entity.Users;

public class Repository {

  public boolean adminLogin(String username, String password) throws SQLException {

    try (Connection con = Database.getConnection()) {

      PreparedStatement smt = con.prepareStatement("SELECT * FROM USERS U JOIN USERROLE UR ON U.ID = UR.USERID "
          + "JOIN ROLE R ON UR.ROLEID = R.ID " + "WHERE R.NAME = 'ADMIN' AND U.USERNAME = ? AND U.PASSWORD = ?");

      smt.setString(1, username);
      smt.setString(2, password);

      ResultSet rs = smt.executeQuery();

      return rs.next();
    }
  }

  public int getUserCount() throws SQLException {

    try (Connection con = Database.getConnection()) {

      Statement smt = con.createStatement();

      ResultSet rs = smt.executeQuery("SELECT COUNT(*) FROM USERS");

      rs.next();

      return rs.getInt(1);
    }
  }

  public int getAdminCount() throws SQLException {

    try (Connection con = Database.getConnection()) {

      PreparedStatement smt = con.prepareStatement("SELECT COUNT(*) FROM USERS U JOIN USERROLE UR ON U.ID = UR.USERID "
          + "JOIN ROLE R ON UR.ROLEID = R.ID " + "WHERE R.NAME = 'ADMIN'");

      ResultSet rs = smt.executeQuery();

      rs.next();

      return rs.getInt(1);
    }
  }

  public List<Users> getUsers() throws SQLException {

    List<Users> result = new ArrayList<>();

    try (Connection con = Database.getConnection()) {

      Statement smt = con.createStatement();

      ResultSet rs = smt.executeQuery(
          "SELECT * FROM USERS U " + "JOIN USERROLE UR ON U.ID = UR.USERID " + "JOIN ROLE R ON UR.ROLEID = R.ID");

      while (rs.next()) {

        Users u = new Users();

        u.setID(rs.getInt(1));
        u.setNAME(rs.getString(2));
        u.setSURNAME(rs.getString(3));
        u.setUSERNAME(rs.getString(4));
        u.setPASSWORD(rs.getString(5));
        u.setBIRTHDATE(rs.getDate(6));

        Role r = new Role();

        r.setID(rs.getInt(9));
        r.setNAME(rs.getString(10));

        u.setROLE(r);

        result.add(u);
      }

      return result;
    }
  }

  public List<Role> getRoles() throws SQLException {

    List<Role> result = new ArrayList<>();

    try (Connection con = Database.getConnection()) {

      Statement smt = con.createStatement();

      ResultSet rs = smt.executeQuery("SELECT * FROM ROLE");

      while (rs.next()) {

        Role u = new Role();

        u.setID(rs.getInt(1));
        u.setNAME(rs.getString(2));

        result.add(u);
      }

      return result;
    }
  }

  @SuppressWarnings("unchecked")
  public List<Products> getProducts(int categoryId) throws SQLException, IOException {
    // DenemeMethod();
    Session session = null;
    List<Products> products = null;
    try {
      session = HibernateHelper.getSession();

      Criteria criteriaQuery = session.createCriteria(Products.class);
      Criterion criterion1 = Restrictions.eq("categoryid", categoryId);
      criteriaQuery.add(criterion1);
      products = (List<Products>) criteriaQuery.list();

      for (int i = 0; i < products.size(); i++) {
        List<ProductImage> images = getProductImages(products.get(i).getId());
        products.get(i).setImages(images);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
    }
    return products;

    // DenemeMethod();

    // List<Products> result = new ArrayList<>();
    // try (Connection con = Database.getConnection()) {
    // PreparedStatement smt = con.prepareStatement("SELECT * FROM PRODUCT WHERE
    // CATEGORYID = ?");
    // smt.setInt(1, categoryId);
    // ResultSet rs = smt.executeQuery();
    // while (rs.next()) {
    // Products product = new Products();
    // product.setId(rs.getInt(1));
    // product.setName(rs.getString(2));
    // String lenghtControl = product.getName();
    // if (lenghtControl.length() > 64) {
    // product.setShowName(lenghtControl.substring(0, 64) + "...");
    // } else {
    // product.setShowName(product.getName());
    // }
    // product.setSupplierId(rs.getInt(3));
    // product.setPrice(rs.getFloat(4));
    // // kategoriyi bilmemize ra�men sonra tekrar kullan�m i�in
    // // tutabiliriz.
    // // /sel�uk
    // product.setCategoryId(rs.getInt(5));
    //
    // // Eski �al��an kod
    // // Ekran g�r�nt�s� i�in a�t�k
    // List<String> images = new ArrayList<String>();
    // //
    // images.add("http://images.hepsiburada.net/assets/Bilgisayar/500/9478601506866.jpg");
    // //
    // images.add("http://images.hepsiburada.net/assets/Bilgisayar/500/9513959587890.jpg");
    // //
    // images.add("http://images.hepsiburada.net/assets/Bilgisayar/500/9525990359090.jpg");
    // images.add("/resources/images/1.jpg");
    // product.setImages(images);
    //
    // // Ekran goruntusu i�in kapatt�k
    // // product.setImages(getProductImages(product.getId()));
    // result.add(product);
    // }
    //
    // return result;
    // }
  }

  public void DenemeMethod() throws SQLException, IOException {

    FileInputStream is = new FileInputStream("C:\\Users\\Selcuk\\Desktop\\1.jpg");
    byte[] bytelar = convertByteArray(is);

    Session session = HibernateHelper.getSession();

    @SuppressWarnings({ "deprecation", "rawtypes" })
    List list = session.createCriteria(ProductImage.class).add(Restrictions.eq("id", 2)).list();

    if (!list.isEmpty()) {
      session.beginTransaction();

      ProductImage image = (ProductImage) list.get(0);
      image.setImage2(bytelar);

      session.saveOrUpdate(image);

      session.getTransaction().commit();
    }

    // FileInputStream is = null;
    //// try (Connection con = Database.getConnection();) {
    ////
    //// // PreparedStatement smt = con.prepareStatement("UPDATE
    // PRODUCTIMAGE
    //// // SET
    //// // IMAGE = ? WHERE ID = ?");
    ////
    //// PreparedStatement smt = con
    //// .prepareStatement("INSERT INTO PRODUCTIMAGE
    // VALUES(PRODUCTIMAGE_SEQ.NEXTVAL, ?, ?)");
    ////
    // is = new FileInputStream(
    // "C:\\Users\\tosh�ba\\workspace\\ShoppingCard\\WebContent\\resources\\images\\1.jpg");
    //
    //// byte[] buffer = new byte[1024];
    //// int count = 0;
    //// String str = "";
    //// while ((count = is.read(buffer, 0, buffer.length)) > 0) {
    //// str += new String(buffer, 0, count);
    //// }
    // smt.setInt(1, 1);
    //
    // byte[] bytelar = convertByteArray(is);
    //
    // // kodun �al��mayan k�sm� byte arraydan bloba ge�i�
    // // SerialBlob sb = new SerialBlob(bytelar);
    // // smt.setBlob(2, sb);
    //
    //// InputStream is2 = new ByteArrayInputStream((byte[]) bytelar);
    //// smt.setBlob(2, is2);
    //
    // Blob blob = con.createBlob();
    // blob.setBytes(0, bytelar);
    // smt.setBlob(2, blob);
    //
    // smt.executeUpdate();
    // is.close();
    // }
  }

  public byte[] convertByteArray(FileInputStream is) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    int nRead;
    byte[] data = new byte[16384];

    while ((nRead = is.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }

    buffer.flush();

    return buffer.toByteArray();
  }

  public Integer getProductRating(int id) throws SQLException {
    try (Connection con = Database.getConnection()) {

      PreparedStatement smt = con
          .prepareStatement("SELECT ROUND(AVG(RATINGPOINT)) " + "FROM PRODUCTRATING WHERE PRODUCTID = ?");

      smt.setInt(1, id);

      ResultSet rs = smt.executeQuery();
      int sonuc = 0;
      if (rs.next()) {
        sonuc = rs.getInt(1);
      }

      return sonuc;
    }
  }

  public void setProductRating(int productId, int UserId, int point) throws SQLException {
    boolean sonuc = false;
    try (Connection con = Database.getConnection()) {

      PreparedStatement smt1 = con
          .prepareStatement("SELECT COUNT(*) FROM PRODUCTRATING WHERE PRODUCTID = ? AND USERID = ? ");
      smt1.setInt(1, productId);
      smt1.setInt(2, UserId);
      ResultSet rs = smt1.executeQuery();

      // PreparedStatement smt2 = null;

      // Debug modda rs.next ifadesi false a giriyor. Deneme ama�l�
      // konsola yazd�rd�m. debug yapmay�nca do�ru
      // �al���yor.

      int ratingVar = 0;
      if (rs.next()) {
        ratingVar = rs.getInt(1);
      }
      if (ratingVar == 1) {
        sonuc = true;
      } else {
        sonuc = false;
      }
      System.out.println(sonuc);

      // smt2 = con.prepareStatement("UPDATE PRODUCTRATING SET RATINGPOINT
      // = ?
      // WHERE PRODUCTID = ? AND USERID = ?");
      // smt2.setInt(1, point);
      // smt2.setInt(2, productId);

      // else {
      // int satirIki = 0;
      // // smt2 = con.prepareStatement("INSERT INTO PRODUCTRATING
      // // VALUES(PRODUCTRATING_SEQ.NEXTVAL, ? , ? , ?)");
      // // smt2.setInt(1, productId);
      // // smt2.setInt(2, UserId);
      // // smt2.setInt(3, point);
      // }

      // int result = smt2.executeUpdate();
      Method2(productId, UserId, point, sonuc);
    }

  }

  public void Method2(int productId, int UserId, int point, boolean sonuc) throws SQLException {
    try (Connection con = Database.getConnection()) {
      PreparedStatement smt = null;
      int durum = 0;
      if (sonuc) {
        smt = con.prepareStatement("UPDATE PRODUCTRATING SET RATINGPOINT = ? WHERE PRODUCTID = ? AND USERID = ?");
        smt.setInt(1, point);
        smt.setInt(2, productId);
        smt.setInt(3, UserId);
        durum = 1;
      } else {
        smt = con.prepareStatement("INSERT INTO PRODUCTRATING VALUES(PRODUCTRATING_SEQ.NEXTVAL, ? , ? , ?)");
        smt.setInt(1, productId);
        smt.setInt(2, UserId);
        smt.setInt(3, point);
        durum = 2;
      }
      int result = smt.executeUpdate();
      if (result == 1) {
        if (durum == 1) {
          FacesContext context = FacesContext.getCurrentInstance();

          context.addMessage(null, new FacesMessage("", "Puan�n�z g�ncellendi"));
        } else if (durum == 2) {
          FacesContext context = FacesContext.getCurrentInstance();

          context.addMessage(null, new FacesMessage("", "Puan�n�z sisteme kaydedildi"));
        }

      } else {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("", "�uan Puan�n�z al�namamaktad�r"));
      }
    }
  }

  @SuppressWarnings({ "unchecked" })
  public List<ProductImage> getProductImages(int productId) throws SQLException, IOException {

    Session session = HibernateHelper.getSession();
    return (List<ProductImage>) session.createCriteria(ProductImage.class).add(Restrictions.eq("productId", productId))
        .list();

    // List<ProductImage> result = new ArrayList<>();
    // try (Connection con = Database.getConnection()) {
    // PreparedStatement smt = con.prepareStatement("SELECT * FROM
    // PRODUCTIMAGE WHERE PRODUCTID = ?");
    // smt.setInt(1, productId);
    // ResultSet rs = smt.executeQuery();
    // while (rs.next()) {
    // ProductImage productImage = new ProductImage();
    // productImage.setId(rs.getInt(1));
    // productImage.setProductId(rs.getInt(2));
    // byte[] buffer = rs.getBytes(3);
    // InputStream is = new ByteArrayInputStream((byte[]) buffer);
    // productImage.setImage(new DefaultStreamedContent(is, "image/png"));
    //
    // // Y�ntem 1
    // // productImage.setImageFile(rs.getBlob(3));
    //
    // // Y�ntem 2
    // // Blob blob = rs.getBlob(3);
    // // InputStream is = blob.getBinaryStream();
    // // BufferedImage image = ImageIO.read(is);
    // // productImage.setImage(image);
    //
    // // Y�ntem 3
    // // Blob blob = rs.getBlob(3);
    // // InputStream is = null;
    // // if (blob != null) {
    // // is = blob.getBinaryStream();
    // // }
    //
    // // StreamedContent scImage = new DefaultStreamedContent(is,
    // // "image/jpg");
    // // productImage.setImage(scImage);
    // result.add(productImage);
    // }
    //
    // return result;
    // }
  }

  public ArrayList<ProductDetail> getProductDetail(int id) throws SQLException {
    ArrayList<ProductDetail> result = new ArrayList<ProductDetail>();
    try (Connection con = Database.getConnection()) {
      PreparedStatement smt = con
          .prepareStatement("SELECT PD.CATEGORYID, PD.NAME, PDM.* FROM PRODUCTDETAILMAPPING PDM JOIN "
              + "PRODUCTDETAIL PD ON PD.ID = PDM.PRODUCTDETAILID WHERE PDM.PRODUCTID= ?");
      smt.setInt(1, id);
      ResultSet rs = smt.executeQuery();
      while (rs.next()) {
        ProductDetail productDetail = new ProductDetail();
        ProductDetailMapping productDetailMapping = new ProductDetailMapping();
        productDetail.setId(rs.getInt("PRODUCTDETAILID"));
        productDetail.setCategoryId(rs.getInt("CATEGORYID"));
        productDetail.setName(rs.getString("NAME"));

        productDetailMapping.setId(rs.getInt("ID"));
        productDetailMapping.setProductId(rs.getInt("PRODUCTID"));
        productDetailMapping.setProductDetailId(rs.getInt("PRODUCTDETAILID"));
        productDetailMapping.setValue(rs.getString("VALUE"));

        productDetail.setProductDetailMapping(productDetailMapping);
        // productDetail.setProductDetailMapping();

        result.add(productDetail);
      }

      return result;
    }
  }

  public void queryCategory() {

    Session session = null;

    try {

      session = HibernateHelper.getSession();

      System.out.println("deneme");
      ////////////////////////////
      // SQL
      // SQLQuery query = session.createSQLQuery("SELECT * FROM CATEGORY
      // c");
      // query.addEntity(Category.class);
      // List<Category> list = (List<Category>) query.list();
      // for (Object object : list) {
      // Category category = (Category) object;
      // System.out.println(category);
      // }

      // HQL
      // Query q = session.createQuery("from Category");
      // List<Category> list2 = (List<Category>)q.list();

      // Criteria
      // Criteria query2 = session.createCriteria(Category.class);

      // Criterion c1 = Restrictions.eqOrIsNull("Name", "deneme1");
      // Criterion c2 = Restrictions.eqOrIsNull("Name", "deneme2");

      // query2.add(Restrictions.or(c1, c2));

      // query2.setProjection(Projections.property("name"));
      // query2.setProjection(Projections.property("id"));
      //
      // query2.list();

      ////////////////////////////////////////////////////////////

      // insert
      // try {
      // Category category1 = new Category();
      // category1.setName("deneme");
      //
      // Category category2 = new Category();
      // category2.setName("deneme");
      //
      // session.beginTransaction();
      // session.saveOrUpdate(category1);
      // session.saveOrUpdate(category2);
      // session.getTransaction().commit();
      // } catch (Exception e) {
      // session.getTransaction().rollback();
      // }

      // Update
      // try {
      //
      // Query query = session.createQuery("from Category Where id =
      // :id");
      // query.setInteger("id", 1);
      // List<Category> list = (List<Category>) query.list();
      // Category category = list.get(0);
      //
      // category = new Category();
      // category.setId(6);
      // category.setName("Elektronik3");
      //
      // session.beginTransaction();
      // session.saveOrUpdate(category);
      // session.getTransaction().commit();
      //
      // } catch (Exception e) {
      // session.getTransaction().rollback();
      // }

      try (FileInputStream is = new FileInputStream("D:\\Chrysanthemum.jpg");) {

        byte[] imageBytes = IOUtils.toByteArray(is);

        ProductImage image = new ProductImage();
        image.setImage2(imageBytes);
        image.setProductId(1);

        session.beginTransaction();
        session.saveOrUpdate(image);
        session.getTransaction().commit();

      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null)
        session.close();
    }
  }

  public void Where(List<Category> categoryList, Predicate<Category> contition) {
    for (Category category : categoryList) {
      if (contition.test(category)) {
        System.out.println(category.getName());
      }
    }
  }
}