package com.SecuriThingsTest.testing;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Payment {
	
	ePaymentMethod method;
	String orderComment;
	JSONObject jsonFile = new JSONResource().getDetailsFromJson();
	
	public Payment() throws FileNotFoundException, IOException, ParseException {
		this.orderComment = (String) jsonFile.get("orderComment").toString();
		setMethod();
		}

	private void setMethod() {
		String method =  (String) jsonFile.get("payment method").toString();
		if(method.equals("bank")) {
			this.method = ePaymentMethod.BANK_WIRE;
		}else if(method.equals("check")) {
			this.method = ePaymentMethod.CHECK;
		}
	}

	public String getOrderComment() {
		return orderComment;
	}

	public ePaymentMethod getMethod() {
		return method;
	}

}
