package com.chock;

import com.chock.service.QueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootPrestoApplicationTests {

	@Autowired
	private QueryService queryService;

	@Test
	public void test() throws Exception{
		queryService.query("funnelSql.txt");
	}
}
