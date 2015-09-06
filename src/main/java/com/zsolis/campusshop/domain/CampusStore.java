package com.zsolis.campusshop.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class CampusStore {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 3507627878431362563L;
		
		@Column(name = "CAMPUS_ID")
		private Long campusId;
		@Column(name = "STORE_ID")
		private Long storeId;
		
		public Id() {}
		public Id(Long campusId, Long storeId) {
			this.campusId = campusId;
			this.storeId = storeId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((storeId == null) ? 0
							: storeId.hashCode());
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
			if (storeId == null) {
				if (other.storeId != null)
					return false;
			} else if (!storeId.equals(other.storeId))
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
	@JoinColumn(name = "STORE_ID",
			insertable = false,
			updatable = false)
	private Store store;
	private Long priority;
	private CampusStatus status = CampusStatus.checking;
	
	public CampusStore() {}
	public CampusStore(Campus campus, Store store, Long priority) {
		this.campus = campus;
		this.store = store;
		this.priority = priority;
		
		this.id.campusId = campus.getId();
		this.id.storeId = store.getId();
	}
	
	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	
	public CampusStatus getStatus() {
		return status;
	}
	public void setStatus(CampusStatus status) {
		this.status = status;
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
		if (!(obj instanceof CampusStore))
			return false;
		CampusStore other = (CampusStore) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
