package com.scienjus.smartqq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.GroupInfo;

import alimama.PropertiesUtil;

public class QQMonitor {

	// key=监听的qq群,value=发送的qq群
	public static Map<String, List<String>> monitorGroupMap = new HashMap<>();

	public static Map<String, String> group_name_code_Map = new HashMap<>();

	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据groupcode获取群名称
	 * 
	 * @param client
	 * @param groupCode
	 * @return
	 */
	public static String getGroupNameByGroupCode(SmartQQClient client, long groupCode) {
		String groupName = group_name_code_Map.get(String.valueOf(groupCode));

		if (StringUtils.isNotBlank(groupName)) {
			return groupName;
		} else {
			GroupInfo groupInfo = client.getGroupInfo(groupCode);
			return groupInfo.getName();
		}
	}

	public static void init() throws Exception {
		Properties propertiesUtil = PropertiesUtil
				.getProperty(QQMonitor.class.getClassLoader().getResourceAsStream("qq.properties"));
		System.out.println(propertiesUtil);

		String gorups = propertiesUtil.getProperty("qq.monitor.group");
		if (StringUtils.isNotBlank(gorups)) {
			for (String group : gorups.split("\\_")) {
				System.out.println(group);
				String key = group.split("\\>")[0];
				String val = group.split("\\>")[1];
				List<String> list = new ArrayList<>();
				for (String v : val.split("\\:")) {
					list.add(v);
				}
				monitorGroupMap.put(key, list);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		init();

		System.out.println(monitorGroupMap);
	}

}
