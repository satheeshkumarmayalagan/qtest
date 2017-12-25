package com.mycorp.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycorp.crm.dao.CustomerRepository;
import com.mycorp.crm.schema.types.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

	public Customer findCustomer(Integer customerId) {
		log.info("GetCustomer - {}", customerId);
		return customerRepository.findCustomer(customerId);
	}

	public Integer createCustomer(Customer customer) {
		log.info("CreateCustomer - {}", customer);
		return customerRepository.createCustomer(customer);
	}

	public boolean updateCustomer(Customer customer) {
		log.info("UpdateCustomer - {}", customer);
		return customerRepository.updateCustomer(customer);
	}

	public boolean deleteCustomer(Integer customerId) {
		log.info("DeleteCustomer - {}", customerId);
		return customerRepository.deleteCustomer(customerId);
	}
}
