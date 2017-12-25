package com.mycorp.model.customer;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AddressType {
	RESIDENTIAL("Residential"),
	POSTAL("Postal"),
	WORK("Work");
	
	private String type;
	
	private AddressType(String type){
		this.type = type;
	}

	@JsonValue
	public String getType() {
		return type;
	}
	
	public static AddressType fromType(String type) {
		for(AddressType addressType: AddressType.values()) {
			if(addressType.type.equalsIgnoreCase(type)) {
				return addressType;
			}
		}
		return null;
	}
}
