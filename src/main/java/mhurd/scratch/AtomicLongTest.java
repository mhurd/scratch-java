package mhurd.scratch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {

    public static final AtomicLong timestamp = new AtomicLong(System.currentTimeMillis());
    public static final long THREE_SECONDS = 1000*3;
    public static final Map<String, String> cache = new ConcurrentHashMap<String, String>();

    private static Map<String, String> runSQL() {
        Map<String, String> results = new HashMap<String, String>();
        results.put("foo", Thread.currentThread().getName());
        return results;
    }

    private static void threadPrint(String msg) {
        System.out.println(Thread.currentThread().getName() + " " + msg);
    }

    private static Map<String, String> read() {
        long currentTimestamp = System.currentTimeMillis();
        long oldTimestamp = timestamp.get();
        if (currentTimestamp - oldTimestamp >= THREE_SECONDS) {
            threadPrint("timer has triggered at: " + System.nanoTime());
            if (timestamp.compareAndSet(oldTimestamp, currentTimestamp)) {
                // no-one has snuck in and updated
                // update the cache with the SQL
                cache.putAll(runSQL());
                threadPrint("Cache updated!");
            }
        }
        threadPrint("Cache: " + cache);
        return cache;
    }

    private static void spawnReader() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                read();
            }
        }).start();
    }

    public static void main(String[] args) {
        int count = 0;
        while (count < 20) {
            count++;
            spawnReader();
            spawnReader();
            spawnReader();
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
