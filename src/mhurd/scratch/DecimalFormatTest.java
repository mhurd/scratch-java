package mhurd.scratch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalFormatTest {

    private  static boolean isRepeating(double d) {
        char[] chars = String.valueOf(d).toCharArray();
        if (chars.length == 0) {
            return false;
        }
        char lastChar = chars[chars.length-1];
        int repeatingCount = -1;
        for (int i = chars.length-1; i >= 0; i--) {
            if (lastChar == chars[i]) {
                repeatingCount++;
            }
            lastChar = chars[i];
        }
        return repeatingCount > 1;
    }

    private static double parseRounding(double d) {
        if (isRepeating(d)) {
            BigDecimal bd = new BigDecimal(String.valueOf(d));
            int scale = bd.scale();
            StringBuilder b = new StringBuilder("0.");
            for (int i = 1; i<scale; i++) {
                b.append("0");
            }
            b.append("1");
            return bd.add(new BigDecimal(b.toString())).doubleValue();
        } else {
            return d;
        }
    }

    public static void main(String[] args) {
        double d1 = 0.299999d;
        System.out.println(d1 + " -> " + parseRounding(d1));
        double d2 = 0.3d;
        System.out.println(d2 + " -> " + parseRounding(d2));
        double d3 = 0.277777d;
        System.out.println(d3 + " -> " + parseRounding(d3));
        double d4 = 0.9d;
        System.out.println(d4 + " -> " + parseRounding(d4));
        double d5 = 36.543534999d;
        System.out.println(d5 + " -> " + parseRounding(d5));
        double d6 = 99.99999999999d;
        System.out.println(d6 + " -> " + parseRounding(d6));
    }

}
