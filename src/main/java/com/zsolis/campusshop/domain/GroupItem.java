package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class GroupItem extends Item{
	private Float originalPrice;
	
	public GroupItem() {}
	public GroupItem(String name, String barcode, String brief, String detail,
			Long stock, Float presentPrice, Category category, Store store, Float originalPrice) {
		super(name, barcode, brief, detail, stock,
				presentPrice, category, store);
		this.originalPrice = originalPrice;
	}
	
	public Float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Float originalPrice) {
		this.originalPrice = originalPrice;
	}
}
