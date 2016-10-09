package com.scienjus.smartqq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import alimama.PropertiesUtil;

public class QQMonitor {
	
	
	//key=监听的qq群,value=发送的qq群
	public static Map<String,List<Long>>  monitorGroupMap = new HashMap<>();
	
	
	static{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void init()throws Exception{
		Properties propertiesUtil = PropertiesUtil.getProperty(QQMonitor.class.getClassLoader().getResourceAsStream("qq.properties"));
		System.out.println(propertiesUtil);
		
		String gorups = propertiesUtil.getProperty("qq.monitor.group");
		if(StringUtils.isNotBlank(gorups)){
			for(String group:gorups.split("\\_")){
				System.out.println(group);
					String key = group.split("\\>")[0];
					String val = group.split("\\>")[1];
					List<Long> list = new ArrayList<>();
					for(String v:val.split("\\:")){
						list.add(Long.valueOf(v));
					}
					monitorGroupMap.put(key, list);
			}
		}
	}
	
	public static void main(String[] args)throws Exception {
		init();
		
		System.out.println(monitorGroupMap);
	}

}
