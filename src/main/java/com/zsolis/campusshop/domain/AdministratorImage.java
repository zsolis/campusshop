package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class AdministratorImage {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String path;
	@ManyToOne
	@JoinColumn(name = "ADMINISTRATOR_ID", nullable = false)
	private Administrator administrator;
	
	public AdministratorImage() {}
	public AdministratorImage(String path, Administrator administrator) {
		this.path = path;
		this.administrator = administrator;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
		if (!(obj instanceof AdministratorImage))
			return false;
		AdministratorImage other = (AdministratorImage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
