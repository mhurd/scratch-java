package com.mhurd.scratch;

public class NullArrayElement {

    public static void main(String[] args) {
        String[] foo = new String[] {"a", "b", null, "c"};
        int index = 0;
        for (String s : foo) {
            System.out.println("String at index: " + index + " is " + String.valueOf(s));
            index++;
            index++;
        }
    }

}
