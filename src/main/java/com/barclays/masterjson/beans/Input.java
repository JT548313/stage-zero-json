package com.barclays.masterjson.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Input {

	String type;

	boolean required;

	@JsonProperty("default")
	String defaultValue;

	int minLength;

	int maxLength;

	String allowedPattren;

	String constraintDescription;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getAllowedPattren() {
		return allowedPattren;
	}

	public void setAllowedPattren(String allowedPattren) {
		this.allowedPattren = allowedPattren;
	}

	public String getConstraintDescription() {
		return constraintDescription;
	}

	public void setConstraintDescription(String constraintDescription) {
		this.constraintDescription = constraintDescription;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	@JsonProperty("default")
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
