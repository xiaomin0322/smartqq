package com.scienjus.smartqq;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.Category;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.Friend;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

import alimama.AlibabUrl;
import alimama.MatcherUtil;
import alimama.Timer;

/**
 * @author ScienJus
 * @date 2015/12/18.
 */
public class Application {

	/**
	 * 
	 * {"result":[{"poll_type":"message","value":{"content":[["font",{"color":
	 * "000000","name":"微软雅黑","size":10,"style":[0,0,0]}],"222"],
	 * 
	 * "from_uin":2277803026,"msg_id":26119,"msg_type":0,"time":1475682476,
	 * "to_uin":2902528429}}],"retcode":0}
	 * 
	 * @param args
	 * @throws Exception
	 */

	static SmartQQClient client;

	public static String getNewContent(String content) {
		String newContent = content;
		try {
			// 原始的url
			List<String> urls = MatcherUtil.getUrl(content);

			System.out.println("提取url:" + urls);

			for (String url : urls) {
				String newURl = AlibabUrl.getClickURL(url);
				if(StringUtils.isBlank(newURl)){
					return null;
				}
				newContent = newContent.replace(url, newURl);
				System.out.println("oldURL :" + url + " newURL : " + newURl);
			}
			
			System.out.println("newContent : "+newContent);
			return newContent;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

	public static void main(String[] args) throws Exception {

		AlibabUrl.login2();
		
		Timer.timer();

		// 创建一个新对象时需要扫描二维码登录，并且传一个处理接收到消息的回调，如果你不需要接收消息，可以传null
		client = new SmartQQClient(new MessageCallback() {
			@Override
			public void onMessage(Message message) {
				// System.out.println(message.getContent());
				System.out.println(message);
			}

			@Override
			public void onGroupMessage(GroupMessage message) {
				System.out.println("message:"+message);

				try {
					List<Long> qqList = QQMonitor.monitorGroupMap.get(String.valueOf(message.getGroupId()));

					System.out.println("qqGroupId:" + message.getGroupId() + "  qqlist >>>>>>>>>>>>" + qqList);

					if (CollectionUtils.isNotEmpty(qqList)) {
						String content = getNewContent(message.getContent());
						if(StringUtils.isNotBlank(content)){
							for (Long qqGroupId : qqList) {
								content += "\r\n___________________________________________________________________________________________________________________";
								client.sendMessageToGroup(qqGroupId, content,3);
								Thread.sleep(1000);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onDiscussMessage(DiscussMessage message) {
				System.out.println(message.getContent());
			}
		});
		// 登录成功后便可以编写你自己的业务逻辑了
		List<Category> categories = client.getFriendListWithCategory();
		for (Category category : categories) {
			System.out.println(category.getName());
			for (Friend friend : category.getFriends()) {
				System.out.println("————" + friend.getNickname());
			}
		}

		// client.sendMessageToGroup(groupId, msg);

		/*
		 * for(;;){ //client.sendMessageToFriend(635674608, "你好吗"); //2277803026
		 * client.sendMessageToFriend(2277803026l, "你好吗222");
		 * Thread.sleep(5000); }
		 */

		// 使用后调用close方法关闭，你也可以使用try-with-resource创建该对象并自动关闭
		/*
		 * try { client.close(); } catch (IOException e) { e.printStackTrace();
		 * }
		 */
	}
}
