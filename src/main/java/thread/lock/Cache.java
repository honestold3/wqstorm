package thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
	private static final Map<String, Object> CACHE = new HashMap<String, Object>();
	private static final ReadWriteLock RWL = new ReentrantReadWriteLock();
    private static volatile boolean cacheValid=false;
    int i;

	public Object getValue(String key) {
        Object value = null;
		try {
			RWL.readLock().lock();

			value = CACHE.get(key);

			if (!cacheValid) {
				try {
					RWL.readLock().unlock();
					RWL.writeLock().lock();
					if (!cacheValid) {
                        if(value==null)
                            cacheValid=true;
						value = "xxx";
						CACHE.put(key, value);
					}
                    RWL.readLock().lock();
				} finally {
					RWL.writeLock().unlock();
				}
			}
		} finally {
			RWL.readLock().unlock();
		}
		return value;
	}
}
