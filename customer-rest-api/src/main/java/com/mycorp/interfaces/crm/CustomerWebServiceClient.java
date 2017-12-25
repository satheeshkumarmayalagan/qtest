package com.mycorp.interfaces.crm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.mycorp.crm.schema.types.CreateCustomerRequest;
import com.mycorp.crm.schema.types.CreateCustomerResponse;
import com.mycorp.crm.schema.types.Customer;
import com.mycorp.crm.schema.types.DeleteCustomerRequest;
import com.mycorp.crm.schema.types.DeleteCustomerResponse;
import com.mycorp.crm.schema.types.GetCustomerRequest;
import com.mycorp.crm.schema.types.GetCustomerResponse;
import com.mycorp.crm.schema.types.UpdateCustomerRequest;
import com.mycorp.crm.schema.types.UpdateCustomerResponse;

@Component
public class CustomerWebServiceClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(CustomerWebServiceClient.class);

	public GetCustomerResponse getCustomer(Integer customerId) {
		log.info("Get Customer " + customerId);
		GetCustomerRequest request = new GetCustomerRequest();
		request.setCustomerId(customerId);
		GetCustomerResponse response = (GetCustomerResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		return response;
	}

	public CreateCustomerResponse createCustomer(Customer customer) {
		log.info("Create Customer " + customer);
		CreateCustomerRequest request = new CreateCustomerRequest();
		request.setFirstName(customer.getFirstName());
		request.setLastName(customer.getLastName());
		request.setDateOfBirth(customer.getDateOfBirth());
		request.setEmailAddress(customer.getEmailAddress());
		request.getAddress().addAll(customer.getAddress());
		return (CreateCustomerResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}

	public UpdateCustomerResponse updateCustomer(Customer customer) {
		log.info("Update Customer " + customer);
		UpdateCustomerRequest request = new UpdateCustomerRequest();
		request.setCustomer(customer);
		return (UpdateCustomerResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}

	public DeleteCustomerResponse deleteCustomer(Integer customerId) {
		log.info("Delete Customer " + customerId);
		DeleteCustomerRequest request = new DeleteCustomerRequest();
		request.setCustomerId(customerId);
		return (DeleteCustomerResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}
}