package com.dp.dubbo.connect.provider.sample.impl;

import com.dp.dubbo.connect.consumer.sample.RecordService;
import com.dp.dubbo.connect.dto.HelloDto;
import com.dp.dubbo.connect.provider.sample.HelloService;
import de.hybris.platform.core.Registry;


public class HelloServiceImpl implements HelloService
{
	private RecordService recordService;

	@Override
	public String saySomething(HelloDto something) throws Exception
	{
		recordService = Registry.getApplicationContext().getBean("recordService", RecordService.class);
		if(something == null){
			throw new RuntimeException("error");
		}

		return something.getContent()+recordService.recordSomething(something);
	}
}
