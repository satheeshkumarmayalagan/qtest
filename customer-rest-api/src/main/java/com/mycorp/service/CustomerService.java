package com.mycorp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycorp.interfaces.crm.CustomerWebService;
import com.mycorp.model.customer.Customer;

@Service
public class CustomerService {
	@Autowired
	CustomerWebService crmService;

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

	public Customer getCustomer(Integer customerId) {
		log.info("GetCustomer - {}", customerId);
		return crmService.getCustomer(customerId);
	}

	public Integer createCustomer(Customer customer) {
		log.info("CreateCustomer - {}", customer);
		return crmService.createCustomer(customer);
	}
	
	public boolean updateCustomer(Customer customer) {
		log.info("UpdateCustomer - {}", customer);
		return crmService.updateCustomer(customer);
	}
	
	public boolean deleteCustomer(Integer customerId) {
		log.info("DeleteCustomer - {}", customerId);
		return crmService.deleteCustomer(customerId);
	}
}
