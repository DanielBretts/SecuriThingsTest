package com.SecuriThingsTest.testing;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONResource {
	public JSONResource() throws FileNotFoundException, IOException, ParseException {
		getDetailsFromJson();
	}

	
	
	JSONObject getDetailsFromJson() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader fr = new FileReader(".\\JSONFiles\\details.json");
		Object obj = jsonParser.parse(fr);
		return (JSONObject) obj;
	}
}
