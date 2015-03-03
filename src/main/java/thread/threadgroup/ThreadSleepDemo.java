package thread.threadgroup;

import java.util.concurrent.TimeUnit;

public class ThreadSleepDemo {

	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println("kankan");
				for (int i = 5; i >= 1; i--) {
					System.out.println(i+"...");
					try {
						//Thread.sleep(1000);
						TimeUnit.SECONDS.sleep(1);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
