package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class TemporaryUser extends User{
	public TemporaryUser() {}
	
	public String getName() {
		return "临时用户：" + super.getId();
	}
}
