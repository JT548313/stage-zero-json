package com.barclays.masterjson.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MasterJsonServiceTest {

	@Autowired
	private MasterJsonService masterJsonService;


	@Test
	public void downloadMasterJsonRepoTest() {

		boolean result = false;
		try {
			result = masterJsonService.downloadMasterJsonRepo();
		} catch (NoWorkTreeException | IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(result);
	}

	@Test
	public void downloadMasterJsonRepoFailureTest() {

		boolean result = false;
		try {
			result = masterJsonService.downloadMasterJsonRepo();
		} catch (NoWorkTreeException | IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(result);
	}

	@Test
	public void fetchPatternByNameTest() {

		String result = null;
		result = masterJsonService.fetchPatternByName("Java Jboss Openshift V3");
		assertNotNull(result);
	}

	
	@Test
	public void fetchPatternByIdTest() {

		String result = null;
		result = masterJsonService.fetchPatternById("java-jboss-openshift-v1");
		assertNotNull(result);
	}
	

	@Test
	public void fetchModuleByNameTest() {

		String result = null;
		result = masterJsonService.fetchModuleByName("OpenShift v3 Checkout");
		assertNotNull(result);
	}

	
	@Test
	public void fetchModuleByIdTest() {

		String result = null;
		result = masterJsonService.fetchModuleById("osv3-preflight.1.0");
		assertNotNull(result);
	}
	
	@Test
	public void fetchModuleParamsByReferenceTest() {

		String result = null;
		result = masterJsonService.fetchModuleParamsByReference("osv3-preflight.1.0.json");
		System.out.println(result);
		assertNotNull(result);
	}
}
