package com.mycorp.interfaces.crm;

import java.util.ArrayList;

import com.mycorp.model.customer.Address;
import com.mycorp.model.customer.AddressType;
import com.mycorp.model.customer.Customer;
import com.mycorp.utils.LocalDateConverter;

public class CustomerMapper {

	public static Customer from(com.mycorp.crm.schema.types.Customer crmCustomer) {
		Customer customer = null;
		if (crmCustomer != null) {
			customer = new Customer();
			customer.setFirstName(crmCustomer.getFirstName());
			customer.setLastName(crmCustomer.getLastName());
			customer.setDateOfBirth(LocalDateConverter.toLocalDate(crmCustomer.getDateOfBirth()));
			customer.setEmailAddress(crmCustomer.getEmailAddress());
			if (crmCustomer.getAddress() != null && !crmCustomer.getAddress().isEmpty()) {
				customer.setAddress(new ArrayList<Address>());
				for (com.mycorp.crm.schema.types.Address crmAddress : crmCustomer.getAddress()) {
					customer.getAddress().add(from(crmAddress));
				}
			}
		}
		return customer;
	}

	public static com.mycorp.crm.schema.types.Customer to(Customer customer) {
		com.mycorp.crm.schema.types.Customer crmCustomer = null;
		if (customer != null) {
			crmCustomer = new com.mycorp.crm.schema.types.Customer();
			crmCustomer.setFirstName(customer.getFirstName());
			crmCustomer.setLastName(customer.getLastName());
			crmCustomer.setDateOfBirth(LocalDateConverter.toXmlDate(customer.getDateOfBirth()));
			crmCustomer.setEmailAddress(customer.getEmailAddress());
			if (crmCustomer.getAddress() != null && !crmCustomer.getAddress().isEmpty()) {
				for (Address address : customer.getAddress()) {
					crmCustomer.getAddress().add(to(address));
				}
			}
		}
		return crmCustomer;
	}
	
	private static Address from(com.mycorp.crm.schema.types.Address crmAddress) {
		Address address = null;
		if (crmAddress != null) {
			address = new Address();
			address.setAddressType(AddressType.fromType(crmAddress.getAddressType().value()));
			address.setPostCode(crmAddress.getPostCode());
			address.setState(crmAddress.getState());
			address.setStreetName(crmAddress.getStreetName());
			address.setStreetNumber(crmAddress.getStreetNumber());
			address.setUnitNumber(crmAddress.getUnitNumber());
			address.setSuburb(crmAddress.getSuburb());
		}
		return address;
	}

	private static com.mycorp.crm.schema.types.Address to(Address address) {
		com.mycorp.crm.schema.types.Address crmAddress = null;
		if (address != null) {
			crmAddress = new com.mycorp.crm.schema.types.Address();
			crmAddress.setAddressType(com.mycorp.crm.schema.types.AddressType.fromValue(crmAddress.getAddressType().value()));
			crmAddress.setPostCode(crmAddress.getPostCode());
			crmAddress.setState(crmAddress.getState());
			crmAddress.setStreetName(crmAddress.getStreetName());
			crmAddress.setStreetNumber(crmAddress.getStreetNumber());
			crmAddress.setUnitNumber(crmAddress.getUnitNumber());
			crmAddress.setSuburb(crmAddress.getSuburb());
		}
		return crmAddress;
	}
}
