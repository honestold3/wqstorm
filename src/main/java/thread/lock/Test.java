package thread.lock;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		/*List<Food> foods = new ArrayList<Food>();
		for (int i = 0; i < 10; i++) {
			foods.add(new Food((i+1)+""));
		}
		Restaurant r = new Restaurant(foods);
		for (int i = 0; i < 3; i++) {
			new Thread(r).start();
		}*/
		List<Food> foods = new ArrayList<Food>();
		for (int i = 0; i < 4; i++) {
			new Thread(new Producter(foods),"Producter"+(i+1)).start();
		}
		for (int i = 0; i < 3; i++) {
			new Thread(new Consumer(foods),"Customer"+(i+1)).start();
		}
	}
}
