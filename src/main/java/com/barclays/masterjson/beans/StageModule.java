package com.barclays.masterjson.beans;

import java.util.ArrayList;

public class StageModule {

	String stage;
	ArrayList<String> moduleIds = new ArrayList<String>();

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public ArrayList<String> getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(ArrayList<String> moduleIds) {
		this.moduleIds = moduleIds;
	}

}
