package thread.utils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

	public static void main(String[] args) {
		final CyclicBarrier cb = new CyclicBarrier(4);
		
		for (int i = 0; i < 4; i++) {
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					try {
						System.out.println(Thread.currentThread().getName()+" :task1 start");
						TimeUnit.SECONDS.sleep(new Random().nextInt(5)+1);
						System.out.println(Thread.currentThread().getName()+" :task1 finished!");
						cb.await();//等待，直到计数器变为0为止
						
						System.out.println(Thread.currentThread().getName()+" :task2 start!");
						TimeUnit.SECONDS.sleep(new Random().nextInt(5)+1);
						System.out.println(Thread.currentThread().getName()+" :task2 finished!");
						cb.await();//等待，直到计数器变为0为止
						
						System.out.println(Thread.currentThread().getName()+" :task3 start!");
						TimeUnit.SECONDS.sleep(new Random().nextInt(5)+1);
						System.out.println(Thread.currentThread().getName()+" :task3 finished!");
						cb.await();//等待，直到计数器变为0为止
						
						System.out.println(Thread.currentThread().getName()+" go home!");
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
		}
		
	}
}
