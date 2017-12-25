package com.mycorp.api.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycorp.interfaces.crm.mock.MockCustomerWebService;
import com.mycorp.model.customer.Address;
import com.mycorp.model.customer.AddressType;
import com.mycorp.model.customer.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	MockCustomerWebService mockCustomerWebService;

	@LocalServerPort
	private int port = 0;

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	String accessToken = null;

	@Before
	public void setUp() throws Exception {
		mockCustomerWebService.start();
		accessToken = getJwtTokenByClientCredentialForUser();
	}

	@Test
	public void shouldGetCustomerOk() throws Exception {
		this.mockMvc.perform(get("/customer/1").header("Authorization", "Bearer " + accessToken)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Elon"))
				.andExpect(jsonPath("$.lastName").value("Musk"));
	}

	@Test
	public void shouldCreateCustomerOk() throws Exception {
		Customer customer = new Customer();
		customer.setFirstName("Hello");
		customer.setLastName("World");
		customer.setEmailAddress("HelloWorld@testing.com");
		customer.setDateOfBirth(LocalDate.now());
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
		customer.setAddress(addresses);

		this.mockMvc.perform(
				post("/customer").header("Authorization", "Bearer " + accessToken).content(asJsonString(customer))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void shouldUpdateCustomerOk() throws Exception {
		Customer customer = new Customer();
		customer.setFirstName("Hello");
		customer.setLastName("World");
		customer.setEmailAddress("HelloWorld@testing.com");
		customer.setDateOfBirth(LocalDate.now());
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
		customer.setAddress(addresses);

		this.mockMvc.perform(
				put("/customer/1").header("Authorization", "Bearer " + accessToken).content(asJsonString(customer))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void shouldDeleteCustomerOk() throws Exception {
		this.mockMvc.perform(delete("/customer/3").header("Authorization", "Bearer " + accessToken)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturnBadRequest() throws Exception {
		Customer customer = new Customer();
		customer.setLastName("World");
		customer.setEmailAddress("HelloWorld@testing.com");
		customer.setDateOfBirth(LocalDate.now());
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
		customer.setAddress(addresses);

		this.mockMvc.perform(
				post("/customer").header("Authorization", "Bearer " + accessToken).content(asJsonString(customer))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public String getJwtTokenByClientCredentialForUser() throws Exception {
		ResponseEntity<String> response = new TestRestTemplate(clientId, clientSecret).postForEntity(
				"http://localhost:" + port + "/oauth/token?grant_type=password&username=admin.admin&password=jwtpass",
				null, String.class);
		String responseText = response.getBody();
		HashMap jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);
		return (String) jwtMap.get("access_token");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldAuthorizeOk() throws Exception {
		ResponseEntity<String> response = new TestRestTemplate(clientId, clientSecret).postForEntity(
				"http://localhost:" + port + "/oauth/token?grant_type=password&username=admin.admin&password=jwtpass",
				null, String.class);
		String responseText = response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());

		HashMap jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);
		assertEquals("bearer", jwtMap.get("token_type"));
		assertEquals("read write", jwtMap.get("scope"));
		assertTrue(jwtMap.containsKey("access_token"));
		assertTrue(jwtMap.containsKey("expires_in"));
		assertTrue(jwtMap.containsKey("jti"));
		String accessToken = (String) jwtMap.get("access_token");

		Jwt jwtToken = JwtHelper.decode(accessToken);
		String claims = jwtToken.getClaims();
		HashMap claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
		assertEquals(resourceIds, ((List<String>) claimsMap.get("aud")).get(0));
		assertEquals(clientId, claimsMap.get("client_id"));
		assertEquals("admin.admin", claimsMap.get("user_name"));
	}

	@After
	public void destroy() throws Exception {
		mockCustomerWebService.stop();
	}
}
