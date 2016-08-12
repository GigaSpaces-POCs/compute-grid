package org.openspaces.example.masterworker.nonblocking;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler;
import org.openspaces.example.masterworker.model.JobResult;
import org.openspaces.example.masterworker.model.Request;
import org.openspaces.example.masterworker.model.Result;
import org.openspaces.example.masterworker.model.WorkerProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.client.WriteModifiers;
import com.gigaspaces.query.IdQuery;
import com.j_spaces.core.client.SQLQuery;

@SuppressWarnings("restriction")
@EventDriven @Polling (concurrentConsumers=2, gigaSpace="gigaMySpace")
@Component
public class Worker {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    protected GigaSpace gigaMySpace;
    public static String hostName;
    public static long processId;
    public static String id;
    public static Timestamp startTime = new Timestamp(System.currentTimeMillis());
	
	public Worker ()
	{
		logger.info("Worker started!");
	}
	
	@PostConstruct
	public void construct () {
		logger.info("Worker construct called!");
		try {
			RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
			String jvmName = runtimeBean.getName();

			hostName = InetAddress.getLocalHost().getHostName();
			processId = Long.valueOf(jvmName.split("@")[0]);
			id = processId + "@"+ hostName;
			logger.info("From Worker JVM PID  = " + processId + " for hostName=" + hostName + " and JVM Name = " + jvmName);
			
			WorkerProcess workerProcess = new WorkerProcess();
			workerProcess.setHostName(hostName);
			workerProcess.setProcessId(processId);
			workerProcess.setId(id);
			workerProcess.setStartDateTime(startTime);
			workerProcess.setLastUpdateDateTime(new Timestamp(System.currentTimeMillis()));
			gigaMySpace.write(workerProcess, 10000L, 500, WriteModifiers.UPDATE_OR_WRITE);
			new WorkerHeartbeat(gigaMySpace);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
    @EventTemplate
    SQLQuery<Request> template() {
    	SQLQuery<Request> query = new SQLQuery<Request>(Request.class, "priority >= 0");
        return query;
    }
    
    @ReceiveHandler
    ReceiveOperationHandler receiveHandler() {
        SingleTakeReceiveOperationHandler receiveHandler = new SingleTakeReceiveOperationHandler();
        receiveHandler.setNonBlocking(true);
        receiveHandler.setNonBlockingFactor(10);
        return receiveHandler;
    }

    @SpaceDataEvent
    public Result execute(Request request) {
        //process Data here
		IdQuery<JobResult> idQuery = new IdQuery<JobResult>(JobResult.class, request.getJobID()+"");
		gigaMySpace.change(idQuery, new ChangeSet().set("jobStatus", JobResult.STARTED)
									.set("jobStartTime", new Timestamp(System.currentTimeMillis())));
		logger.info("from worker.execute processing jobId=" + request.getJobID() + ", taskId=" + request.getTaskID() + ", priority=" + request.getPriority());
    	try {Thread.sleep(1000);
    	} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	Result reponse = new Result ();
    	reponse.setJobID(request.getJobID());
    	reponse.setTaskID(request.getTaskID());
    	return reponse;
    }
}