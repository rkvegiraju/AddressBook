package com.address.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.address.exception.ContactAlreayExistsException;
import com.address.exception.ContactNotFoundException;
import com.address.helper.ContactHelper;
import com.address.helper.ListUtils;
import com.address.model.Address;
import com.address.model.Contact;


@Repository
public class ContactDao {

	private Hashtable<Integer, Contact> contacts = new Hashtable<>();
	public ContactDao() {
		Address address1 = new Address("280 N Mathilda Ave", "Sunnyvale", "CA", "94086");
		Contact contact1 = new Contact(1, "David", "Thomas", "408-444-5566", "david@email.com", address1);

		Address address2 = new Address("440 N Wolf Rd", "Sunnyvale", "CA", "94087");
		Contact contact2 = new Contact(2, "Jon", "David", "408-444-7788", "jon@email.com", address2);
		
		this.contacts.put(contact1.getId(), contact1);
		this.contacts.put(contact2.getId(), contact2);
	}
	
	public Contact getContact(int id) {
		return this.contacts.get(id);
	}

	public boolean deleteContact(int id) throws ContactNotFoundException {
		if (this.contacts.containsKey(id)) {
			this.contacts.remove(id);
			return true;
		}
			
		return false;
	}

	public Contact addContact(Contact contact) throws ContactAlreayExistsException {
		if (!this.contacts.containsKey(contact.getId())) {
			contact.setLastContacedTime(new Date());
			this.contacts.put(contact.getId(), contact);
			return contact;
		}
		
		return null;
	}

	public Contact updateContact(Contact contact) throws ContactNotFoundException {
		if (this.contacts.containsKey(contact.getId())) {
			Contact contactDB = this.contacts.get(contact.getId());
			
			contactDB.setFirstName(contact.getFirstName());
			contactDB.setLastName(contact.getLastName());
			contactDB.setPhoneNumber(contact.getPhoneNumber());
			contactDB.setEmail(contact.getEmail());
			
			if (contact.getAddress() != null) {
				Address addressDB = contactDB.getAddress();
				addressDB.setStreet(contact.getAddress().getStreet());
				addressDB.setCity(contact.getAddress().getCity());
				addressDB.setState(contact.getAddress().getState());
				addressDB.setZip(contact.getAddress().getZip());
			}
			
			contactDB.setLastContacedTime(new Date());
			
			this.contacts.put(contact.getId(), contactDB);
			return contact;
		}
			
		return null;
	}

	public Collection<Contact> getAllContacts() {
		return this.contacts.values();
	}
	
	public List<Contact> findAllByDate(final Date startDate, final Date endDate) {
		List<Contact> filteredList = new ArrayList<>();
		
//		final Date startOfDate = DateUtils.startOfDay(startDate);
//		final Date endOfDate = DateUtils.endOfDay(endDate);

		// If we use DB, this filter will apply on DB. So, I put this logic here in DAO.
		if (this.contacts != null && this.contacts.size() > 0) {
		    ListUtils.filter(this.contacts.values(), filteredList, new ListUtils.Filter<Contact>() {
				@Override
				public boolean apply(Contact contact) {
					return ( (startDate == null || endDate == null) || 
							(contact.getLastContactedTime() != null && 
							ContactHelper.between(contact.getLastContactedTime(), startDate, endDate)) ); 
				}
		    });
		}
		
		return filteredList;
	}
	
	public List<Contact> findAllByState(final List<String> search) {
		List<Contact> filteredList = new ArrayList<>();
		
		// If we use DB, this filter will apply on DB. So, I put this logic here in DAO.
		if (this.contacts != null && this.contacts.size() > 0) {
		    ListUtils.filter(this.contacts.values(), filteredList, new ListUtils.Filter<Contact>() {
				@Override
				public boolean apply(Contact contact) {
					return ( (search == null || search.size() == 0) || 
							search.contains(contact.getAddress().getState()) ); 
				}
		    });
		}
		
		return filteredList;
	}

	public List<Contact> findAllByAreaCode(final String areaCode) {
		List<Contact> filteredList = new ArrayList<>();
		
		// If we use DB, this filter will apply on DB. So, I put this logic here in DAO.
		if (this.contacts != null && this.contacts.size() > 0) {
		    ListUtils.filter(this.contacts.values(), filteredList, new ListUtils.Filter<Contact>() {
				@Override
				public boolean apply(Contact obj) {
					return ( !StringUtils.hasLength(areaCode) || 
								(StringUtils.hasLength(obj.getPhoneNumber()) && 
								StringUtils.startsWithIgnoreCase(obj.getPhoneNumber(), areaCode)) ); 
				}
		    });
		}
		
		return filteredList;
	}

	public List<Contact> filter(final List<String> search, final Date startDate, final Date endDate, final String areaCode) {
		List<Contact> filteredList = new ArrayList<>();
		
//		final Date startOfDate = DateUtils.startOfDay(startDate);
//		final Date endOfDate = DateUtils.endOfDay(endDate);
		
		if (this.contacts != null && this.contacts.size() > 0) {
		    ListUtils.filter(this.contacts.values(), filteredList, new ListUtils.Filter<Contact>() {
		    	
				@Override
				public boolean apply(Contact contact) {
					return ( 
							( !StringUtils.hasLength(areaCode) || 
									(StringUtils.hasLength(contact.getPhoneNumber()) && 
											StringUtils.startsWithIgnoreCase(contact.getPhoneNumber(), areaCode)) )
							
							&&  ( (search == null || search.size() == 0) || 
								search.contains(contact.getAddress().getState()) )
								
							&& ( (startDate == null || endDate == null) || 
								(contact.getLastContactedTime() != null && 
								ContactHelper.between(contact.getLastContactedTime(), startDate, endDate)) )
							); 
				}
		    });
		}
		
		return filteredList;
	}
}
