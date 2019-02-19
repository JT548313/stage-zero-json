package com.barclays.masterjson.beans;

import java.util.ArrayList;

public class Module {
	String id;
	Metadata metadata;
	String stage;
	Validity validity;
	String $ref;
	ArrayList<String> pipelineTypes = new ArrayList<String>();
	ArrayList<String> depends = new ArrayList<String>();
	ArrayList<String> tags = new ArrayList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public ArrayList<String> getPipelineTypes() {
		return pipelineTypes;
	}

	public void setPipelineTypes(ArrayList<String> pipelineTypes) {
		this.pipelineTypes = pipelineTypes;
	}

	public Validity getValidity() {
		return validity;
	}

	public void setValidity(Validity validity) {
		this.validity = validity;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String get$ref() {
		return $ref;
	}

	public void set$ref(String $ref) {
		this.$ref = $ref;
	}

	public ArrayList<String> getDepends() {
		return depends;
	}

	public void setDepends(ArrayList<String> depends) {
		this.depends = depends;
	}

}
