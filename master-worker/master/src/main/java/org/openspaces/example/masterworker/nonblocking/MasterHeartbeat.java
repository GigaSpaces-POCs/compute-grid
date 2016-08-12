package org.openspaces.example.masterworker.nonblocking;

import java.sql.Timestamp;
import java.util.logging.Logger;

import org.openspaces.core.GigaSpace;
import org.openspaces.example.masterworker.model.MasterProcess;

import com.gigaspaces.client.WriteModifiers;

public class MasterHeartbeat implements Runnable {
    Logger logger = Logger.getLogger(this.getClass().getName());
    protected GigaSpace gigaMySpace;
	
	public MasterHeartbeat(GigaSpace gigaMySpace) {
		logger.info("MasterHeartbeat constructor called.");
		this.gigaMySpace = gigaMySpace;
		new Thread(this).start();
	}

	public void run() {
		while (true) {
			try {
				MasterProcess masterProcess = new MasterProcess();
				masterProcess.setHostName(Master.hostName);
				masterProcess.setProcessId(Master.processId);
				masterProcess.setId(Master.id);
				masterProcess.setStartDateTime(Master.startTime);
				masterProcess.setLastUpdateDateTime(new Timestamp(System.currentTimeMillis()));
				gigaMySpace.write(masterProcess, 10000L, 500, WriteModifiers.UPDATE_OR_WRITE);
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