package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class Address {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String detail;
	private String phoneNumber;
	private String name;
	private AddressStatus status = AddressStatus.normal;
	@ManyToOne
	@JoinColumn(name = "CAMPUS_ID", nullable = false)
	private Campus campus;
	/**
	 * 可为null，null值为其他
	 */
	@ManyToOne
	@JoinColumn(name = "CAMPUSREGION_ID")
	private CampusRegion campusRegion;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	public Address() {}
	public Address(Campus campus, CampusRegion campusRegion, String detail,
			String phoneNumber, String name, User user) {
		this.campus = campus;
		this.campusRegion = campusRegion;
		this.detail = detail;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.user = user;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public CampusRegion getCampusRegion() {
		return campusRegion;
	}
	public void setCampusRegion(CampusRegion campusRegion) {
		this.campusRegion = campusRegion;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
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
	public AddressStatus getStatus() {
		return status;
	}
	public void setStatus(AddressStatus status) {
		this.status = status;
	}
	
	/**
	 * @return
	 * 返回拼接好的完整地址
	 */
	public String getFinalAddress() {
		String finalAddress = campus.getName() + " ";
		if(campusRegion != null) {
			finalAddress += campusRegion.getName() + " ";
		}
		finalAddress += detail + " " + name + " " + phoneNumber;
		return finalAddress;
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
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
