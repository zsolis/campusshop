package com.zsolis.campusshop.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {
	@Id
	@GeneratedValue(generator = "_hilo") @org.hibernate.annotations.GenericGenerator(name = "_hilo", strategy = "hilo")
	private Long id = null;
	private String name;
	private String barcode;
	private String brief;
	private String detail;
	private Long stock;
	private Long sales = 0L;
	private Float presentPrice;
	private Date date = new Date();
	private ItemStatus status = ItemStatus.normal;
	@ManyToOne
	@JoinColumn(name = "IMAGE_ID")
	private ItemImage mainImage;
	@ManyToOne
	@JoinColumn(name ="STORE_ID", nullable = false)
	private Store store;
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private Category category;
	
	public Item() {}
	public Item(String name, String barcode, String brief, String detail,
			Long stock, Float presentPrice, Category category, Store store) {
		this.name = name;
		this.barcode = barcode;
		this.brief = brief;
		this.detail = detail;
		this.stock = stock;
		this.presentPrice = presentPrice;
		this.category = category;
		this.store = store;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public Long getSales() {
		return sales;
	}
	public void setSales(Long sales) {
		this.sales = sales;
	}
	public Float getPresentPrice() {
		return presentPrice;
	}
	public void setPresentPrice(Float presentPrice) {
		this.presentPrice = presentPrice;
	}
	public ItemStatus getStatus() {
		return status;
	}
	public void setStatus(ItemStatus status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ItemImage getMainImage() {
		return mainImage;
	}
	public void setMainImage(ItemImage mainImage) {
		this.mainImage = mainImage;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
		if (!(obj instanceof Item))
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
