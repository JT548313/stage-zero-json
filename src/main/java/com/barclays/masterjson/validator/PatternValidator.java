package com.barclays.masterjson.validator;

import java.io.IOException;
import java.util.ArrayList;

import com.barclays.masterjson.beans.Message;
import com.barclays.masterjson.beans.PipelinePattern;
import com.barclays.masterjson.beans.PipelinePatterns;
import com.barclays.masterjson.constants.MasterJsonConstants;
import com.barclays.masterjson.controller.JsonValidator;
import com.barclays.masterjson.util.FreeTextValidations;
import com.barclays.masterjson.util.JsonValidatorUtil;
import com.barclays.masterjson.util.MasterJsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @version 1.0.0
 *
 */
public class PatternValidator {

	/**
	 * This method is responsible to read Pattern JSON file from Git Repository.
	 * Loaded JSON will be parsed for validity. As per the requirement many business
	 * rules will be checked.
	 * 
	 * @return
	 *
	 * @return messageBean
	 * @throws IOException
	 * @throws JsonParseException
	 */
	public static ArrayList<Message> parsePatternIndexJson() {
		JsonParser jp = null;
		try {
			jp = MasterJsonUtil.readJsonFile(MasterJsonConstants.PATTERN_JSON_URL);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		PipelinePatterns patterns = null;
		try {
			patterns = mapper.readValue(jp, PipelinePatterns.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<PipelinePattern> patternList = patterns.getPatterns();

		ArrayList<Message> messages = new ArrayList<Message>();

		//Validate description field in metadata. 
		for (PipelinePattern pattern : patternList) {
			messages = FreeTextValidations.validateFreeText(pattern.getMetadata().getDescription());
		}

		return messages;

	}

	public static void main(String[] args) {
		PatternValidator p = new PatternValidator();
		p.parsePatternIndexJson();
	}

}