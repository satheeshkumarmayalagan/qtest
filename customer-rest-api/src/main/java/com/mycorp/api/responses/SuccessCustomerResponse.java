package com.mycorp.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessCustomerResponse {
	
	public SuccessCustomerResponse(Boolean success) {
		this.success = success;
	}

	private Boolean success;

	@JsonProperty(value="success")
	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	
}
