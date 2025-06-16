package member.dto;

import java.sql.Date;

public class MemberDTO {
	
	private int mno;
	private String id;
	private String pw;
	private String mname;
	private int birthday;
	private String phonenumber;
	private String email;
	private String address;
	private Date regidate;
	
	public int getMno() {
		return mno;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getMname() {
		return mname;
	}
	public int getBirthday() {
		return birthday;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	public Date getRegidate() {
		return regidate;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setRegidate(Date regidate) {
		this.regidate = regidate;
	}
	
	
}
