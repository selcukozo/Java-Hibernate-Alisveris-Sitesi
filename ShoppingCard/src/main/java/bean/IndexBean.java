package bean;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.PieChartModel;

import com.ojdbc.Repository;

@ManagedBean
@SessionScoped
public class IndexBean {

  private PieChartModel pieModel;

  public PieChartModel getPieModel() {
    return pieModel;
  }

  public void setPieModel(PieChartModel pieModel) {
    this.pieModel = pieModel;
  }

  private void createPieModel() throws SQLException, IOException {

    Repository repo = new Repository();

    pieModel = new PieChartModel();

    pieModel.set("Admin", repo.getAdminCount());
    pieModel.set("Kullanýcý", repo.getUserCount());
    pieModel.set("Kayýtlý Sayfa Sayýsý", 20);
    pieModel.set("Ürünler", 20);

    pieModel.setLegendPosition("w");
  }

  public IndexBean() throws SQLException, IOException {
    createPieModel();
  }
}