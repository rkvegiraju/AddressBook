package com.address.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.address.exception.ContactAlreayExistsException;
import com.address.exception.ContactNotFoundException;
import com.address.model.Contact;
import com.address.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	ContactService cs;
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Contact> getAllContacts() {
		return cs.getAllContacts();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Contact getContact(@PathVariable("id") int id) {
		return cs.getContact(id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public boolean deleteContact(@PathVariable("id") int id) throws ContactNotFoundException {
		return cs.deleteContact(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Contact addContact(@RequestBody Contact contact) throws ContactAlreayExistsException {
	   return cs.addContact(contact);
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Contact updateContact(@RequestBody Contact contact) throws ContactNotFoundException {
	   return cs.updateContact(contact);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public List<Contact> filter(
			@RequestParam(value = "states", required = false) List<String> search,
			@RequestParam(value = "startdate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "enddate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
			@RequestParam(value = "areacode", required = false) String areaCode) {
		return cs.filter(search, startDate, endDate, areaCode);
	}

	// filter API will handle all cases (by states, dates and area code). 
	// We can use these APIa in case if we want separate API for each search
	
	@RequestMapping(method = RequestMethod.GET, value = "/search/states")
	public List<Contact> findAllByState(
			@RequestParam(value = "states", required = true) List<String> search) {
		return cs.findAllByState(search);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/dates")
	public List<Contact> findAllByDate(
			@RequestParam(value = "startdate", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "enddate", required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		return cs.findAllByDate(startDate, endDate);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/areacode")
	public List<Contact> findAllByAreaCode(
			@RequestParam(value = "areacode", required = true) String areaCode) {
		return cs.findAllByAreaCode(areaCode);
	}

}
