package com.mhurd.scratch;

public class Locks {

    private static class ReadyLock {
        private volatile boolean ready = false;

        void await() {
            // check (volatile) ready state first prior to expensive synchronisation
            if (!ready) {
                // synchronise on the monitor to use wait
                synchronized (this) {
                    try {
                        // always use in a loop due to spurious wake-ups/
                        // http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html#wait%28%29
                        // http://stackoverflow.com/questions/1050592/do-spurious-wakeups-actually-happen#1051816
                        while (!ready) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        // pass on the interrupt to avoid losing it
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        void lock() {
            System.out.println("LOCK...");
            ready = false;
        }

        // synchronise on the monitor to use notifyAll
        synchronized void unlock() {
            System.out.println("UNLOCK...");
            ready = true;
            notifyAll();
        }


        boolean isReady() {
            return ready;
        }

    }

    private static final ReadyLock READY_LOCK = new ReadyLock();

    private static void snooze(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startWorker() {
        Runnable runnable = () -> {
            while (true) {
                READY_LOCK.await();
                System.out.println(Thread.currentThread().getName() + " is running, ready = " + READY_LOCK.isReady());
                snooze(1000L);
            }
        };
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();
    }

    public static void main(String[] args) {
        READY_LOCK.lock();
        startWorker();
        startWorker();
        startWorker();
        startWorker();
        startWorker();
        READY_LOCK.unlock();
        snooze(3000L);
        READY_LOCK.lock();
        snooze(3000L);
        READY_LOCK.unlock();
        snooze(5000L);
        READY_LOCK.lock();
        snooze(1000L);
        System.out.println("Finished!");
    }

}
