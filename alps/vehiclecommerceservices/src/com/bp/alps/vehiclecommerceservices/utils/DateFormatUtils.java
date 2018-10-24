package com.bp.alps.vehiclecommerceservices.utils;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormatUtils
{
	private static final org.apache.log4j.Logger LOG = Logger.getLogger(DateFormatUtils.class);

	public static final String dateFormat = "yyyy-MM-dd";
	public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String getHoure = "yyyy-MM-dd HH:mm:ss";
	public static final String TimeFormat = "HH:mm:ss";
	public static final String hoursFormat = "HH";

	public static Date getToday(){
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try
		{
			return formatter.parse(formatter.format(new Date()));
		}catch (ParseException e){
			return new Date();
		}
	}

	public static Date getTomorrow(){
		Calendar calendarend = Calendar.getInstance();
		calendarend.setTime(new Date());
		calendarend.add(Calendar.DAY_OF_YEAR, 1);

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try
		{
			return formatter.parse(formatter.format(calendarend.getTime()));
		}catch (ParseException e){
			return new Date();
		}
	}

	public static String getDateString(String type, Date date){
		SimpleDateFormat formatter = getFormat(type);
		return formatter.format(date);
	}

	public static Date getBeforetimeKeyDate(String type){
		Date date = new Date();
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if(type.equals("onemonth"))
			{
				cal.add(Calendar.MONTH, -1);
			}
			if(type.equals("twomonths"))
			{
				cal.add(Calendar.MONTH, -2);
			}
			if(type.equals("threemonths"))
			{
				cal.add(Calendar.MONTH, -3);
			}
			if(type.equals("halfyear"))
			{
				cal.add(Calendar.MONTH, -6);
			}
			if(type.equals("oneyear"))
			{
				cal.add(Calendar.YEAR, -1);
			}
			return cal.getTime();
		}catch (Exception e){
			return null;
		}
	}

	public static Date getDate(String type, String date){
		SimpleDateFormat formatter = getFormat(type);
		try
		{
			return (Date)formatter.parseObject(date);
		}catch (Exception e){
			LOG.debug(e.getMessage());
			return null;
		}
	}

	public static SimpleDateFormat getFormat(String type)
	{
		SimpleDateFormat formatter;
		if(type.equals("date")){
			formatter = new SimpleDateFormat(dateFormat);
		}else if(type.equals("datetime")){
			formatter = new SimpleDateFormat(dateTimeFormat);
		}else if(type.equals("time")){
			formatter = new SimpleDateFormat(TimeFormat);
		}else if(type.equals("hours")){
			formatter = new SimpleDateFormat(hoursFormat);
		}else{
			formatter = new SimpleDateFormat(dateTimeFormat);
		}
		return formatter;
	}

	public static Integer getLeftTime(final Date lastTime, final Integer maxTime){
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastTime);
		cal.add(Calendar.MINUTE, maxTime);
		final Date finalTime = cal.getTime();
		final Date currentDate = new Date();
		final BigDecimal finalTimeNum = new BigDecimal(finalTime.getTime());
		final BigDecimal currentDateNum = new BigDecimal(currentDate.getTime());
		final BigDecimal leftTimeForSecond = finalTimeNum.subtract(currentDateNum);
		final BigDecimal leftTimeForMin =  leftTimeForSecond.divide(new BigDecimal(1000.00), BigDecimal.ROUND_HALF_UP)
				.divide(new BigDecimal(60.00), BigDecimal.ROUND_HALF_UP)
				.setScale(0 , BigDecimal.ROUND_HALF_UP);
		return leftTimeForMin.intValue()>0 ? leftTimeForMin.intValue() : 0;
	}

	public static Date getBeforeTimee(final Integer beforMinu){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 0 - beforMinu);
		return cal.getTime();
	}
}
