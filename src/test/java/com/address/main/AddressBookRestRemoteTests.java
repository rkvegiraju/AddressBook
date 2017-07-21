package com.address.main;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.address.exception.ContactNotFoundException;
import com.address.model.Address;
import com.address.model.Contact;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource("classpath:application-test.properties")
public class AddressBookRestRemoteTests {
	
	@Autowired
	public Environment env;

	private String contactsUrl = null;
	private String searchUrl = null;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private RestTemplate restTemplate = new RestTemplate();
	private int contactId = 5;

    @Before
    public void setup() {
    	contactsUrl = env.getProperty("rest.server.url") + "/contacts/";
    	searchUrl = contactsUrl + "search";
    }

	public ResponseEntity<Contact> getContact(int id) throws Exception {
		String url = contactsUrl + id;
		return restTemplate.getForEntity(url, Contact.class);
	}

	public ResponseEntity<Contact> addContact(Contact contact) throws Exception {
		return restTemplate.postForEntity(contactsUrl, contact, Contact.class);
	}

	public void deleteContact(int id) throws Exception {
		String url = contactsUrl + id;
		restTemplate.delete(url);
	}

	@SuppressWarnings({ "rawtypes" })
	@Test
	public void testA_GetContacts() throws Exception {
		ResponseEntity<Collection> responseEntity = restTemplate.getForEntity(contactsUrl, Collection.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testB_AddContact() throws Exception {
		Address address1 = new Address("260 N Mathilda Ave", "Sunnyvale", "CA", "94086");
		Contact contact1 = new Contact(contactId, "Rama123", "Vegi", "408-444-5566", "username1@email.com", address1);

		try {
			ResponseEntity<Contact> responseEntity = addContact(contact1);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        
	        Contact contact = responseEntity.getBody();
	        if (contact != null) {
	        	assertEquals(contact.getId(), contact1.getId());
	        	assertEquals(contact.getFirstName(), contact1.getFirstName());
	        }
		} catch (ContactNotFoundException e) {
            assertThat(e.getMessage(), is("does not exist"));
        }
	}

	@Test
	public void testC_GetContact() throws Exception {
		ResponseEntity<Contact> responseEntity = getContact(contactId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
        Contact contact = responseEntity.getBody();
        if (contact != null) {
        	assertEquals(contact.getId(), contactId);
        }
	}

	@Test
	public void testD_UpdateContact() throws Exception {
		Address address1 = new Address("260 N Mathilda Ave", "Sunnyvale", "CA", "94086");
		Contact contact1 = new Contact(contactId, "Rama123", "Vegi", "408-444-5566", "username1@email.com", address1);

		try {
			restTemplate.put(contactsUrl, contact1);
			
			ResponseEntity<Contact> responseEntity = getContact(contact1.getId());
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        
	        Contact contact = responseEntity.getBody();
	        if (contact != null) {
	        	assertEquals(contact.getId(), contact1.getId());
	        	assertEquals(contact.getFirstName(), contact1.getFirstName());
	        }
		} catch (ContactNotFoundException e) {
            assertThat(e.getMessage(), is("does not exist"));
        }
	}

	@Test
	public void testE_DeleteContact() throws Exception {
		try {
			deleteContact(contactId);
		
			ResponseEntity<Contact> responseEntity = getContact(contactId);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		} catch (ContactNotFoundException e) {
            assertThat(e.getMessage(), is("does not exist"));
        }
	}

	@Test
	public void testF_Filter() throws Exception {
		   Map<String, String> map = new HashMap<>();
		   map.put("states", "GA, VA, CA");
		   map.put("areacode", "408");
		   map.put("startdate", sdf.format(new Date()));
		   map.put("enddate", sdf.format(new Date()));

		   ResponseEntity<String> responseEntity = restTemplate.getForEntity(searchUrl, String.class, map);
		   assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
