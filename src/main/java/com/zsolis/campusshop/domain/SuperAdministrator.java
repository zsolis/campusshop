package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class SuperAdministrator extends Administrator{
	public SuperAdministrator() {}
	public SuperAdministrator(String account, String password, String name) {
		super(account, password, name);
	}
}
