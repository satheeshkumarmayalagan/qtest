package com.mycorp.interfaces.crm;

import static com.mycorp.interfaces.crm.CustomerMapper.from;
import static com.mycorp.interfaces.crm.CustomerMapper.to;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycorp.api.BaseController;
import com.mycorp.crm.schema.types.GetCustomerResponse;
import com.mycorp.model.customer.Customer;

@Service
public class CustomerWebService {

	@Autowired
	CustomerWebServiceClient client;

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);

	public Customer getCustomer(Integer customerId) {
		log.info("GetCustomer - {}", customerId);
		Customer customer = null;
		GetCustomerResponse crmCustomerResponse = client.getCustomer(customerId);
		if (crmCustomerResponse != null) {
			customer = from(crmCustomerResponse.getCustomer());
		}
		return customer;
	}

	public Integer createCustomer(Customer customer) {
		log.info("CreateCustomer - {}", customer);
		return client.createCustomer(to(customer)).getCustomerId();
	}

	public boolean updateCustomer(Customer customer) {
		log.info("UpdateCustomer - {}", customer);
		return client.updateCustomer(to(customer)).isUpdated();
	}

	public boolean deleteCustomer(Integer customerId) {
		log.info("DeleteCustomer - {}", customerId);
		return client.deleteCustomer(customerId).isDeleted();
	}
}
