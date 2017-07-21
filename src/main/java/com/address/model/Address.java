package com.address.model;

public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;

	public Address() { }
	
	public Address(String aStreet, String aCity, String aState, String aZip)
	{ 
		street = aStreet;
		city = aCity;
		state = aState;
		zip = aZip;
	}  

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
