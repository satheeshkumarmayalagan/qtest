/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mycorp.crm;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.mycorp.crm.schema.types.Address;
import com.mycorp.crm.schema.types.AddressType;
import com.mycorp.crm.schema.types.CreateCustomerRequest;
import com.mycorp.crm.schema.types.Customer;
import com.mycorp.crm.schema.types.DeleteCustomerRequest;
import com.mycorp.crm.schema.types.GetCustomerRequest;
import com.mycorp.crm.schema.types.UpdateCustomerRequest;
import com.mycorp.crm.utils.DateConverter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTests {

	private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	@LocalServerPort
	private int port = 0;
	WebServiceTemplate ws;
	
	@Before
	public void init() throws Exception {
		marshaller.setPackagesToScan(ClassUtils.getPackageName(Customer.class));
		marshaller.afterPropertiesSet();
		ws = new WebServiceTemplate(marshaller);
	}

	@Test
	public void testGetCustomer() {
		GetCustomerRequest request = new GetCustomerRequest();
		request.setCustomerId(1);
		assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/crm/ws", request)).isNotNull();
	}

	@Test
	public void testAddCustomer() {
		CreateCustomerRequest customer = new CreateCustomerRequest();
		customer.setFirstName("Hello");
		customer.setLastName("World");
		customer.setEmailAddress("HelloWorld@testing.com");
		customer.setDateOfBirth(DateConverter.toXmlDate(LocalDate.now()));
		
    	List<Address> addresses = new ArrayList<Address>();
    	
    	Address residence = new Address();
    	residence.setAddressType(AddressType.WORK);
    	residence.setPostCode(2145);
    	residence.setSuburb("Strathfield");
    	residence.setStreetName("Station Street");
    	residence.setStreetNumber("200");
    	residence.setUnitNumber("20");
    	residence.setState("NSW");
    	addresses.add(residence);
    	
    	Address work = new Address();
    	work.setAddressType(AddressType.WORK);
    	work.setPostCode(2145);
    	work.setSuburb("Sydney");
    	work.setStreetName("Miller Street");
    	work.setStreetNumber("100");
    	work.setUnitNumber("1A");
    	work.setState("NSW");
		addresses.add(work );
    	customer.getAddress().addAll(addresses);

		assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/crm/ws", customer)).isNotNull();
	}
	
	@Test
	public void testUpdateCustomer() {
		UpdateCustomerRequest request = new UpdateCustomerRequest();
		Customer customer = new Customer();
		customer.setFirstName("Hello");
		customer.setLastName("World");
		customer.setEmailAddress("HelloWorldUpdate@testing.com");
		customer.setDateOfBirth(DateConverter.toXmlDate(LocalDate.now()));
		request.setCustomer(customer);
		assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/crm/ws", request)).isNotNull();
	}
	
	@Test
	public void testDeleteCustomer() {
		DeleteCustomerRequest request = new DeleteCustomerRequest();
		request.setCustomerId(1);
		assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/crm/ws", request)).isNotNull();
	}
}