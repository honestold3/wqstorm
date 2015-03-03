package thread.utils;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch cdl1 = new CountDownLatch(1);
		final CountDownLatch cdl2 = new CountDownLatch(4);

		// t.join(); // 优先执行当前线程

		for (int i = 0; i < 4; i++) {
			new Thread(new Runnable() {// 运动员

						@Override
						public void run() {
							try {
								cdl1.await(); // 等待计数器变为0

								System.out.println(Thread.currentThread().getName() + ": start");
								TimeUnit.SECONDS.sleep(new Random().nextInt(3));
								System.out.println(Thread.currentThread().getName() + ": finished!");
								cdl2.countDown();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
		}

		Thread t = new Thread(new Runnable() {// 发布命令的裁判

					@Override
					public void run() {
						System.out.println("ready");
						for (int i = 3; i >= 1; i--) {
							System.out.println(i + "...");
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println("go");
						cdl1.countDown();// 计数器减1

					}
				});
		t.start();
		
		new Thread(new Runnable() {// 宣布成绩的裁判

			@Override
			public void run() {
				try {
					cdl2.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("The end!");
			}
		}).start();
		
	}
}
