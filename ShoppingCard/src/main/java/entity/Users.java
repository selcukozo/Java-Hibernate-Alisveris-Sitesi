package entity;

import java.util.Date;

public class Users {

	private int ID;
	private String NAME;
	private String SURNAME;
	private String USERNAME;
	private String PASSWORD;
	private Date BIRTHDATE;
	
	private Role ROLE;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getSURNAME() {
		return SURNAME;
	}

	public void setSURNAME(String sURNAME) {
		SURNAME = sURNAME;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public Date getBIRTHDATE() {
		return BIRTHDATE;
	}

	public void setBIRTHDATE(Date bIRTHDATE) {
		BIRTHDATE = bIRTHDATE;
	}

	public Role getROLE() {
		return ROLE;
	}
	
	public void setROLE(Role rOLE) {
		ROLE = rOLE;
	}
}
