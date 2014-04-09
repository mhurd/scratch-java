package mhurd.scratch.multiprocessor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class AbstractLock implements Lock {

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // Not interested in this implementation
    }

    @Override
    public boolean tryLock() {
        return false; // Not interested in this implementation
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false; // Not interested in this implementation
    }

    @Override
    public Condition newCondition() {
        return null; // Not interested in this implementation
    }

}
