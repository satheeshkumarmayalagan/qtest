package com.mycorp.interfaces.crm;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CustomerWebServiceConfiguration {

	@Value("${endpoints.crm-webservice-uri}")
	private String crmCustomerUri;

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.mycorp.crm.schema.types");
		return marshaller;
	}

	@Bean
	public CustomerWebServiceClient customerWebServiceClient(Jaxb2Marshaller marshaller) {
		CustomerWebServiceClient client = new CustomerWebServiceClient();
		client.setDefaultUri(crmCustomerUri);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
