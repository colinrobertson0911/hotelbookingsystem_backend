package com.fdmgroup.hotelbookingsystem.model;

public enum UserType {

	HOTELOWNER("hotelOwner"), ADMIN("admin"), CUSTOMER("customer");

	private String name;

	private UserType(String type) {
		name = type;
	}

	public String getName() {
		return name;
	}

	public static String getUserType(int index) {
		return UserType.values()[index].toString();
	}
}
