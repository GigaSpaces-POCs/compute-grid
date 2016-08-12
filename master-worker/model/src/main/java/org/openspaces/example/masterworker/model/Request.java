package org.openspaces.example.masterworker.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.metadata.index.SpaceIndexType;

@SpaceClass
public class Request extends Base{
	private Integer priority;
	
	public Request (){}
	
	@SpaceIndex(type=SpaceIndexType.EXTENDED)
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}