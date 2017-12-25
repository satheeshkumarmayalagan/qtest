package com.mycorp.interfaces.crm.mock;

import java.net.MalformedURLException;

import javax.xml.ws.Endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mycorp.crm.schema.types.CustomerPortService;

@Component
public class MockCustomerWebService {
	Endpoint endpoint = null;

	@Value("${endpoints.crm-webservice-uri}")
	private String crmCustomerUri;
	
	public void start() throws MalformedURLException {
		endpoint = Endpoint.publish(crmCustomerUri, new CustomerPortService());
	}

	public void stop() {
		endpoint.stop();
	}

}