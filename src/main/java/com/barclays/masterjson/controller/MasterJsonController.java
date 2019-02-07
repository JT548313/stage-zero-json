package com.barclays.masterjson.controller;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterJsonController {
	@RequestMapping("/")
	public static String moduleIndex() throws IOException, InvalidRemoteException, TransportException, GitAPIException {

		File file = new File("./TestRepo");
		Git git = null;
		/*
		 * try {
		 * 
		 * } catch (GitAPIException gae) {
		 */
		try {
			FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
			Repository repository = repositoryBuilder.setGitDir(new File("./TestRepo")).readEnvironment() // scan
					.findGitDir() // scan up the file system tree
					.setMustExist(true).build();

			PullResult pc = Git.open(file).pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider("JT548313", "Manc@1234")).call();

			System.out.println("Fetched From : " + pc.getFetchedFrom());
			System.out.println("Result : " + pc.isSuccessful());

		} catch (RepositoryNotFoundException ex) {
			System.out.println(ex.getMessage());
			git = Git.cloneRepository().setURI("https://github.com/JT548313/MasterJsonRepo.git")
					.setCredentialsProvider(new UsernamePasswordCredentialsProvider("JT548313", "Manc@1234"))
					.setDirectory(file).call();
		}
		// }

		File[] files = file.listFiles();
		int i = 1;
		for (File f : files) {
			System.out.println("" + i++ + " : " + f.toString());
		}
		return "Greetings from Spring Boot!";
	}

	public static void main(String[] args) throws IOException, GitAPIException {
		System.out.println(MasterJsonController.moduleIndex());
	}
}