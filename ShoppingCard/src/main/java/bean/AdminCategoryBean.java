package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.TreeNode;

import entity.Category;

@ManagedBean
@ViewScoped
public class AdminCategoryBean {
  private int id;
  private String name;
  private int parentid;
  private TreeNode selectedNode;
  private TreeNode category;
  private String newCategoryName;

  public String getNewCategoryName() {
    return newCategoryName;
  }

  public void setNewCategoryName(String newCategoryName) {
    this.newCategoryName = newCategoryName;
  }

  public TreeNode getSelectedNode() {
    return selectedNode;
  }

  public void setSelectedNode(TreeNode selectedNode) {
    this.selectedNode = selectedNode;
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

  public int getParentid() {
    return parentid;
  }

  public void setParentid(int parentid) {
    this.parentid = parentid;
  }

  @PostConstruct
  public void init() {
    Category c = new Category();
    setCategory(c.GetCategory());
  }

  public void Add() {
    int parentId = 0;

    if (selectedNode != null) {
      Category ct = (Category) selectedNode.getData();
      parentId = ct.getId();
    }

    Category c = new Category();
    c.setName(newCategoryName);
    c.setParentid(parentId);
    c.Add();
  }

  public void Update() {
    if (selectedNode != null) {
      Category ct = (Category) selectedNode.getData();
      ct.Update();
    }
  }

  public TreeNode getCategory() {
    return this.category;
  }

  public void setCategory(TreeNode node) {
    this.category = node;
  }
}
