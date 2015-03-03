package thread.synchronize;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    public void product() {
        while (true) {
            synchronized (Consumer.class) {
                if (foods.size() < MAXSIZE) {
                    Food food = new Food((num++) + "");
                    foods.add(food);
                    System.out.println(Thread.currentThread().getName()
                            + ": producter" + food.getId() + "producting");
                    Random ran = new Random();
                    int i = ran.nextInt(300);
                    try {
                        TimeUnit.MILLISECONDS.sleep(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Consumer.class.notify();
                    //Consumer.class.notifyAll();
                } else {
                    System.out.println(Thread.currentThread().getName()
                            + " :product finished");

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
        product();
    }
}
