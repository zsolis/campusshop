package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class CampusAdministrator extends Administrator{
	private String phoneNumber;
	private String superNote;
	
	public CampusAdministrator() {}
	public CampusAdministrator(String account, String password, String name, String phoneNumber) {
		super(account, password, name);
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSuperNote() {
		return superNote;
	}
	public void setSuperNote(String superNote) {
		this.superNote = superNote;
	}
}
