package com.barclays.masterjson.service;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclays.masterjson.Exception.LocalRepoNotCleanException;

@Service
public class MasterJsonService {

	@Autowired
	public MasterJsonService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean downloadMasterJsonRepo() throws IOException, NoWorkTreeException, GitAPIException {

		File file = new File("./TestRepo");
		Git git = null;
		/*
		 * File[] files = file.listFiles(); if (files != null && files.length > 0) {
		 * System.out.println("Files in TestRepo before Pull from Master branch : " +
		 * files.length); int i = 1; for (File f : files) { System.out.println("" + i++
		 * + " : " + f.toString()); } }
		 */

		try {
			FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
			/*
			 * Check if Local repo exists or not. If not, throw Exception, clone the repo
			 * else execute pull command to fetch latest changes.
			 */
			repositoryBuilder.setGitDir(new File("./TestRepo/.git")).readEnvironment() // scan
					.findGitDir() // scan up the file system tree
					.setMustExist(true).build();

			/*
			 * Check status of local repository if local repo have been altered stop the
			 * execution via throwing the exception
			 */
			Git gitLocalRepo = Git.open(file);
			Status status = gitLocalRepo.status().call();

			if (status.isClean()) {
				/*
				 * Repository exists, execute git-pull command
				 */
				PullResult pc = Git.open(file).pull()
						.setCredentialsProvider(new UsernamePasswordCredentialsProvider("JT548313", "Manc@1234"))
						.call();

				System.out.println("Result : " + pc.isSuccessful());
			} else {
				/*
				 * Local repository has unstaged changes. Throw Server side exception and flag
				 * end user.
				 */
				throw new LocalRepoNotCleanException("Request failed with HTTPS 500 error code. Local repo corrupted");
			}

		} catch (RepositoryNotFoundException ex) {

			try {
				git = Git.cloneRepository().setURI("https://github.com/JT548313/MasterJsonRepo.git")
						.setCredentialsProvider(new UsernamePasswordCredentialsProvider("JT548313", "Manc@1234"))
						.setDirectory(file).call();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoWorkTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LocalRepoNotCleanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * File[] filesOut = file.listFiles(); System.out.println("Files in TestRepo : "
		 * + filesOut.length); int j = 1; for (File f : filesOut) {
		 * System.out.println("" + j++ + " : " + f.toString()); }
		 */
		return true;

	}

	public boolean fetchPatternByName() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean fetchPatternById() {
		// TODO Auto-generated method stub
		return false;
	}

}
