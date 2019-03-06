package com.barclays.masterjson.validator;

import java.io.IOException;
import java.util.ArrayList;

import com.barclays.masterjson.beans.Message;
import com.barclays.masterjson.beans.Module;
import com.barclays.masterjson.beans.ModuleIndex;
import com.barclays.masterjson.constants.MasterJsonConstants;
import com.barclays.masterjson.util.DateFormatValidations;
import com.barclays.masterjson.util.MasterJsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModulesValidator {

	public void parseModuleIndexJson() {
		final ArrayList<Message> messages = new ArrayList<Message>();
		// Read Module Index JSON file from local repo
		JsonParser jp = null;
		try {
			jp = MasterJsonUtil.readJsonFile(MasterJsonConstants.MODULE_INDEX_JSON_URL);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		ModuleIndex modules = null;
		try {
			modules = mapper.readValue(jp, ModuleIndex.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Module> moduleList = modules.getModule();

		for(Module module: moduleList) {
			
		// Date Format validation	
		messages.addAll(DateFormatValidations.validateDateFormat(module.getValidity().getNotBefore().toString()));
		
		// notBefore date should not be after notAfter date. update the code.
		messages.addAll(DateFormatValidations.checkForPastDate(module.getValidity().getNotBefore(), messages));
		}
		//

	}

}
