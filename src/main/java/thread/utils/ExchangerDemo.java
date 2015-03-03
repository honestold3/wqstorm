package thread.utils;

import org.apache.commons.lang.math.RandomUtils;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerDemo {

	public static void main(String[] args) {
		final Exchanger<String> exchanger = new Exchanger<String>();
		//for (int i = 0; i < 3; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						System.out.println(Thread.currentThread().getName()+ ": app store...");
						TimeUnit.SECONDS.sleep(new Random().nextInt(5));
						String data1 = "iphone6 plus";
						String data2 = exchanger.exchange(data1);
						System.out.println(Thread.currentThread().getName()+ " : receive " + data2);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						System.out.println("-->" + Thread.currentThread().getName() + ": customer");
						TimeUnit.SECONDS.sleep(new Random().nextInt(2));
						String data1 = "5888";
						String data2 = exchanger.exchange(data1);
						System.out.println(Thread.currentThread().getName()+ " : show " + data2);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
	//	}
	}
}
