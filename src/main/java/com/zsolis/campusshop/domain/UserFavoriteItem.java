package com.zsolis.campusshop.domain;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
public class UserFavoriteItem {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -3805697260626286336L;
		@Column(name = "USER_ID")
		private Long userId;
		@Column(name = "ITEM_ID")
		private Long itemId;
		
		public Id() {}
		public Id(Long userId, Long itemId) {
			this.userId = userId;
			this.itemId = itemId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((itemId == null) ? 0 : itemId.hashCode());
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
			if (itemId == null) {
				if (other.itemId != null)
					return false;
			} else if (!itemId.equals(other.itemId))
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
	@JoinColumn(name = "ITEM_ID",
			insertable = false,
			updatable = false)
	private Item item;
	private Date date = new Date();
	
	public UserFavoriteItem() {}
	public UserFavoriteItem(User user, Item item) {
		this.user = user;
		this.item = item;
		
		this.id.itemId = item.getId();
		this.id.userId = user.getId();
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
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
		if (!(obj instanceof UserFavoriteItem))
			return false;
		UserFavoriteItem other = (UserFavoriteItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
