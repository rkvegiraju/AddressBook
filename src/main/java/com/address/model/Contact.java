package com.address.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Contact {
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Address address;
	private String email;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastContactedTime;
	
	public Contact() { }
	
	public Contact(int id, String firstName, String lastName, String phoneNumber, 
			String email, Address address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		lastContactedTime = new Date();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String name) {
		this.firstName = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLastContactedTime() {
		return lastContactedTime;
	}
	public void setLastContacedTime(Date lastContactedTime) {
		this.lastContactedTime = lastContactedTime;
	}
}
