package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class AlipayDirectOrder extends Order{
	private String alipayAccount;
	private String alipyNumber;
	
	public AlipayDirectOrder() {}
	public AlipayDirectOrder(Address address, User user, Store store, String userNote,
			String storeNote, OrderStatus status) {
		super(address, user, store,userNote,storeNote, status);
	}
	
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	public String getAlipyNumber() {
		return alipyNumber;
	}
	public void setAlipyNumber(String alipyNumber) {
		this.alipyNumber = alipyNumber;
	}
}
