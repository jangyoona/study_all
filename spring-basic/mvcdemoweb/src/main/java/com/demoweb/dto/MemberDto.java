package com.demoweb.dto;

import java.util.Date;

public class MemberDto {
	private String memberId;
	private String passwd;
	private String email;
	private String userType;
	private Date regDate;
	private boolean active;
	
	
	public void setMemberId(String memberid) {
		this.memberId = memberid;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getMemberId() {
		return memberId;
	}
	public String getPasswd() {
		return passwd;
	}
	public String getEmail() {
		return email;
	}
	public String getUserType() {
		return userType;
	}
	public Date getRegDate() {
		return regDate;
	}
	public boolean isActive() {
		return active;
	}
	
	
	@Override
	public String toString() {
		return String.format("[%s][%s][%s][%s][%b]", memberId, email, userType, regDate, active);
	}
}
