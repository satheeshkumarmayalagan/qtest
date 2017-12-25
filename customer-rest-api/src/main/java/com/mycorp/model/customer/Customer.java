package com.mycorp.model.customer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mycorp.api.serializers.CustomLocalDateDeSerializer;
import com.mycorp.api.serializers.CustomLocalDateSerializer;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	private int customerId;

	@NotNull(message = "First Name must not be empty!")
	private String firstName;

	@NotNull(message = "Last Name must not be empty!")
	private String lastName;

	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeSerializer.class)
	@NotNull(message = "Date of Birth must not be empty!")
	private LocalDate dateOfBirth;

	@NotNull(message = "Email Address must not be empty!")
	private String emailAddress;

	@NotNull(message = "Email Address must not be empty!")
	private List<Address> address;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format("Customer [customerId=%s, firstName=%s, lastName=%s, dateOfBirth=%s]", customerId,
				firstName, lastName, dateOfBirth);
	}

}
