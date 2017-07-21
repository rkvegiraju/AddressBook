1. JUnit Test
	* Use AddressBookControllerIntegrationTest.java to test all exposed APIs

2. Regular Test - through browser or API testing tools
	* Get All Contacts
		URL: http://localhost:8088/addressbook/contacts
		HTTPMethod: GET
		
	* Get Contact By ID
		URL: http://localhost:8088/addressbook/contacts/1
		HTTPMethod: GET

	* Create Contact
		URL: http://localhost:8088/addressbook/contacts
		HTTPMethod: POST
		Request Body: {"id":3,"firstName":"David","lastName":"Thomas","phoneNumber":"408-444-5566",
							"address":{"street":"260 N Mathilda Ave","city":"Sunnyvale","state":"CA","zip":"94086"},
							"email":"david@email.com","lastContactedTime":"2017-07-21 22:59:22"}
		
	* Update Contact
		URL: http://localhost:8088/addressbook/contacts
		HTTPMethod: PUT
		Request Body: {"id":3,"firstName":"Jon","lastName":"David","phoneNumber":"408-444-5566",
							"address":{"street":"440 N Wolf Rd","city":"Sunnyvale","state":"CA","zip":"94087"},
							"email":"jon@email.com","lastContactedTime":"2017-07-21 22:59:22"}
		
	* Delete Contact
		URL: http://localhost:8088/addressbook/contacts
		HTTPMethod: DELETE
		
	* Search - 
		URL: http://localhost:8088/addressbook/contacts/search?areacode=408&states=GA, VA, CA&startdate=2017-07-21&enddate=2017-07-23
		HTTPMethod: GET
		Note: All parameters are optional
		