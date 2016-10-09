package alimama;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtil {

	public static void main(String[] args) {

	}

	public static List<String> matcher(String content) {
		Pattern p = Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?");

		// Matcher m = p.matcher(
		// "多功能儿童智能手表手机天猫券后【69元】包邮秒杀领券:http://shop.m.taobao.com/shop/coupon.htm?activity_id=e4f0d0eeda8a45b78ea1ee2adf0f82c9&seller_id=1699430130
		// \r\n 抢购:http://s.click.taobao.com/XqZdaRx");
		Matcher m = p.matcher(content);

		List<String> list = new ArrayList<>();
		while (m.find()) {
			/// System.out.println(m.group());
			list.add(m.group());
		}
		return list;

	}

	public static List<String> getUrl(String content) {
		List<String> list = matcher(content);
		
		List<String> newList = new ArrayList<>();
		for (String str : list) {
			if (!str.contains("shop.m.taobao.com")) {
				newList.add(str);
			}
		}
		return newList;

	}

}