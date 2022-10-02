package com.SecuriThingsTest.testing;

public class JSONException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JSONException(String type) {
		String generalString = "Please check README file to see more details about how to enter parameters correctly in JSON file";
		switch (type) {
		case ("payment"):
			System.err.println("There is no such payment method. " + generalString);
			break;
		case ("color"):
			System.err.println("This item is not avaliable in this color. " + generalString);
			break;
		case ("gender"):
			System.err.println("Gender can be male or female. " + generalString);
			break;
		case ("email"):
			System.err.println("Email is invalid. " + generalString);
			break;
		case ("not enough items"):
			System.err.println("Automation needs at least 2 items to purchase " + generalString);
			break;
		default:
			System.err.println(generalString);
		}
		return;
	}
}
