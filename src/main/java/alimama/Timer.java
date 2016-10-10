package alimama;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
	public static void main(String[] args) {
		
	}
	
	
	public static void timer(){
		Runnable runnable = new Runnable() {
			public void run() {
				// tasktorungoeshere
				System.out.println("页面刷新了>>>>>>>>>>>>>>>>>>>>>!!");
				try {
					AlibabUrl.getClickURL(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, 100, 300, TimeUnit.SECONDS);
		
		System.out.println("定时任务准备开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}
