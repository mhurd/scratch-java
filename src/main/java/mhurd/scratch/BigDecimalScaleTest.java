package mhurd.scratch;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: mhurd
 * Date: 07/09/12
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class BigDecimalScaleTest {

    public static final BigDecimal HUNDRED = new BigDecimal("100");

    private static void scalingTest() {
        BigDecimal frm = new BigDecimal("0.60125");
        System.out.println(frm.multiply(HUNDRED));
        float frm2 = 60.125f;
        System.out.println("float conversion: " + frm2/100f);
        BigDecimal l = new BigDecimal("60.12499999999999511501869164931122213602066040039062500");
        System.out.println("Unrounded: " + l);
        System.out.println("Rounded: " + l.setScale(8, RoundingMode.HALF_UP));
        BigDecimal l2 = new BigDecimal("200000100020000.12499999999999511501869164931122213602066040039062500");
        System.out.println("Unrounded: " + l2);
        System.out.println("Rounded: " + l2.setScale(8, RoundingMode.HALF_UP).round(new MathContext(20)));

        BigDecimal foo = new BigDecimal("100");
        System.out.println("DIVIDE = " + foo.divide(new BigDecimal("10"), 2, BigDecimal.ROUND_HALF_UP));

        if (Long.MAX_VALUE + 1 > Long.MAX_VALUE) {
            System.out.println("Greater than max long!");
        }
    }

    private static void divideTest() {
        System.out.println(BigDecimal.ONE.divide(new BigDecimal("0.2361"), 8, RoundingMode.HALF_UP));
    }

    public static void main(String[] args) {
        scalingTest();
        divideTest();
    }

}
