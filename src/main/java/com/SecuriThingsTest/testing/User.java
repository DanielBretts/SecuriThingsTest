package com.SecuriThingsTest.testing;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class User {

	Boolean signNewsletters;
	Boolean recieveSpecialOffers;
	Boolean isUSA;

	String email;
	String password;
	String gender;
	String firstName;
	String lastName;
	String company;
	String address;
	String stateUSA;
	String postalCode;
	String secondAddress;
	String city;
	String additionalInfo;
	String homePhone;
	String mobilePhone;
	Hashtable<String, String> birthDate = new Hashtable<String, String>();

	JSONObject details = new JSONResource().getDetailsFromJson();
	JSONArray registrationDetails = (JSONArray) details.get("registrationDetails");
//	    "day" : "14",
//	    "month" : "8",
//	    "year" : "1997"
//	  

	public User() throws FileNotFoundException, IOException, ParseException {
		JSONObject basicDetails = (JSONObject) registrationDetails.get(0);
		this.signNewsletters = convertStringToBool((String) details.get("signNewsletters").toString().toLowerCase());
		this.recieveSpecialOffers = convertStringToBool(
				(String) details.get("recieveSpecialOffers").toString().toLowerCase());
		this.isUSA = convertStringToBool((String) basicDetails.get("isUSA").toString().toLowerCase());
		this.email = (String) basicDetails.get("email");
		this.password = (String) basicDetails.get("password");
		this.gender = (String) basicDetails.get("gender");
		this.firstName = (String) basicDetails.get("firstName");
		this.lastName = (String) basicDetails.get("lastName");
		this.company = (String) basicDetails.get("company");
		this.address = (String) basicDetails.get("address");
		this.secondAddress = (String) basicDetails.get("secondAddress");
		this.stateUSA = capitalizeString((String) basicDetails.get("stateUSA").toString().toLowerCase());
		this.postalCode = (String) basicDetails.get("postalCode").toString().toLowerCase();
		this.city = (String) basicDetails.get("city");
		this.additionalInfo = (String) basicDetails.get("additionalInfo");
		this.homePhone = (String) basicDetails.get("homePhone");
		this.mobilePhone = (String) basicDetails.get("mobilePhone");
		setBirthDate();
	}

	//If input in JSON file is wrong - it will be false
	private Boolean convertStringToBool(String str) {
		return str.equals("true");
	}

	private void setBirthDate() {
		JSONObject dateOfBirth = (JSONObject) registrationDetails.get(1);
		String[] birthdayParameters = { "day", "month", "year" };
		for (String str : birthdayParameters) {
			String value = (String) dateOfBirth.get(str);
			this.birthDate.put(str, value);
		}
	}

	public Boolean getSignNewsletters() {
		return signNewsletters;
	}

	public Boolean getRecieveSpecialOffers() {
		return recieveSpecialOffers;
	}


	public Boolean getIsUSA() {
		return isUSA;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getGender() {
		return gender;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCompany() {
		return company;
	}

	public String getStateUSA() {
		return stateUSA;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getAddress() {
		return address;
	}
	
	public String getSecondAddress() {
		return secondAddress;
	}

	public String getCity() {
		return city;
	}

	
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}


	public Hashtable<String, String> getBirthDate() {
		return this.birthDate;
	}

	private static String capitalizeString(String str) {
		String[] strArray = str.split(" ");
		StringBuilder capString = new StringBuilder();
		for (int i = 0; i < strArray.length; i++) {
			if (!strArray[i].equals("of")) {
				strArray[i] = strArray[i].substring(0, 1).toUpperCase() + strArray[i].substring(1);
			}
			if (i != strArray.length - 1) {
				capString.append(strArray[i] + " ");
			} else {
				capString.append(strArray[i]);
			}
		}
		return capString.toString();
	}
	

}
