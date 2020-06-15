package com.fdmgroup.hotelbookingsystem.model;

import java.math.BigDecimal;

public enum Extras {
	
	AIRPORTTRANSFER("Airport Transfer", new BigDecimal ("20.00")),
	NO_EXTRAS("No Extras", new BigDecimal ("0.00"));
	
	private final String service;
	private final BigDecimal price;
	
	private Extras(String service, BigDecimal price) {
		this.service = service;
		this.price = price;
	}
	
	public String getService() {
		return service +" £" + price;
	}	

	public BigDecimal getPrice() {
		return price;
	}

	public static String getExtra(int index) {
		return Extras.values()[index].toString();
	}
}
