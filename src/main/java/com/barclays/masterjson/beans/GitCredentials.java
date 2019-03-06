package com.barclays.masterjson.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * To store Git User Credentials from config.propertoies file
 * 
 * @author JayatiNaik
 *
 */
@ConfigurationProperties("git")
public class GitCredentials {

	String user;
	String password;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
