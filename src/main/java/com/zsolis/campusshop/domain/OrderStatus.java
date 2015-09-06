package com.zsolis.campusshop.domain;

public enum OrderStatus {
	beforePay,
	beforeAccept,
	groupWait,
	beforeDelivery,
	beforeArrive,
	beforeComment,
	finish,
	userCancel,
	storeCancel,
	administratorCancel
}
