package com.jumper.junit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.jumper.angel.quartz.PrenatalExaminationRemindJob;

public class ServiceTest extends JUnitDaoBase {
	
	@Autowired
	private PrenatalExaminationRemindJob job;
	
	@Test
	@Rollback(false)
	public void examService() {
		try {
			job.runPrenatalExaminationRemindJob();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
