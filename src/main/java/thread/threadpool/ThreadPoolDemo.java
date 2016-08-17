package thread.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] arr = new int[2];
        int[] a = {1,2,3};

        int processors = Runtime.getRuntime().availableProcessors();// get core num
		// System.out.println("pro : " + processors);

		ExecutorService fixedThreadPool = Executors
				.newFixedThreadPool(processors * 100);// 固定线程个数的线程池
		// System.out.println(fixedThreadPool);
		for (int i = 0; i < 10; i++) {
			fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());// pool-1-thread-1
				}
			});
		}

		fixedThreadPool.shutdown();// 将线程池关闭

		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();// 缓存线程池，无上限,系统自适

		for (int i = 0; i < 100; i++) {
			cachedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());
				}
			});
		}
		cachedThreadPool.shutdown();

		ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();// 单线程池,永远会存在一条线程

		for (int i = 0; i < 10; i++) {
			final int j = i;
			singleThreadPool.execute(new Runnable() {

				@Override
				public void run() {


//					 if(j == 3) throw new RuntimeException("error...");

					System.out.println(Thread.currentThread().getName() + ":" + j);
				}
			});
		}
		singleThreadPool.shutdown();

		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);//固定个数的线程池，可以执行延时任务，也可以执行带有返回值的任务。

		FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("hello");
				return Thread.currentThread().getName();
			}
		});

		scheduledThreadPool.submit(ft);
		
		String result = ft.get();
		
		System.out.println("result : "+result);
		
		
		scheduledThreadPool.schedule(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+" : bomb!");
			}
		}, 3, TimeUnit.SECONDS);
		
		
	}
}
