package com.barclays.masterjson.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.masterjson.reporting.RequestCorrelation;
import com.barclays.masterjson.service.MasterJsonService;

@RestController
public class MasterJsonController {

	private final MasterJsonService masterJsonService;
	
	private static final Logger LOG = LoggerFactory.getLogger(MasterJsonController.class);

	public MasterJsonController() {
		super();
		this.masterJsonService = new MasterJsonService();
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!!";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/masterjson/download")
	public String downloadMasterJsonRepo() throws Exception {
		LOG.info("{} Master Json Download started", RequestCorrelation.getId());
		if (masterJsonService.downloadMasterJsonRepo()) {
			LOG.info("{} Master Json Download Finished", RequestCorrelation.getId());
			return "Master Json downloaded";
		} else
		{
			LOG.info("{} Master Json download failed", RequestCorrelation.getId());
			return "Master Json download failed";
		}
	}

	
	/**
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/masterjson/validation")
	public String validateMasterJsons() throws Exception {
		LOG.info("{} Master Json Validation started", RequestCorrelation.getId());
		if (masterJsonService.validateMasterJson()) {
			LOG.info("{} Master Json Validation Completed", RequestCorrelation.getId());
			return "Master Json validated";
		} else
		{
			LOG.info("{} Master Json validation failed", RequestCorrelation.getId());
			return "Master Json validation failed";
		}
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/pattern/name/{name}")
	public String fetchPatternByName(@PathVariable String name) throws Exception {
		if (masterJsonService.fetchPatternByName(name) != null) {
			System.out.println("Success");
			return masterJsonService.fetchPatternByName(name);
		} else
			System.out.println("Failure");
		return "Search Pattern by name: Failure";
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/pattern/id/{id}")
	public String fetchPatternById(@PathVariable String id) throws Exception {
		if (masterJsonService.fetchPatternById(id) != null) {
			System.out.println("Success");
			return masterJsonService.fetchPatternById(id);
		} else
			System.out.println("Failure");
		return "Search Pattern by id: Failure";
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/module/id/{id}")
	public String modulePatternById(@PathVariable String id) throws Exception {
		if (masterJsonService.fetchPatternById(id) != null) {
			System.out.println("Success");
			return masterJsonService.fetchPatternById(id);
		} else
			System.out.println("Failure");
		return "Search Pattern by id: Failure";
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/module/name/{name}")
	public String modulePatternByName(@PathVariable String name) throws Exception {
		if (masterJsonService.fetchPatternById(name) != null) {
			System.out.println("Success");
			return masterJsonService.fetchPatternById(name);
		} else
			System.out.println("Failure");
		return "Search Pattern by id: Failure";
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/module/reference/{$ref}")
	public String modulePatternByReference(@PathVariable String $ref) throws Exception {
		if (masterJsonService.fetchModuleParamsByReference($ref) != null) {
			System.out.println("Success");
			return masterJsonService.fetchModuleParamsByReference($ref);
		} else
			System.out.println("Failure");
		return "Search Pattern by id: Failure";
	}

}