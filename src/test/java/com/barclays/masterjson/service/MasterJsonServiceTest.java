package com.barclays.masterjson.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.junit.Before;
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
	
	@Before
	public void setup() {
	}

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

}
