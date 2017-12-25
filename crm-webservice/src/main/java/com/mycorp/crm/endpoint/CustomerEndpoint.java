package com.mycorp.crm.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mycorp.crm.schema.types.CreateCustomerRequest;
import com.mycorp.crm.schema.types.CreateCustomerResponse;
import com.mycorp.crm.schema.types.Customer;
import com.mycorp.crm.schema.types.DeleteCustomerRequest;
import com.mycorp.crm.schema.types.DeleteCustomerResponse;
import com.mycorp.crm.schema.types.GetCustomerRequest;
import com.mycorp.crm.schema.types.GetCustomerResponse;
import com.mycorp.crm.schema.types.UpdateCustomerRequest;
import com.mycorp.crm.schema.types.UpdateCustomerResponse;
import com.mycorp.crm.service.CustomerService;

/**
 * CustomerEndpoint publishes the WS Service for customer.
 */
@Endpoint
public class CustomerEndpoint {

	/** The Constant NAMESPACE_URI. */
	private static final String NAMESPACE_URI = "http://mycorp.com/crm/schema/types";

	/** The customer service. */
	@Autowired
	private CustomerService customerService;

	private static final Logger log = LoggerFactory.getLogger(CustomerEndpoint.class);

	/**
	 * Gets the customer.
	 *
	 * @param request
	 *            the request
	 * @return the customer
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
	@ResponsePayload
	public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
		log.info("GetCustomer");
		GetCustomerResponse response = new GetCustomerResponse();
		response.setCustomer(customerService.findCustomer(request.getCustomerId()));
		return response;
	}

	/**
	 * Creates the customer.
	 *
	 * @param request
	 *            the request
	 * @return the creates the customer response
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCustomerRequest")
	@ResponsePayload
	public CreateCustomerResponse createCustomer(@RequestPayload CreateCustomerRequest request) {
		log.info("CreateCustomer");
		CreateCustomerResponse response = new CreateCustomerResponse();
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmailAddress(request.getEmailAddress());
		customer.setDateOfBirth(request.getDateOfBirth());
		if (request.getAddress() != null && !request.getAddress().isEmpty())
			customer.getAddress().addAll(request.getAddress());
		response.setCustomerId(customerService.createCustomer(customer));
		return response;
	}

	/**
	 * Update customer.
	 *
	 * @param request
	 *            the request
	 * @return the update customer response
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCustomerRequest")
	@ResponsePayload
	public UpdateCustomerResponse updateCustomer(@RequestPayload UpdateCustomerRequest request) {
		log.info("UpdateCustomer");
		UpdateCustomerResponse response = new UpdateCustomerResponse();
		response.setUpdated(customerService.updateCustomer(request.getCustomer()));
		return response;
	}

	/**
	 * Gets the customer.
	 *
	 * @param request
	 *            the request
	 * @return the customer
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse getCustomer(@RequestPayload DeleteCustomerRequest request) {
		log.info("DeleteCustomer");
		DeleteCustomerResponse response = new DeleteCustomerResponse();
		response.setDeleted(customerService.deleteCustomer(request.getCustomerId()));
		return response;
	}

}
