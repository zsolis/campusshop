package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class TemporaryUser extends User{
	public TemporaryUser() {}
	
	public String getName() {
		return "��ʱ�û���" + super.getId();
	}
}
