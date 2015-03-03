package thread.synchronize;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
            synchronized (Consumer.class) {
                if (foods.size() > 0) {
                    try {
                        Food food = foods.get(0);
                        System.out.println(Thread.currentThread().getName()
                                + ": consumer " + food.getId() + " consuming...");
                        Random ran = new Random();
                        int i = ran.nextInt(300);

                        TimeUnit.MILLISECONDS.sleep(i);
                        foods.remove(0);
                        Consumer.class.notify();
                        //Consumer.class.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ":consume finished");
                    try {
                        Consumer.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @Override
    public void run() {
        sale();
    }

}
