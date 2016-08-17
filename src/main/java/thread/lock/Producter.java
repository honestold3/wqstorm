package thread.lock;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producter implements Runnable {

	private List<Food> foods;
	private static int num = 1;
	private static final int MAXSIZE = 1;

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public Producter(List<Food> foods) {
		this.foods = foods;
	}

	public Producter() {
	}

	public void produce() {
		while (true) {
			Lock lock = MyLock.LOCK;
			Condition product_con =  MyLock.PRODUCT_CON;
			Condition consume_con =  MyLock.CONSUME_CON;
			lock.lock();
			if (foods.size() < MAXSIZE) {
				Food food = new Food((num++) + "");
				foods.add(food);
				System.out.println(Thread.currentThread().getName() + " producter "+ food.getId() + " producting...");
				Random ran = new Random();
				int i = ran.nextInt(300);
				try {
					TimeUnit.MILLISECONDS.sleep(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//Consumer.class.notify();
                //consume_con.signalAll();
				consume_con.signal();
			} else {
				System.out.println(Thread.currentThread().getName()+ " :producter finished!");

				try {
					product_con.await();
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
		produce();
	}
}
