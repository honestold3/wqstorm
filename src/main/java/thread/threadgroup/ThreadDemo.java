package thread.threadgroup;

public class ThreadDemo {

	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true)
					System.out.println("Thread1");
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true)
					System.out.println("Thread2");
			}
		}).start();
		
		while(true){
			System.out.println("main");
		}
		
	}
}
