package com.zsolis.campusshop.domain;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
public class UserFavoriteStore {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -4343821107133842407L;
		@Column(name = "USER_ID")
		private Long userId;
		@Column(name = "STORE_ID")
		private Long storeId;
		
		public Id() {}
		public Id(Long userId, Long storeId) {
			this.userId = userId;
			this.storeId = storeId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((storeId == null) ? 0 : storeId.hashCode());
			result = prime * result
					+ ((userId == null) ? 0 : userId.hashCode());
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
			if (userId == null) {
				if (other.userId != null)
					return false;
			} else if (!userId.equals(other.userId))
				return false;
			return true;
		}
	}
	@EmbeddedId
	private Id id = new Id();
	@ManyToOne
	@JoinColumn(name = "USER_ID",
			insertable = false,
			updatable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "STORE_ID",
			insertable = false,
			updatable = false)
	private Store store;
	private Date date = new Date();
	
	public UserFavoriteStore() {}
	public UserFavoriteStore(User user, Store store) {
		this.user = user;
		this.store = store;
		
		this.id.storeId = store.getId();
		this.id.userId = user.getId();
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
		if (!(obj instanceof UserFavoriteStore))
			return false;
		UserFavoriteStore other = (UserFavoriteStore) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
