package org.openspaces.example.masterworker.model;

import java.sql.Timestamp;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

public class BaseProcess {
	private String id;
	private String hostName;
	private long processId;
	private Timestamp startDateTime;
	private Timestamp lastUpdateDateTime;
	
	@SpaceIndex(type=SpaceIndexType.BASIC)
	@SpaceId (autoGenerate = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public long getProcessId() {
		return processId;
	}
	public void setProcessId(long processId) {
		this.processId = processId;
	}
	public Timestamp getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Timestamp getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(Timestamp lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
}