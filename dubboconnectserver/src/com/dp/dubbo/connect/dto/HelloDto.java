package com.dp.dubbo.connect.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@SuppressWarnings("serial")
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class HelloDto implements Serializable
{
	private String content;

	public final String getContent() {
		return content;
	}

	public final void setContent(String content) {
		this.content = content;
	}
}
