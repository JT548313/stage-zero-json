package com.barclays.masterjson.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

public class MasterJsonUtil {

	@SuppressWarnings("deprecation")
	public static Object readJsonFile(String filePath) {
		
		JsonFactory f = new JsonFactory();
	    JsonParser jp = null;
		try {
			jp = f.createJsonParser(new File(filePath)); // For reference MasterJsonValidatorConstants.PATTERN_JSON_URL
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException io) {
			// TODO Auto-generated catch block
			io.printStackTrace();
		}
		
		return jp;
		
	}
	
}
