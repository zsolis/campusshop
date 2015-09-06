package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class CashOrder extends Order{
	public CashOrder() {}
	public CashOrder(Address address, User user, Store store, String userNote,
			String storeNote, OrderStatus status) {
		super(address, user, store,userNote,storeNote, status);
	}
}
