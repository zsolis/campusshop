package com.zsolis.campusshop.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class CampusRecommendImage {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -7747385934728059793L;
		@Column(name = "CAMPUS_ID")
		private Long campusId;
		@Column(name = "IMAGE_ID")
		private Long imageId;
		
		public Id() {}
		public Id(Long campusId, Long imageId) {
			this.campusId = campusId;
			this.imageId = imageId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((imageId == null) ? 0
							: imageId.hashCode());
			result = prime * result
					+ ((campusId == null) ? 0 : campusId.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Id other = (Id) obj;
			if (imageId == null) {
				if (other.imageId != null)
					return false;
			} else if (!imageId.equals(other.imageId))
				return false;
			if (campusId == null) {
				if (other.campusId != null)
					return false;
			} else if (!campusId.equals(other.campusId))
				return false;
			return true;
		}
	}
	@EmbeddedId
	private Id id = new Id();
	@ManyToOne
	@JoinColumn(name = "CAMPUS_ID",
			insertable = false,
			updatable = false)
	private Campus campus;
	@ManyToOne
	@JoinColumn(name = "IMAGE_ID",
			insertable = false,
			updatable = false)
	private AdministratorImage image;
	private Long location;
	private String url;
	
	public CampusRecommendImage() {}
	public CampusRecommendImage(Campus campus, AdministratorImage image, Long location, String url) {
		this.campus = campus;
		this.image = image;
		this.location = location;
		this.url = url;
		
		this.id.campusId = campus.getId();
		this.id.imageId = image.getId();
	}

	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	public AdministratorImage getImage() {
		return image;
	}
	public void setImage(AdministratorImage image) {
		this.image = image;
	}
	public Long getLocation() {
		return location;
	}
	public void setLocation(Long location) {
		this.location = location;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
		if (!(obj instanceof CampusRecommendImage))
			return false;
		CampusRecommendImage other = (CampusRecommendImage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
