package com.mhurd.scratch;

public class FormatEffDateOffset {

    private static String format(Integer months) {
        StringBuilder b = new StringBuilder();
        if (months != null && months > 0) {
            if (months % 12 == 0) {
                b.append(months/12).append("Y");
            } else {
                b.append(months).append("M");
            }
        }
        return b.toString();
    }

    public static void main(String[] args) {
        int[] months = {1, 2, 4, 12, 18, 20, 24, 36, 120};
        for (int month : months) {
            System.out.println(month + " = " + format(month));
        }
    }

}
