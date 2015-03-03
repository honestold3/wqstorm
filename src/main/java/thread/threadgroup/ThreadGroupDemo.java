package thread.threadgroup;

public class ThreadGroupDemo {

	public static void main(String[] args) {
		Thread main = Thread.currentThread();
		ThreadGroup mainGroup = main.getThreadGroup();
		
		//System.out.println(threadGroup);
		//mainGroup.list();
		
		Thread t1 = new Thread(mainGroup,"myThread");
		t1.start();
		Thread[] arr = new Thread[mainGroup.activeCount()];
		main.enumerate(arr);//将线程组中活动的线程复制到指定数组中。
		
		for (Thread thread : arr) {
			System.out.println(thread.getName());
		}
		
		ThreadGroup parent = mainGroup.getParent();
		parent.list();
		
		mainGroup.list();//打印线程组的所有信息
		
		
		
	}
}
