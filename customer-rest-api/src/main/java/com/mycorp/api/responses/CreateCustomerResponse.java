package com.mycorp.api.responses;

public class CreateCustomerResponse {
	
	private Integer customerId;

	public CreateCustomerResponse(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
}
