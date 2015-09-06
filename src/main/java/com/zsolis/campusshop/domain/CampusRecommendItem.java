package com.zsolis.campusshop.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class CampusRecommendItem {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -2038748961195287520L;
		@Column(name = "CAMPUS_ID")
		private Long campusId;
		@Column(name = "ITEM_ID")
		private Long itemId;
		
		public Id() {}
		public Id(Long campusId, Long itemId) {
			this.campusId = campusId;
			this.itemId = itemId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((campusId == null) ? 0 : campusId.hashCode());
			result = prime * result
					+ ((itemId == null) ? 0 : itemId.hashCode());
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
			if (campusId == null) {
				if (other.campusId != null)
					return false;
			} else if (!campusId.equals(other.campusId))
				return false;
			if (itemId == null) {
				if (other.itemId != null)
					return false;
			} else if (!itemId.equals(other.itemId))
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
	@JoinColumn(name = "ITEM_ID",
			insertable = false,
			updatable = false)
	private Item item;
	private Long priority;
	
	public CampusRecommendItem() {}
	public CampusRecommendItem(Campus campus, Item item, Long priority) {
		this.campus = campus;
		this.item = item;
		this.priority = priority;
		
		this.id.campusId = campus.getId();
		this.id.itemId = item.getId();
	}

	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
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
		if (!(obj instanceof CampusRecommendItem))
			return false;
		CampusRecommendItem other = (CampusRecommendItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
