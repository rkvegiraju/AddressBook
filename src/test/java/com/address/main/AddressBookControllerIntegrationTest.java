package com.address.main;

import org.json.JSONException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import com.address.model.Address;
import com.address.model.Contact;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddressBookControllerIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private static int contactId = Math.round(10);
	private static Address address = new Address("280 N Mathilda Ave", "Sunnyvale", "CA", "94086");
	private static Contact contact = new Contact(contactId, "David", "Thomas", "408-444-5566", "david@email.com", address);

    @Before
    public void setup() {
    	contact.setLastContacedTime(null);
    }

	@Test
	public void testA_AddContact() throws JSONException {
		HttpEntity<Contact> entity = new HttpEntity<Contact>(contact, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/contacts"),
				HttpMethod.POST, entity, String.class);

		JSONAssert.assertEquals(asJsonString(contact), response.getBody(), false);
	}

	@Test
	public void testB_GetContacts() throws JSONException {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/contacts"),
				HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testC_UpdateContact() throws JSONException {
		contact.getAddress().setStreet("280 N Mathilda Ave, D7");
		
		HttpEntity<Contact> entity = new HttpEntity<Contact>(contact, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/contacts"),
				HttpMethod.PUT, entity, String.class);

		JSONAssert.assertEquals(asJsonString(contact), response.getBody(), false);
	}

	@Test
	public void testD_GetContact() throws JSONException {
		HttpEntity<Contact> entity = new HttpEntity<Contact>(contact, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/contacts/" + contactId),
				HttpMethod.GET, entity, String.class);

		JSONAssert.assertEquals(asJsonString(contact), response.getBody(), false);
	}

	@Test
	public void testE_DeleteContact() throws JSONException {
		restTemplate.delete(createURLWithPort("/contacts/" + contactId));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@SuppressWarnings("deprecation")
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);

	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  

}
