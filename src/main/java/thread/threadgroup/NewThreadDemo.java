package thread.threadgroup;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;



public class NewThreadDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Thread t1 = new Thread(){
			
			@Override
			public void run() {
				System.out.println("new Thread 1");
			}
		};
		
		t1.start();
	//	System.out.println(t1.getName());
		
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("new Thread 2");
			}
		});
		
		t2.start();
		
		//System.out.println(Thread.currentThread().getName());

		FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				System.out.println("new Thread 3");
				return "aaaa";
			}
		});
		
		Thread t3 = new Thread(ft);
		t3.start();
		String result = ft.get();
		System.out.println(result);
		
	}
}
