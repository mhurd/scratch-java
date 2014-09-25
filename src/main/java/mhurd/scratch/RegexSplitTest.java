package mhurd.scratch;

public class RegexSplitTest {

    private static void split(String tenor) {
        for (String s : tenor.split("\\d{1,2}")) {
            if (s.isEmpty()) {
                System.out.print(" ");
            }
            System.out.print(s);
        }
        System.out.println();
    }

    private static void isValid(String tenor) {
        String regex = "^[1-9]*D$|^[1-9]*M$|^[1-9]*Y$";
        System.out.println(tenor + ": " + tenor.matches(regex));
    }

    private static String getTenorMagnitude(String tenor) {
        return tenor.substring(0, tenor.length()-1);
    }

    private static String getTenorUnit(String tenor) {
        return tenor.substring(tenor.length()-1, tenor.length());
    }

    public static void main(String[] args) {
        isValid("1M");
        isValid("1 M");
        isValid("24M");
        isValid("0Y");
        isValid("1Y");
        isValid("1YY");
        isValid("365D");
        System.out.println(getTenorMagnitude("1M"));
        System.out.println(getTenorMagnitude("365D"));
        System.out.println(getTenorMagnitude("24M"));

        System.out.println(getTenorUnit("1M"));
        System.out.println(getTenorUnit("365D"));
        System.out.println(getTenorUnit("24M"));

        String[] array = "".split("|");
        System.out.println("split rule list length = " + array.length);
        for (String s: array) {
            System.out.println("item: '" + s + "'");
        }
        System.out.println("BGCPARTNERM049-YYYYMMDD-UNIQUEREFXXXXXXXXX".length());

        String VALID_TRANSACTION_IDENTIFIER = "[a-zA-Z0-9]+([_:.-][a-zA-Z0-9]+)*";
        System.out.println("T171X20121017X00003FB7X0000X00XX".matches(VALID_TRANSACTION_IDENTIFIER));
        System.out.println("T171-20121017-00003FB7X0000X00XX".matches(VALID_TRANSACTION_IDENTIFIER));
        System.out.println("T171-20121017-00003FB7X0000X00X-".matches(VALID_TRANSACTION_IDENTIFIER));
    }

}
