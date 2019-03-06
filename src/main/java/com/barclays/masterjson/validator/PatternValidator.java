package com.barclays.masterjson.validator;

import java.io.IOException;
import java.util.ArrayList;

import com.barclays.masterjson.beans.PipelinePattern;
import com.barclays.masterjson.constants.MasterJsonConstants;
import com.barclays.masterjson.controller.JsonValidator;
import com.barclays.masterjson.util.FreeTextValidations;
import com.barclays.masterjson.util.JsonValidatorUtil;
import com.fasterxml.jackson.core.JsonParseException;

/**
 * @version 1.0.0
 *
 */
public class PatternValidator extends JsonValidator {

	/**
	 * This method is responsible to read Pattern JSON file from Git Repository.
	 * Loaded JSON will be parsed for validity. As per the requirement many business
	 * rules will be checked.
	 *
	 * @return messageBean
	 * @throws IOException
	 * @throws JsonParseException
	 */
	@Override
	public void parseMasterJson() {
		ArrayList<PipelinePattern> patternList = JsonValidatorUtil
				.readJsonFile(MasterJsonConstants.PATTERN_JSON_URL);

		for (PipelinePattern pattern : patternList) {
			FreeTextValidations.validateFreeText(pattern.getMetadata().getDescription());
		}

	}
	
	public static void main(String[] args) {
		PatternValidator p = new PatternValidator();
		p.parseMasterJson();
	}

}