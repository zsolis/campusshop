package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class RegisteredUser extends User{
	@Column(unique = true)
	private String phoneNumber;
	private String password;
	
	public RegisteredUser() {}
	public RegisteredUser(String phoneNumber, String password) {
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return phoneNumber;
	}
}
