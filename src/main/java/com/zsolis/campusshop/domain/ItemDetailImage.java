package com.zsolis.campusshop.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class ItemDetailImage {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -996572838820487728L;
		@Column(name = "ITEM_ID")
		private Long itemId;
		@Column(name = "IMAGE_ID")
		private Long imageId;
		
		public Id() {}
		public Id(Long itemId, Long imageId) {
			this.itemId = itemId;
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
			if (imageId == null) {
				if (other.imageId != null)
					return false;
			} else if (!imageId.equals(other.imageId))
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
	@JoinColumn(name = "ITEM_ID",
			insertable = false,
			updatable = false)
	private Item item;
	@ManyToOne
	@JoinColumn(name = "IMAGE_ID",
			insertable = false,
			updatable = false)
	private ItemImage image;
	private Long priority;
	
	public ItemDetailImage() {}
	public ItemDetailImage(Item item, ItemImage image, Long priority) {
		this.item = item;
		this.image = image;
		this.priority = priority;
		
		this.id.imageId = image.getId();
		this.id.itemId = item.getId();
	}
	
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public ItemImage getImage() {
		return image;
	}
	public void setImage(ItemImage image) {
		this.image = image;
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
		if (!(obj instanceof ItemDetailImage))
			return false;
		ItemDetailImage other = (ItemDetailImage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
