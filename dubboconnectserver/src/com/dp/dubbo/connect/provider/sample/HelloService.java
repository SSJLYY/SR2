package com.dp.dubbo.connect.provider.sample;

import com.dp.dubbo.connect.dto.HelloDto;

public interface HelloService
{
	String saySomething(HelloDto something) throws Exception;
}
