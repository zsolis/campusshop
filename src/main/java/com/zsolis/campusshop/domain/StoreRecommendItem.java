package com.zsolis.campusshop.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class StoreRecommendItem {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 4489887008895234216L;
		@Column(name = "STORE_ID")
		private Long storeId;
		@Column(name = "ITEM_ID")
		private Long itemId;
		
		public Id() {}
		public Id(Long storeId, Long itemId) {
			this.storeId = storeId;
			this.itemId = itemId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((itemId == null) ? 0 : itemId.hashCode());
			result = prime * result
					+ ((storeId == null) ? 0 : storeId.hashCode());
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
			if (storeId == null) {
				if (other.storeId != null)
					return false;
			} else if (!storeId.equals(other.storeId))
				return false;
			return true;
		}
	}
	@EmbeddedId
	private Id id = new Id();
	@ManyToOne
	@JoinColumn(name = "STORE_ID",
			insertable = false,
			updatable = false)
	private Store store;
	@ManyToOne
	@JoinColumn(name = "ITEM_ID",
			insertable = false,
			updatable = false)
	private Item item;
	private Long priority;
	
	public StoreRecommendItem() {}
	public StoreRecommendItem(Store store, Item item, Long priority) {
		this.store = store;
		this.item = item;
		this.priority = priority;
		
		this.id.itemId = item.getId();
		this.id.storeId = store.getId();
	}

	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
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
		if (!(obj instanceof StoreRecommendItem))
			return false;
		StoreRecommendItem other = (StoreRecommendItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
