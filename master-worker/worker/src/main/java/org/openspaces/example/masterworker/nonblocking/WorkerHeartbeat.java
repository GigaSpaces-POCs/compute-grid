package org.openspaces.example.masterworker.nonblocking;

import java.sql.Timestamp;
import java.util.logging.Logger;

import org.openspaces.core.GigaSpace;
import org.openspaces.example.masterworker.model.WorkerProcess;

import com.gigaspaces.client.WriteModifiers;

public class WorkerHeartbeat implements Runnable {
    Logger logger = Logger.getLogger(this.getClass().getName());
    protected GigaSpace gigaMySpace;
	
	public WorkerHeartbeat(GigaSpace gigaMySpace) {
		logger.info("WorkerHeartbeat constructor called.");
		this.gigaMySpace = gigaMySpace;
		new Thread(this).start();
	}

	public void run() {
		while (true) {
			try {
				WorkerProcess workerProcess = new WorkerProcess();
				workerProcess.setHostName(Worker.hostName);
				workerProcess.setProcessId(Worker.processId);
				workerProcess.setId(Worker.id);
				workerProcess.setStartDateTime(Worker.startTime);
				workerProcess.setLastUpdateDateTime(new Timestamp(System.currentTimeMillis()));
				gigaMySpace.write(workerProcess, 10000L, 500, WriteModifiers.UPDATE_OR_WRITE);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}