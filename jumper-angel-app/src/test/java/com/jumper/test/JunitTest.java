package com.jumper.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.jumper.angel.app.home.entity.PregnantAntenatalExaminationInfo;
import com.jumper.angel.app.home.service.PregnantAntenatalExaminationInfoService;
import com.jumper.junit.Junit4ClassRunner;

@RunWith(Junit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring-context.xml"})
public class JunitTest {
	
	@Autowired
	private PregnantAntenatalExaminationInfoService pregnantService;
	
	@Test
	public void testPregnantWeek(){
		PregnantAntenatalExaminationInfo findPrenatalRemind = pregnantService.findPrenatalRemind(26);
		System.out.println(findPrenatalRemind.getExaminationNumbers()+"*********************");
		
	}
}
