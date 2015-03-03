package thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLock {

	public static final Lock LOCK = new ReentrantLock(true);
	public static final Condition PRODUCT_CON = LOCK.newCondition();
	public static final Condition CONSUME_CON = LOCK.newCondition();
	
	private MyLock(){}
}
