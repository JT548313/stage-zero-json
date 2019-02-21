package com.barclays.masterjson.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclays.masterjson.beans.PipelinePatterns;
import com.barclays.masterjson.beans.ScriptInputParams;
import com.barclays.masterjson.constants.MasterJsonConstants;
import com.barclays.masterjson.exception.LocalRepoNotCleanException;
import com.barclays.masterjson.reporting.RequestCorrelation;
import com.barclays.masterjson.beans.GitCredentials;
import com.barclays.masterjson.beans.Module;
import com.barclays.masterjson.beans.ModuleIndex;
import com.barclays.masterjson.beans.PipelinePattern;
import com.barclays.masterjson.util.MasterJsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MasterJsonService {

	private static final Logger LOG = LoggerFactory.getLogger(MasterJsonService.class);
	private GitCredentials credentials;

	public GitCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(GitCredentials credentials) {
		this.credentials = credentials;
	}

	@Autowired
	public MasterJsonService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean downloadMasterJsonRepo() throws IOException, NoWorkTreeException, GitAPIException {

		File file = new File(MasterJsonConstants.MASTER_JSON_REPO_PATH);
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
			FileRepository fr = repositoryBuilder
					.setGitDir(new File(MasterJsonConstants.MASTER_JSON_REPO_PATH + "/.git")).readEnvironment() // scan
					.findGitDir() // scan up the file system tree
					.setMustExist(true).build();

			LOG.info("{} Clone of {} branch of Master Json repository exists", RequestCorrelation.getId(),
					fr.getBranch());
			/*
			 * Check status of local repository if local repo have been altered stop the
			 * execution via throwing the exception
			 */
			Git gitLocalRepo = Git.open(file);
			Status status = gitLocalRepo.status().call();

			this.setCredentials(new MasterJsonUtil().readProperetyFile());

			if (status.isClean()) {
				LOG.info("{} Clone of {} branch of Master Json repository is in clean state",
						RequestCorrelation.getId(), fr.getBranch());
				LOG.info("{} Git Pull for {} branch of Master Json repository triggered", RequestCorrelation.getId(),
						fr.getBranch());
				/*
				 * Repository exists, execute git-pull command
				 */
				PullResult pc = Git.open(file).pull()
						.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.getCredentials().getUser(),
								this.getCredentials().getPassword()))
						.call();

			} else {
				/*
				 * Local repository has unstaged changes. Throw Server side exception and flag
				 * end user.
				 */
				LOG.info(
						"{} Clone of {} branch of Master Json repository is not in clean state. "
								+ "LocalRepoNotCleanException thrown to UI with HTTPS 500 error code",
						RequestCorrelation.getId(), fr.getBranch());

				throw new LocalRepoNotCleanException(
						"Request failed with HTTPS 500 error code. Clone of Master Json repository is corrupted");
			}

		} catch (RepositoryNotFoundException ex) {

			try {

				LOG.info(
						"{} No existing repo found \nClone of Master Json repository started", RequestCorrelation.getId());
				git = Git.cloneRepository().setURI(MasterJsonConstants.MASTER_JSON_STASH_REPO_PATH)
						.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.getCredentials().getUser(),
								this.getCredentials().getPassword()))
						.setDirectory(file).call();

			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				LOG.error("{} Git Clone failed with {} error", RequestCorrelation.getId(), e.getMessage());
			}

		} catch (IOException e) {
			LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
			e.printStackTrace();
		} catch (NoWorkTreeException e) {
			LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
			e.printStackTrace();
		} catch (GitAPIException e) {
			LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
			e.printStackTrace();
		} catch (LocalRepoNotCleanException e) {
			LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
			e.printStackTrace();
		}

		/*
		 * File[] filesOut = file.listFiles(); System.out.println("Files in TestRepo : "
		 * + filesOut.length); int j = 1; for (File f : filesOut) {
		 * System.out.println("" + j++ + " : " + f.toString()); }
		 */
		return true;

	}

	public String fetchPatternByName(String name) throws JsonParseException {
		// TODO Auto-generated method stub
		JsonParser jp = (JsonParser) MasterJsonUtil
				.readJsonFile(MasterJsonConstants.MASTER_JSON_REPO_PATH + "/Patterns.json");
		ArrayList<PipelinePattern> patternList = null;
		String patternJson = null;
		ObjectMapper mapper = new ObjectMapper();
		PipelinePatterns patterns = null;
		try {
			patterns = mapper.readValue(jp, PipelinePatterns.class);
		} catch (IOException e) {
			LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
			e.printStackTrace();
		}
		patternList = (ArrayList<PipelinePattern>) patterns.getPatterns();

		for (PipelinePattern pattern : patternList) {
			if (pattern.getDisplay().getName().equalsIgnoreCase(name))
				try {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pattern);
				} catch (JsonProcessingException e) {
					LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
					e.printStackTrace();
				}
		}

		return patternJson;
	}

	public String fetchPatternById(String id) throws JsonParseException {
		// TODO Auto-generated method stub
		JsonParser jp = (JsonParser) MasterJsonUtil
				.readJsonFile(MasterJsonConstants.MASTER_JSON_REPO_PATH + "/Patterns.json");
		ArrayList<PipelinePattern> patternList = null;
		String patternJson = null;
		ObjectMapper mapper = new ObjectMapper();
		PipelinePatterns patterns = null;
		try {
			patterns = mapper.readValue(jp, PipelinePatterns.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		patternList = (ArrayList<PipelinePattern>) patterns.getPatterns();

		for (PipelinePattern pattern : patternList) {
			if (pattern.getId().equalsIgnoreCase(id))
				try {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pattern);
				} catch (JsonProcessingException e) {
					LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
					e.printStackTrace();
				}
		}

		return patternJson;
	}

	public String fetchModuleById(String id) throws JsonParseException {
		// TODO Auto-generated method stub
		JsonParser jp = (JsonParser) MasterJsonUtil
				.readJsonFile(MasterJsonConstants.MASTER_JSON_REPO_PATH + "/Module-Index.json");
		ArrayList<Module> moduleList = null;
		String moduleJson = null;
		ObjectMapper mapper = new ObjectMapper();
		ModuleIndex moduleIndex = null;
		try {
			moduleIndex = mapper.readValue(jp, ModuleIndex.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		moduleList = (ArrayList<Module>) moduleIndex.getModule();

		for (Module module : moduleList) {
			if (module.getId().equalsIgnoreCase(id))
				try {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(module);
				} catch (JsonProcessingException e) {
					LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
					e.printStackTrace();
				}
		}

		return moduleJson;
	}

	public String fetchModuleByName(String name) throws JsonParseException {
		// TODO Auto-generated method stub
		JsonParser jp = (JsonParser) MasterJsonUtil
				.readJsonFile(MasterJsonConstants.MASTER_JSON_REPO_PATH + "/Module-Index.json");
		ArrayList<Module> moduleList = null;
		String moduleJson = null;
		ObjectMapper mapper = new ObjectMapper();
		ModuleIndex moduleIndex = null;
		try {
			moduleIndex = mapper.readValue(jp, ModuleIndex.class);
		} catch (IOException e) {
			LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
			e.printStackTrace();
		}
		moduleList = (ArrayList<Module>) moduleIndex.getModule();

		for (Module module : moduleList) {
			if (module.getMetadata().getName().equalsIgnoreCase(name))
				try {
					return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(module);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return moduleJson;
	}

	public String fetchModuleParamsByReference(String $ref) throws JsonParseException {
		// TODO Auto-generated method stub
		JsonParser jp = (JsonParser) MasterJsonUtil
				.readJsonFile(MasterJsonConstants.MASTER_JSON_REPO_PATH + "/" + $ref);
		// ArrayList<ScriptInputParameter> paramList = null;
		String paramJson = null;
		ObjectMapper mapper = new ObjectMapper();
		ScriptInputParams params = null;
		try {
			params = mapper.readValue(jp, ScriptInputParams.class);
			paramJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
		} catch (IOException e) {
			LOG.error("{} Git Operation failed with {} error", RequestCorrelation.getId(), e.getMessage());
			e.printStackTrace();
		}
		return paramJson;
	}

}
