package com.zsolis.campusshop.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Order {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String userNote;
	private String storeNote;
	private Float deliveryFee;
	private Float totalPrice;
	private Date date = new Date();
	private OrderStatus status;
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", nullable = false)
	private Address address;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "STORE_ID", nullable = false)
	private Store store;
	
	public Order() {}
	public Order(Address address, User user, Store store, String userNote,
			String storeNote, OrderStatus status) {
		this.address = address;
		this.user = user;
		this.store = store;
		this.userNote = userNote;
		this.storeNote = storeNote;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getUserNote() {
		return userNote;
	}
	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}
	public String getStoreNote() {
		return storeNote;
	}
	public void setStoreNote(String storeNote) {
		this.storeNote = storeNote;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Float getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Order))
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
