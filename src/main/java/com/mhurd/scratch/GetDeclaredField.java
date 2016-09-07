package com.mhurd.scratch;

import java.lang.reflect.Field;

public class GetDeclaredField {

    private static class SuperSuperclass {

        protected Integer aField2 = Integer.MIN_VALUE;

    }

    private static class Superclass extends SuperSuperclass {

        protected Integer aField = Integer.MIN_VALUE;

    }

    private static class Subclass extends Superclass {

    }

    private static Field getDeclaredField1(Class clazz, String name) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }

    private static Field getDeclaredField2(Class clazz, String name) throws NoSuchFieldException {
        if (clazz == null) {
            throw new NoSuchFieldException(name);
        }
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getDeclaredField2(clazz.getSuperclass(), name);
        }
    }

    public static void main(String[] args) throws NoSuchFieldException {
        int loops = 1000000;
        Subclass clazz = new Subclass();
        long start1 = System.nanoTime();
        for (int i = 0; i < loops; i++) {
            getDeclaredField1(clazz.getClass(), "aField");
        }
        long end1 = System.nanoTime();
        System.out.println("While Loop = " + (end1 - start1) / loops + " nanoseconds");
        long start2 = System.nanoTime();
        for (int i = 0; i < loops; i++) {
            getDeclaredField2(clazz.getClass(), "aField");
        }
        long end2 = System.nanoTime();
        System.out.println("Recursion = " + (end2 - start2) / loops + " nanoseconds");
    }

}
