package com.mycorp.model.customer;

import javax.validation.constraints.NotNull;

public class Address {

	@NotNull(message = "AddressType must not be empty!")
	private AddressType addressType;

	private String unitNumber;

	@NotNull(message = "Street name must not be empty!")
	private String streetName;
	
	private String streetNumber;

	@NotNull(message = "Suburb must not be empty!")
	private String suburb;

	@NotNull(message = "State must not be empty!")
	private String state;

	@NotNull(message = "Postcode must not be empty!")
	private Integer postCode;

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPostCode() {
		return postCode;
	}

	public void setPostCode(Integer postCode) {
		this.postCode = postCode;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	@Override
	public String toString() {
		return String.format("Address [suburb=%s, state=%s, postCode=%s, streetName=%s, streetNumber=%s]",
				suburb, state, postCode, streetName, streetNumber);
	}

}
