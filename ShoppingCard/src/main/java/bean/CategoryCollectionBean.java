package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ojdbc.HibernateHelper;

import entity.Category;

@ManagedBean
@SessionScoped
public class CategoryCollectionBean {
  private List<Category> categories;

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  @PostConstruct
  public void init() {
    Session session = null;
    try {
      session = HibernateHelper.getSession();
      Criteria criteriaQuery = session.createCriteria(Category.class);
      List<Category> categories = (List<Category>) criteriaQuery.list();
      setCategories(categories);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

}
