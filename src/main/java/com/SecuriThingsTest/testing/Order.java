package com.SecuriThingsTest.testing;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;



public class Order {
	
	int MINIMUM_ITEMS = 2;
	
	JSONArray itemsListWithAmount;

	HashMap<String,Integer> itemsQuantity = new HashMap<String,Integer>();
	HashMap<String,String> itemsColor = new HashMap<String,String>();
	HashMap<String,String> itemsSize = new HashMap<String,String>();
	
	
	public Order() throws FileNotFoundException, IOException, ParseException, JSONException {
		//NEED TO THROW EXCEPTION IF UNDER 2
		this.itemsListWithAmount = (JSONArray) new JSONResource().getDetailsFromJson().get("order");
		setItemsWithQuantity();
		if(calculateAtLeastTwoItems()){
			itemsColor = setAttributeList("color");
			itemsSize = setAttributeList("size");
		}
		else {
			throw new JSONException("not enough items");
		}
	}


	public void setItemsWithQuantity() {
		for (Object item : this.itemsListWithAmount) {
			JSONObject jsonItem = (JSONObject) item;
			this.itemsQuantity.put(jsonItem.get("name").toString(), Integer.parseInt(jsonItem.get("quantity").toString()));
		}
	}
	
	public HashMap<String, Integer> getItemsWithQuantity(){
		return this.itemsQuantity;
	}


	private boolean calculateAtLeastTwoItems() {
		int sum = 0;
		for (Object item : this.itemsListWithAmount) {
			int amount = Integer.parseInt(((JSONObject) item).get("quantity").toString());
			sum += amount;
		}
		return sum >= MINIMUM_ITEMS;
	}

	//For quantity hashmap - use getItemsWithQuantity function
	public HashMap<String,String> setAttributeList(String attribute) {
		HashMap<String,String> list = new HashMap<String,String>();
		for (Object item : this.itemsListWithAmount) {
			JSONObject jsonItem = (JSONObject) item;
			list.put(jsonItem.get("name").toString(), capitalizeString(jsonItem.get(attribute).toString().toLowerCase()));
		}
		return list;
	}
	
	public HashMap<String, String> getItemsColor() {
		return itemsColor;
	}


	public HashMap<String, String> getItemsSize() {
		return itemsSize;
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
