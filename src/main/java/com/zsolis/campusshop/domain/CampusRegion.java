package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class CampusRegion {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String name;
	private String description;
	private CampusRegionStatus status = CampusRegionStatus.normal;
	@ManyToOne
	@JoinColumn(name = "CAMPUS_ID", nullable = false)
	private Campus campus;
	
	public CampusRegion() {}
	public CampusRegion(String name, String description, Campus campus) {
		this.name = name;
		this.description = description;
		this.campus = campus;
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
	public CampusRegionStatus getStatus() {
		return status;
	}
	public void setStatus(CampusRegionStatus status) {
		this.status = status;
	}
	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
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
		if (!(obj instanceof CampusRegion))
			return false;
		CampusRegion other = (CampusRegion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
