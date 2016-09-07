package com.mhurd.scratch;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanTest {

    private static AtomicBoolean ready = new AtomicBoolean(false);

    private static boolean isReady() {
        System.out.println("ready = " + ready.get());
        return ready.get();
    }

    private static void setReady(boolean flag) {
        boolean changed = ready.compareAndSet(!flag, flag);
        if (changed) {
            System.out.println("Ready to send execution reports = " + flag);
        }
    }

    public static void main(String[] args) {
        AtomicBooleanTest.isReady();
        AtomicBooleanTest.setReady(false);
        AtomicBooleanTest.setReady(true);
        AtomicBooleanTest.setReady(true);
        AtomicBooleanTest.setReady(true);
        AtomicBooleanTest.setReady(true);
        AtomicBooleanTest.setReady(false);
        AtomicBooleanTest.setReady(false);
        AtomicBooleanTest.setReady(false);
        AtomicBooleanTest.setReady(false);
    }

}
