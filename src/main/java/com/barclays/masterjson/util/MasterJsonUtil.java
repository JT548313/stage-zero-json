package com.barclays.masterjson.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barclays.masterjson.beans.GitCredentials;
import com.barclays.masterjson.constants.MasterJsonConstants;
import com.barclays.masterjson.reporting.RequestCorrelation;
import com.barclays.masterjson.service.MasterJsonService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

public class MasterJsonUtil {

	private static final Logger LOG = LoggerFactory.getLogger(MasterJsonService.class);
	
	@SuppressWarnings("deprecation")
	public static JsonParser readJsonFile(String filePath) throws JsonParseException {

		JsonFactory f = new JsonFactory();
		JsonParser jp = null;
		try {
			jp = f.createJsonParser(new File(filePath)); // For reference MasterJsonValidatorConstants.PATTERN_JSON_URL
		} catch (JsonParseException e) {
			LOG.error("{} Error occured while parsing \"{}\" file", RequestCorrelation.getId(), filePath);
			throw e;
		} catch (IOException io) {
			LOG.error("{} I/O Exception occured while parsing \"{}\" file", RequestCorrelation.getId(), filePath);
			io.printStackTrace();
		}

		return jp;

	}

	/**
	 * This method is used to read property files.
	 * @return
	 * @throws IOException
	 */
	public Properties readProperetyFile() throws IOException {
		InputStream inputStream = null;
		Properties prop = null;
		try {
			prop = new Properties();
			String propFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
				
			} else {
				LOG.error("{} property file '\" + propFileName + \"' not found in the classpath", RequestCorrelation.getId());
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

		} catch (Exception e) {
			LOG.error("{} property file '\" + propFileName + \"' not found in the classpath", RequestCorrelation.getId());
		} finally {
			inputStream.close();
		}
		return prop;
	}
	
	public static void main(String[] args) throws IOException {
		
		Properties prop = new MasterJsonUtil().readProperetyFile();
		
		System.out.println(prop.getProperty(MasterJsonConstants.GIT_USER));
		
		
	}

}
