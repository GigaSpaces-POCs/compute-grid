package org.openspaces.example.masterworker.model;

import com.gigaspaces.annotation.pojo.SpaceFifoGroupingProperty;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class Base {
	private Integer jobID;
	private String taskID;	
	private Object payload;
	
	public Base (){}

	@SpaceIndex(type=SpaceIndexType.BASIC)
	@SpaceRouting
	@SpaceFifoGroupingProperty
	public Integer getJobID() {
		return jobID;
	}
	public void setJobID(Integer jobID) {
		this.jobID = jobID;
	}
	
	@SpaceId(autoGenerate=false)
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
}