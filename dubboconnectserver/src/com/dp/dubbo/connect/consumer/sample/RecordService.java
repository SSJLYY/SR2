package com.dp.dubbo.connect.consumer.sample;

import com.dp.dubbo.connect.dto.HelloDto;


public interface RecordService
{
	String recordSomething(HelloDto something) throws Exception;
}
