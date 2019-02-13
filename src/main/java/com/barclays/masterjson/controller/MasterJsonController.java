package com.barclays.masterjson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.masterjson.service.MasterJsonService;

@RestController
public class MasterJsonController {

	private final MasterJsonService masterJsonService;

	public MasterJsonController() {
		super();
		this.masterJsonService = new MasterJsonService();
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!!";
	}

	@GetMapping("/api/v1/masterjson/download")
	public void fetchMasterJsonIndex() throws Exception {
		if (masterJsonService.downloadMasterJsonRepo()) {
			System.out.println("Success");
			System.exit(0);
		} else
			System.out.println("Failure");
		System.exit(1);
	}
	
	@GetMapping("/api/v1/pattern/name/{name}")
	public void fetchPatternByName() throws Exception {
		if (masterJsonService.fetchPatternByName()) {
			System.out.println("Success");
			System.exit(0);
		} else
			System.out.println("Failure");
		System.exit(1);
	}
	
	@GetMapping("/api/v1/pattern/id/{id}")
	public void fetchPatternById() throws Exception {
		if (masterJsonService.fetchPatternById()) {
			System.out.println("Success");
			System.exit(0);
		} else
			System.out.println("Failure");
		System.exit(1);
	}

}