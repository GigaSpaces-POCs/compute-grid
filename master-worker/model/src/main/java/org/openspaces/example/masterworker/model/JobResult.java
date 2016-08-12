package org.openspaces.example.masterworker.model;

import java.sql.Timestamp;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

@SpaceClass
public class JobResult {
	public static final String CREATED = "Created";
	public static final String STARTED = "Started";
	public static final String COMPLETED = "Completed";
	public static final String ENDED = "Ended"; 
	
	private String jobId;
	private String jobStatus;
	private Timestamp jobCreateTime;
	private Timestamp jobEndTime;
	private Timestamp jobStartTime;
	private Timestamp jobCompleteTime;

	@SpaceIndex(type=SpaceIndexType.BASIC)
	@SpaceId (autoGenerate = false)
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Timestamp getJobCreateTime() {
		return jobCreateTime;
	}

	public void setJobCreateTime(Timestamp jobCreateTime) {
		this.jobCreateTime = jobCreateTime;
	}

	public Timestamp getJobEndTime() {
		return jobEndTime;
	}

	public void setJobEndTime(Timestamp jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	public Timestamp getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(Timestamp jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public Timestamp getJobCompleteTime() {
		return jobCompleteTime;
	}

	public void setJobCompleteTime(Timestamp jobCompleteTime) {
		this.jobCompleteTime = jobCompleteTime;
	}

}
