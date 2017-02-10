package entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialException;

@Entity
@Table(name = "PRODUCTIMAGE")
public class ProductImage {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
  @SequenceGenerator(name = "G1", sequenceName = "PRODUCTIMAGE_SEQ")
  @Column(name = "ID")
  private int id;

  @Column(name = "PRODUCTID")
  private int productId;

  @Column(name = "IMAGE")
  private byte[] image2;

  public byte[] getImage2() {
    return image2;
  }

  public void setImage2(byte[] image2) {
    this.image2 = image2;
  }

  public ProductImage() {

  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id")
  private Products products;

  public Products getProducts() {
    return products;
  }

  public void setProducts(Products products) {
    this.products = products;
  }

  // @Transient
  // private DefaultStreamedContent renderedImage;
  @OneToMany
  public String getRenderedImage() throws SerialException, SQLException, IOException {
    // hocanýn örneði
    // return "http://localhost:8080//Shopping" + "/servlet" + "?id" = id;
    // Çalýþan kod
    // return "http://localhost:8080/CustomServlet_Deneme1/Custom?id=1";

    // Çalýþan kod deneme için kapattýk
    byte[] databaseImage = getImage2();
    File file = new File("C://Users//Selcuk//Desktop//Gecici//Olustur.jpg");
    String fileName = "Olustur";
    if (file.exists()) {
      int i = 0;
      do {
        i++;
        file = new File("C://Users//Selcuk//Desktop//Gecici//Olustur(" + i + ").jpg");
        fileName = "Olustur(" + i + ")";
      } while (file.exists());
    }
    FileOutputStream fos = new FileOutputStream(file);

    fos.write(databaseImage, 0, databaseImage.length);
    fos.flush();
    System.out.println("OK!");
    fos.close();

    return "http://localhost:8080/CustomServlet_Deneme1/Custom?id=" + getId() + "&fileName=" + fileName;
    // End:Çalýþan kod deneme için kapattýk

    // return "http://localhost:8080/ShoppingCard/ImageServlet";
    // try (FileInputStream is = new
    // FileInputStream("C:\\Users\\Selcuk\\Desktop\\2.jpg");) {
    //
    // // byte[] buffer = new byte[1024];
    // // int count = 0;
    // // String str = "";
    // // while ((count = is.read(buffer, 0, buffer.length)) > 0) {
    // // str += new String(buffer, 0, count);
    // // }
    // // return str;
    //
    // // byte[] bytelar = str.getBytes();
    //
    // byte[] imageBytes = IOUtils.toByteArray(is);
    //
    // Blob blob = new SerialBlob(imageBytes);
    // return new DefaultStreamedContent(blob.getBinaryStream());
    //
    // // Blob blob = new SerialBlob(imageBytes);
    // // return new DefaultStreamedContent(blob.getBinaryStream());
    // }

    // Blob blob = new SerialBlob(getImage2());
    // return new DefaultStreamedContent(blob.getBinaryStream());

    // String read = IOUtils.toString(getImage2());
    // Blob blob = new SerialBlob(IOUtils.toByteArray(read));
    // return new DefaultStreamedContent(blob.getBinaryStream());

    // InputStream is = new ByteArrayInputStream(getImage2());
    // return new DefaultStreamedContent(is, "image/jpg");

    // InputStream is = new ByteArrayInputStream(getImage2());
    // Blob blob = new SerialBlob(IOUtils.toByteArray(is));
    // return new DefaultStreamedContent(blob.getBinaryStream());

    // InputStream is = new ByteArrayInputStream(getImage2());
    // return new DefaultStreamedContent(is);

    // return new DefaultStreamedContent(new ByteArrayInputStream(getImage2()));

    // byte[] bytelar = getImage2();
    // String decoded;
    // decoded = new String(bytelar, "UTF-8");
    //
    // byte[] bytelarbytelar = IOUtils.toByteArray(decoded);
    // Blob blob = new SerialBlob(bytelarbytelar);
    //
    // return new DefaultStreamedContent(blob.getBinaryStream());

    // farklý kod path kýsmý normalde masaüstü þeklindeydi
    // byte[] databaseImage = getImage2();
    // File file = new File(
    // "C:\\Users\\Selcuk\\workspaceEclipseNeonEE\\ShoppingCard\\WebContent\\resources\\images\\Olustur.jpg");
    // String path = "Olustur.jpg";
    // if (file.exists()) {
    // int i = 0;
    // do {
    // i++;
    // file = new File(
    // "C:\\Users\\Selcuk\\workspaceEclipseNeonEE\\ShoppingCard\\WebContent\\resources\\images\\Olustur("
    // + i
    // + ").jpg");
    // path = "Olustur(" + i + ").jpg";
    // } while (file.exists());
    // }
    // FileOutputStream fos = new FileOutputStream(file);
    //
    // fos.write(databaseImage, 0, databaseImage.length);
    // fos.flush();
    // System.out.println("OK!");
    // fos.close();
    // return path;

    // farklý kod
    // FileInputStream is = new FileInputStream(file);
    // byte[] imageBytes = IOUtils.toByteArray(is);
    // Blob blob = new SerialBlob(imageBytes);
    // return new DefaultStreamedContent(blob.getBinaryStream());
    // farklý kod
    // InputStream in = new ByteArrayInputStream(getImage2());
    // StreamedContent sc = new DefaultStreamedContent(in, "image/jpeg");
    // return sc;
    // farklý kod
    // byte[] bytelar = getImage2();
    // Image image = ImageIO.read(new ByteArrayInputStream(bytelar));
    // return image;
    // farklý kod
    // byte[] bytelar = getImage2();
    // return new ByteArrayInputStream(bytelar);

    // byte[] bytelar = getImage2();
    // InputStream is = new ByteArrayInputStream(bytelar);
    // return new DefaultStreamedContent(is);
  }

  // private Blob imageFile;
  // private BufferedImage image;
  // private StreamedContent image;
  //
  // public StreamedContent getImage() {
  // return image;
  // }
  //
  // public void setImage(StreamedContent image) {
  // this.image = image;
  // }

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
}
