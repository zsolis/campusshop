package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class PromotionItem extends Item{
	private Float originalPrice;
	private Long limitQuantity;
	
	public PromotionItem() {}
	public PromotionItem(String name, String barcode, String brief, String detail,
			Long stock, Float presentPrice, Category category, Store store, Long limit, Float originalPrice) {
		super(name, barcode, brief, detail, stock,
				presentPrice, category, store);
		this.limitQuantity = limit;
		this.originalPrice = originalPrice;
	}
	
	public Float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Float originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Long getLimit() {
		return limitQuantity;
	}
	public void setLimit(Long limit) {
		this.limitQuantity = limit;
	}
}
