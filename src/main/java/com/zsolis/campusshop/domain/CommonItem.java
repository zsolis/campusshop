package com.zsolis.campusshop.domain;

import javax.persistence.*;

@Entity
public class CommonItem extends Item{
	
	public CommonItem() {}
	public CommonItem(String name, String barcode, String brief, String detail,
			Long stock, Float presentPrice, Category category, Store store) {
		super(name, barcode, brief, detail, stock,
				presentPrice, category, store);
	}
}
