package thread.utils;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(5);
		
		for (int i = 0; i < 50; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						semaphore.acquire();//获得一个许可
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()
							+ ": kankan"+(5-semaphore.availablePermits())+" ");

					try {
						TimeUnit.SECONDS.sleep(new Random().nextInt(3));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("-->" + Thread.currentThread().getName()
							+ ": bye");
					
					semaphore.release();//释放许可
				}
			}).start();

		}
	}
}
