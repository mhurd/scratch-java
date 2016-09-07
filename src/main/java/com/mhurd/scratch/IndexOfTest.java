package com.mhurd.scratch;

public class IndexOfTest {

    public static void main(String[] args) {
        String test = "a b c d (one,two,three,four,five) g h i j";
        int openParentheses = test.indexOf("(");
        int closeParentheses = test.indexOf(")");
        String parsed = test.substring(openParentheses+1, closeParentheses);
        System.out.println(parsed);
        String[] elements = parsed.split(",");
        int index = 1;
        for (String element : elements) {
            System.out.print(index);
            System.out.println(" = " + element);
            index++;
        }
    }

}
