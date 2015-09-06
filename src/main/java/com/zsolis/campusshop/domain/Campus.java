package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class Campus {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String name;
	private String description;
	private CampusStatus status = CampusStatus.checking;
	@ManyToOne
	@JoinColumn(name = "CITY_ID", nullable = false)
	private City city;
	@ManyToOne
	@JoinColumn(name = "ADMINISTRATOR_ID", nullable = false)
	private Administrator administrator;
	
	public Campus() {}
	public Campus(String name, String description, City city, Administrator administrator) {
		this.name = name;
		this.description = description;
		this.city = city;
		this.administrator = administrator;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public CampusStatus getStatus() {
		return status;
	}
	public void setStatus(CampusStatus status) {
		this.status = status;
	}
	public Administrator getAdministrator() {
		return administrator;
	}
	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
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
		if (!(obj instanceof Campus))
			return false;
		Campus other = (Campus) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
