package com.zsolis.campusshop.domain;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
public class OrderItem {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 6845745414708823459L;
		@Column(name = "ORDER_ID")
		private Long orderId;
		@Column(name = "ITEM_ID")
		private Long itemId;
		
		public Id() {}
		public Id(Long orderId, Long itemId) {
			this.orderId = orderId;
			this.itemId = itemId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((itemId == null) ? 0 : itemId.hashCode());
			result = prime * result
					+ ((orderId == null) ? 0 : orderId.hashCode());
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
			if (orderId == null) {
				if (other.orderId != null)
					return false;
			} else if (!orderId.equals(other.orderId))
				return false;
			return true;
		}
	}
	@EmbeddedId
	private Id id = new Id();
	@ManyToOne
	@JoinColumn(name = "ORDER_ID",
			insertable = false,
			updatable = false)
	private Order order;
	@ManyToOne
	@JoinColumn(name = "ITEM_ID",
			insertable = false,
			updatable = false)
	private Item item;
	private Long quantity;
	/**
	 * 保存购买时商品的价格，防止改价后计算出错
	 */
	private Float itemPrice;
	/**
	 * 保存购买时商品的修改日期，以便判断下次来是否修改
	 */
	private Date itemDate;
	
	public OrderItem() {}
	public OrderItem(Order order, Item item, Long quantity) {
		this.order = order;
		this.item = item;
		this.quantity = quantity;
		
		this.itemPrice = item.getPresentPrice();
		this.itemDate = item.getDate();
		
		this.id.itemId = item.getId();
		this.id.orderId = order.getId();
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
		this.itemPrice = item.getPresentPrice();
		this.itemDate = item.getDate();
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Date getItemDate() {
		return itemDate;
	}
	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
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
		if (!(obj instanceof OrderItem))
			return false;
		OrderItem other = (OrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
