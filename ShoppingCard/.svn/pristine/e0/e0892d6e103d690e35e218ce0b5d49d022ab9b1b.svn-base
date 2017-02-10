package bean;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ojdbc.Repository;
import entity.Role;
import entity.Users;

@ManagedBean
@SessionScoped
public class AdminBean {
	
	private List<Users> users;
	private List<Role> roles;
	private int page;
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public List<Users> getUsers() {
		return users;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	@PostConstruct
	public void init() {
		try
		{
			Repository repo = new Repository();
			setRoles(repo.getRoles());
			setUsers(repo.getUsers());
		}
		catch (Exception e) {
		}
	}
}