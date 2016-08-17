package thread.lock;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

	private List<Food> foods;

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public Consumer(List<Food> foods) {
		this.foods = foods;
	}

	public Consumer() {
	}

	public void sale() {
		while (true) {
			Lock lock = MyLock.LOCK;
			Condition product_con =  MyLock.PRODUCT_CON;
			Condition consume_con =  MyLock.CONSUME_CON;
			lock.lock();
			if (foods.size() > 0) {
				try {
					Food food = foods.get(0);
					System.out.println(Thread.currentThread().getName() + ": consumer " + food.getId() + " consuming...");
					Random ran = new Random();
					int i = ran.nextInt(300);

					TimeUnit.MILLISECONDS.sleep(i);
					foods.remove(0);
					//Consumer.class.notify();
					product_con.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(Thread.currentThread().getName()+ ":consumer finish!");
				try {
					consume_con.await();
                    //Consumer.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			lock.unlock();
		}
	}

	@Override
	public void run() {
		sale();
	}

}
