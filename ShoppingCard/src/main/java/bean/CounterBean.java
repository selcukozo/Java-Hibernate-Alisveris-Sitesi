package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ojdbc.Database;
import com.ojdbc.Repository;

@ManagedBean
@RequestScoped
public class CounterBean {

	public static int counter;

	public int getCounter() {
		return counter;
	}
	
	public String getCategoryMenu(){
		return getCategoryMenu(0);
	}

	public String getCategoryMenu(int parrentId) {
		String menu = "";
		
		menu += "<ul " + (parrentId == 0 ? "class='nav navbar-nav'" : "class='dropdown-menu'") + ">";

		try (Connection con = Database.getConnection()) {

			PreparedStatement smt = con.prepareStatement("SELECT * FROM CATEGORY WHERE PARENTID = ?");
			smt.setInt(1, parrentId);
			ResultSet rs = smt.executeQuery();

			while (rs.next()) {
				PreparedStatement smt2 = con
						.prepareStatement("SELECT count(*) FROM CATEGORY WHERE PARENTID = " + rs.getInt("ID"));
				ResultSet rs2 = smt2.executeQuery();
				rs2.next();
				if (rs2.getInt(1) > 0) {
					menu += "<li "+(rs.getInt("PARENTID")==0?"class='dropdown'":"class='dropdown-submenu'")+"> <a href='?categoryId=" + rs.getInt("ID")
							+ "' class='dropdown-toggle'  data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>"
							+ rs.getString("NAME") + (rs.getInt("PARENTID")==0?"<span class='caret'></span>":"")+"</a> "
							+ getCategoryMenu(rs.getInt("ID")) + "</li>";
				} else
					menu += "<li><a href='?categoryId=" + rs.getInt("ID") + "'>" + rs.getString("NAME") + "</a></li>";

			}

		} catch (Exception e) {
		}

		menu += "</ul>";

		return menu;
	}

	public CounterBean() {
	    (new Repository()).queryCategory();
	}
}