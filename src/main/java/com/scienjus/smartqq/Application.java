package com.scienjus.smartqq;

import java.util.List;

import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.Category;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.Friend;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;

/**
 * @author ScienJus
 * @date 2015/12/18.
 */
public class Application {

	
	/**
	 * 
{"result":[{"poll_type":"message","value":{"content":[["font",{"color":"000000","name":"微软雅黑","size":10,"style":[0,0,0]}],"222"],

"from_uin":2277803026,"msg_id":26119,"msg_type":0,"time":1475682476,"to_uin":2902528429}}],"retcode":0}

	 * @param args
	 * @throws Exception
	 */
    public static void main(String[] args)throws Exception {
        //创建一个新对象时需要扫描二维码登录，并且传一个处理接收到消息的回调，如果你不需要接收消息，可以传null
        SmartQQClient client = new SmartQQClient(new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                //System.out.println(message.getContent());
            	System.out.println(message);
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                System.out.println(message.getContent());
            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                System.out.println(message.getContent());
            }
        });
        //登录成功后便可以编写你自己的业务逻辑了
        List<Category> categories = client.getFriendListWithCategory();
        for (Category category : categories) {
            System.out.println(category.getName());
            for (Friend friend : category.getFriends()) {
                System.out.println("————" + friend.getNickname());
            }
        }
        
        
        for(;;){
        	//client.sendMessageToFriend(635674608, "你好吗");
        	//2277803026
        	client.sendMessageToFriend(2277803026l, "你好吗222");
            Thread.sleep(5000);	
        }
        
        
        //使用后调用close方法关闭，你也可以使用try-with-resource创建该对象并自动关闭
        /*try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
