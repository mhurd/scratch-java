package mhurd.scratch.multiprocessor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class LockTester {

    private final CountDownLatch startLatch = new CountDownLatch(1);
    private final CountDownLatch finishLatch;
    private final Lock lock;
    private final int numberOfThreads;
    private int counter = 0;

    private int totalTimeInMillis;

    public LockTester(Lock lockToTest, int numberOfThreads) throws InterruptedException {
        this.lock = lockToTest;
        this.finishLatch = new CountDownLatch(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            Runnable work = new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.lock();
                    try {
                        int foo = 0;
                        for (int i = 0; i < 100000; i++) {
                            foo++;
                        }
                        counter++;
                    } finally {
                        lock.unlock();
                        finishLatch.countDown();
                    }
                }
            };
            Thread thread = new Thread(work);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void runTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        startLatch.countDown();
        finishLatch.await();
        System.out.println(lock.getClass().getSimpleName() + " = "
            + (System.currentTimeMillis() - start)
            + " milliseconds to complete for "
            + numberOfThreads
            + " threads (counter = "
            + counter
            + ")");
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 1000;
        new LockTester(new TASLock(), numberOfThreads).runTest();
        new LockTester(new TTASLock(), numberOfThreads).runTest();
    }

}
