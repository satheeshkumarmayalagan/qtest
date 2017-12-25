package com.mycorp.api.customer;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycorp.api.BaseController;
import com.mycorp.api.exception.ResourceNotFoundException;
import com.mycorp.model.customer.Customer;
import com.mycorp.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {

	@Autowired
	CustomerService customerService;

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	@RequestMapping("/{customerId}")
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<?> getCustomer(@PathVariable("customerId") Integer customerId) {
		log.info("GetCustomer - {}", customerId);
		Customer customer = null;
		customer = customerService.getCustomer(customerId);
		if (customer != null) {
			return ResponseEntity.ok(customer);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer) {
		log.info("CreateCustomer - {}", customer);
		Integer customerId = customerService.createCustomer(customer);
		return ResponseEntity.ok(customerId);
	}

	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@RequestMapping(value = "/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@PathVariable(value = "customerId", required = true) Integer customerId,
			@Valid @RequestBody Customer customer) {
		log.info("UpdateCustomer - {}", customer);
		customer.setCustomerId(customerId);
		return ResponseEntity.ok(customerService.updateCustomer(customer));
	}

	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomer(@PathVariable(value = "customerId", required = true) Integer customerId) {
		log.info("deleteCustomer - {}", customerId);
		return ResponseEntity.ok(customerService.deleteCustomer(customerId));
	}

}
