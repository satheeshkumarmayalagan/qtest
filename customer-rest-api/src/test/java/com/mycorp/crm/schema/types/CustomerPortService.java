package com.mycorp.crm.schema.types;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.mycorp.utils.LocalDateConverter;

@WebService(endpointInterface="com.mycorp.crm.schema.types.CustomerPort",
targetNamespace="http://mycorp.com/crm/schema/types",
portName="CustomerPortSoap11",
serviceName="CustomerPortService")
public class CustomerPortService implements CustomerPort {

	@Override
	public GetCustomerResponse getCustomer(GetCustomerRequest getCustomerRequest) {
		GetCustomerResponse okResponse = new GetCustomerResponse();
		com.mycorp.crm.schema.types.Customer customer = new com.mycorp.crm.schema.types.Customer();
		customer.setFirstName("Elon");
		customer.setLastName("Musk");
		customer.setEmailAddress("HelloWorld@testing.com");
		customer.setDateOfBirth(LocalDateConverter.toXmlDate(LocalDate.now()));
		List<com.mycorp.crm.schema.types.Address> addresses = new ArrayList<com.mycorp.crm.schema.types.Address>();

		com.mycorp.crm.schema.types.Address residence = new com.mycorp.crm.schema.types.Address();
		residence.setAddressType(com.mycorp.crm.schema.types.AddressType.WORK);
		residence.setPostCode(2145);
		residence.setSuburb("Strathfield");
		residence.setStreetName("Station Street");
		residence.setStreetNumber("200");
		residence.setUnitNumber("20");
		residence.setState("NSW");
		addresses.add(residence);
		customer.getAddress().addAll(addresses);
		okResponse.setCustomer(customer);
		return okResponse;
	}

	@Override
	public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
		CreateCustomerResponse createResponse = new CreateCustomerResponse();
		createResponse.setCustomerId(100);
		return createResponse;
	}

	@Override
	public UpdateCustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
		UpdateCustomerResponse updateResponse = new UpdateCustomerResponse();
		updateResponse.setUpdated(true);
		return updateResponse;
	}

	@Override
	public DeleteCustomerResponse deleteCustomer(DeleteCustomerRequest deleteCustomerRequest) {
		DeleteCustomerResponse deleteResponse = new DeleteCustomerResponse();
		deleteResponse.setDeleted(true);
		return deleteResponse;
	}

}