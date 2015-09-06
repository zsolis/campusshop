package com.zsolis.campusshop.domain;

import java.util.*;

import javax.persistence.*;

@Entity
public class Comment {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "ITEM_ID", nullable = false)
	private Item item;
	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;
	private String content;
	private CommentStatus status = CommentStatus.normal;
	private Date date = new Date();
	
	public Comment() {}
	public Comment(User user, Item item, Order order, String content) {
		this.user = user;
		this.item = item;
		this.order = order;
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus status) {
		this.status = status;
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
		if (!(obj instanceof Comment))
			return false;
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
