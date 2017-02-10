package bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.ojdbc.UserIDdata;

@ManagedBean
public class RegistrationBean {

	private String userName;
	private String password;
	private int userId;

	private String name;
	private String lastname;
	private String mail;
	private String birthday;
	HashSet<Integer> userIDs;
	int year;
	int month;
	int day;
	private String userType = "Supplier";
	private String companyName;
	private String city;
	private String contact;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
//		UserIDdata ids = new UserIDdata();
//		userIDs = ((UserIDdata) ids).getIDs();
//		Random rnd = new Random();
//		int num = rnd.nextInt(10000);
//		while (userIDs.contains(num)) {
//			num = rnd.nextInt(10000);
//		}
//		userId = num;
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String insert() {

		if(lastname == null || lastname.isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage("Error", new FacesMessage("LastName can not be empty."));
		}
		else {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@db.k8j.net:1521:orcl", "java16",
						"java16")) {

					Statement smt = con.createStatement();

					ResultSet rs = smt.executeQuery("select USER_SEQ.nextval from dual");
					rs.next();
					setUserId(rs.getInt(1));

					PreparedStatement pstmt = con.prepareStatement("insert into users values(?,?,?,?,?,?,?)");
					pstmt.setInt(1, userId);
					pstmt.setString(2, name);
					pstmt.setString(3, lastname);
					pstmt.setString(4, userName);
					pstmt.setString(5, password);
					pstmt.setDate(6, Date.valueOf(birthday));
					pstmt.setString(7, mail);
					pstmt.executeUpdate();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if ("Supplier".equals(userType)) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@db.k8j.net:1521:orcl", "java16", "java16")) {
						PreparedStatement pstmt = con.prepareStatement("insert into SUPPLIER values(SUPPLIER_SEQ.NEXTVAL,?,?,NULL,?,?,?)");

						pstmt.setInt(1, userId);
						pstmt.setString(2, city);
						pstmt.setString(3, contact);
						pstmt.setInt(4, 0);
						pstmt.setString(5, companyName);
						pstmt.executeUpdate();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return "Login";
		}
		
		return "";
	}
}
