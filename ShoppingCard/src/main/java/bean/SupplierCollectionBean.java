package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ojdbc.HibernateHelper;

import entity.Suppliers;

@ManagedBean
@SessionScoped
public class SupplierCollectionBean {
  private List<Suppliers> suppliers;

  public List<Suppliers> getSuppliers() {
    return suppliers;
  }

  public void setSuppliers(List<Suppliers> suppliers) {
    this.suppliers = suppliers;
  }

  @PostConstruct
  public void init() {
    Session session = null;
    try {
      session = HibernateHelper.getSession();
      Criteria criteriaQuery = session.createCriteria(Suppliers.class);
      List<Suppliers> suppliers = (List<Suppliers>) criteriaQuery.list();
      setSuppliers(suppliers);

    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  // public List<Suppliers> getAllSupplierList() {
  // Session session = null;
  // try {
  // session = HibernateHelper.getSession();
  //
  // Criteria CriteriaQuery = session.createCriteria(Suppliers.class);
  // // Criterion c1 = Restrictions.
  // List<Suppliers> suppliers = (List<Suppliers>) CriteriaQuery.list();
  // setSuppliers(suppliers);
  // } finally {
  // if (session != null) {
  // session.close();
  // }
  // }
  // return getSuppliers();
  // }
}
