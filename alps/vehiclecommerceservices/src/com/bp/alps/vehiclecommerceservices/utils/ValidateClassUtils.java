package com.bp.alps.vehiclecommerceservices.utils;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;


public class ValidateClassUtils
{
	public static boolean isAllFieldNull(final Object obj){
		Class stuCla = (Class) obj.getClass();
		Method[] mes = stuCla.getMethods();
		Optional optional = Arrays.stream(mes)
			.filter(me -> me.getName().startsWith("get"))
			.filter(me -> !me.getName().equals("getClass"))
			.filter(me -> !me.getName().equals("getItemtype"))
			.filter(me -> !me.getName().equals("getItemModelContext"))
			.filter(me -> !me.getName().equals("getMortgage"))
			.filter(me -> !me.getName().equals("getTenantId"))
			.filter(me -> {
				Parameter[] parameter = me.getParameters();
				return parameter == null || (parameter!=null && parameter.length == 0);
			})
			.filter(me -> {
				try
				{
					Object value = me.invoke(obj);
					if(value instanceof Double){
						return (Double)value > 0;
					}
					return value!=null;
				}catch (Exception e){
					return false;
				}
			})
			.findAny();
		return !optional.isPresent();
	}
}
