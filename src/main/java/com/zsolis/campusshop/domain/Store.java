package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class Store {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	@Column(unique = true)
	private String account;
	private String password;
	private String phoneNumber;
	private String name;
	private String address;
	private String description;
	private String statement;
	private Float minimumAmount;
	private Float deliveryFee;
	private String imagePath;
	@Column(unique = true)
	private String authToken;
	
	public Store() {}
	public Store(String account,
			String password,
			String phoneNumber,
			String name,
			String address) {
		this.account = account;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.address = address;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public Float getMinimumAmount() {
		return minimumAmount;
	}
	public void setMinimumAmount(Float minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	public Float getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
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
		if (!(obj instanceof Store))
			return false;
		Store other = (Store) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
