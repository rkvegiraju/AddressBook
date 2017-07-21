package com.address.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.address.dao.ContactDao;
import com.address.exception.ContactAlreayExistsException;
import com.address.exception.ContactNotFoundException;
import com.address.helper.DateUtils;
import com.address.model.Contact;


@Service
public class ContactService {

	@Autowired
	ContactDao csDao;

	public Contact getContact(int id) {
		return csDao.getContact(id);
	}

	public boolean deleteContact(int id) throws ContactNotFoundException {
		if (csDao.deleteContact(id)) {
			return true;
		}
			
		throw new ContactNotFoundException(String.format("Contact %d does not exists", id));
	}

	public Contact addContact(Contact contact) throws ContactAlreayExistsException {
		if (csDao.addContact(contact) == null) {
			throw new ContactAlreayExistsException(String.format("Contact %d already exists", contact.getId()));
		}
		
		return contact;
	}

	public Contact updateContact(Contact contact) throws ContactNotFoundException {
		if (csDao.updateContact(contact) == null) {
			throw new ContactNotFoundException(String.format("Contact %d does not exists", contact.getId()));
		}
			
		return contact;
	}

	public Collection<Contact> getAllContacts() {
		return csDao.getAllContacts();
	}
	
	public List<Contact> findAllByDate(Date startDate, Date endDate) {
		final Date startOfDate = DateUtils.startOfDay(startDate);
		final Date endOfDate = DateUtils.endOfDay(endDate);

		return csDao.findAllByDate(startOfDate, endOfDate);
	}
	
	public List<Contact> findAllByState(final List<String> search) {
		return csDao.findAllByState(search);
	}

	public List<Contact> findAllByAreaCode(final String areaCode) {
		return csDao.findAllByAreaCode(areaCode);
	}

	public List<Contact> filter(final List<String> search, Date startDate, Date endDate, final String areaCode) {
		final Date startOfDate = DateUtils.startOfDay(startDate);
		final Date endOfDate = DateUtils.endOfDay(endDate);
		
		return csDao.filter(search, startOfDate, endOfDate, areaCode);
	}
}
