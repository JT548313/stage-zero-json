package com.barclays.masterjson.beans;

import java.util.ArrayList;

public class PipelinePattern {

	String id;
	Metadata metadata;
	ArrayList<StageModule> stageModules= new ArrayList<StageModule>();
	
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
	public Metadata getDisplay() {
		return metadata;
	}
	public void setDisplay(Metadata metadata) {
		this.metadata = metadata;
	}
	public ArrayList<StageModule> getStageModules() {
		return stageModules;
	}
	public void setStageModules(ArrayList<StageModule> stageModules) {
		this.stageModules = stageModules;
	} 
	
	
	
}
