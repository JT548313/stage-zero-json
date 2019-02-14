package com.barclays.masterjson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.masterjson.service.MasterJsonService;

@RestController
public class MasterJsonController {

	private final MasterJsonService masterJsonService;

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
	public String fetchMasterJsonIndex() throws Exception {
		if (masterJsonService.downloadMasterJsonRepo()) {
			System.out.println("Success");
			return "Meta Json downloaded";
		} else
			return "Meta Json download failed";
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/api/v1/pattern/name/{name}")
	public String fetchPatternByName(@PathVariable String name) throws Exception {
		if (masterJsonService.fetchPatternByName(name)!= null) {
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

}