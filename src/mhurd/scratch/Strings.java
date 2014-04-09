package mhurd.scratch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

    public static final String P4_GMO_BIN_MATCH_REGEX = "UKDevelopment";
    public static final String P4_GMO_BIN_REPLACE = "$GMO_BIN_DIR/";
    public static final String P4_GMO_LIB_MATCH_REGEX = "UKDevelopment";
    public static final String P4_GMO_LIB_REPLACE = "$GMO_LIB_DIR/";

    private static String prettyPrint(Collection<?> collection) {
        return prettyPrint(collection.toArray(new Object[] {}));
    }

    private static String prettyPrint(Object[] collection) {
        StringBuilder b = new StringBuilder();
        b.append("(");
        int count = 0;
        for (Object s : collection) {
            count++;
            if (s instanceof Object[]) {
                b.append(prettyPrint((Object[])s));
            } else {
                b.append(s);
            }
            if (count < collection.length) {
                b.append(", ");
            }
        }
        b.append(")");
        return b.toString();
    }

    private static String stripZeros(String aString) {
        int decimalPointIndex = aString.indexOf(".");
        if (decimalPointIndex != -1) {
            if (aString.substring(decimalPointIndex+1, aString.length()).matches("0*")) {
                // just 0's after the decimal point - get rid of it
                return aString.substring(0, decimalPointIndex);
            }
        }
        return aString;
    }

    private static class IdUserElectronicComparator implements Comparator<String> {

        private final Pattern idUserElectronicVoiceUserRegEx = Pattern.compile("(v[0]*)([1-9][0-9]*)([a-zA-Z]*)");

        @Override
        public int compare(String s1, String s2) {
            String s1Le, s2Le, s1Postfix, s2Postfix;
            Matcher s1Matcher = idUserElectronicVoiceUserRegEx.matcher(s1.toLowerCase());
            if (s1Matcher.find()) {
                s1Le = s1Matcher.group(2);
                s1Postfix = s1Matcher.group(3);
            } else {
                throw new IllegalArgumentException(s1 + " is not well formed!");
            }
            Matcher s2Matcher = idUserElectronicVoiceUserRegEx.matcher(s2.toLowerCase());
            if (s2Matcher.find()) {
                s2Le = s2Matcher.group(2);
                s2Postfix = s2Matcher.group(3);
            } else {
                throw new IllegalArgumentException(s2 + " is not well formed!");
            }
            if (!s1Le.equals(s2Le)) {
                throw new IllegalArgumentException(s1Le + " and " + s2Le + " LEs don't match!");
            }
            if (s1Postfix.length() == s2Postfix.length()) {
                return s1Postfix.compareTo(s2Postfix);
            } else if (s1Postfix.length() < s2Postfix.length()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    private static String increment(String str) {
        if (str == null || str.isEmpty()) {
            return "a";
        }
        char[] cArray = str.toCharArray();
        char finalChar = cArray[str.length()-1];
        if (finalChar == 'z') {
            return str + "a";
        } else {
            finalChar++;
            return str.substring(0, str.length()-1) + String.valueOf(finalChar);
        }
    }

    public static void main(String[] args) {
        String name = "ATFConverter-12.9";
        System.out.println(name.substring(0, name.lastIndexOf("-")));
        System.out.println("//UKDevelopment/GMO/branches/12.9/lib/XmlObjects.jar".replaceAll("//UKDevelopment/GMO.*/lib/", "\\$GMO_LIB_DIR/"));

        String foo = "a\nstring\n";
        String bar = foo.trim();
        System.out.println("'" + foo + "'");
        System.out.println("'" + bar + "'");
        System.out.println(foo.equals(bar));

        String stringToSplit = "";
        String[] splitString = stringToSplit.split("[|]");
        System.out.println("Splitting string: " + stringToSplit);
        for (String entry : splitString) {
            System.out.println("Split string has entry: '" + entry + "'");
        }

        List<String> c = new ArrayList<String>();
        c.add("foo");
        c.add("bar");
        c.add("mike");
        c.add("blah");
        System.out.println(prettyPrint(c));

        Object[] oa = {23, 5d, "foo", new Object[] {"one", "two", 1, new String[] {"lala"}}};
        System.out.println(prettyPrint(oa));

        System.out.println("Strip 1234 > " + stripZeros("1234"));
        System.out.println("Strip 1234.0 > " + stripZeros("1234.0"));
        System.out.println("Strip 1234.00 > " + stripZeros("1234.00"));
        System.out.println("Strip 1234.1 > " + stripZeros("1234.1"));
        System.out.println("Strip 1234.01 > " + stripZeros("1234.01"));
        System.out.println("Strip 1234.00005 > " + stripZeros("1234.00005"));

        System.out.println();
        List<String> idUserElectronics = new ArrayList<String>();
        idUserElectronics.add("v0117b");
        idUserElectronics.add("v00117a");
        idUserElectronics.add("v0117zzb");
        idUserElectronics.add("v000117z");
        idUserElectronics.add("v0117q");
        idUserElectronics.add("v0117za");
        idUserElectronics.add("v0117a");
        idUserElectronics.add("v0117A");
        Collections.sort(idUserElectronics, new IdUserElectronicComparator());
        for (String user : idUserElectronics) {
            System.out.println("> " + user);
        }


        System.out.println();
        System.out.println("a -> " + increment("a"));
        System.out.println("b -> " + increment("b"));
        System.out.println("z -> " + increment("z"));
        System.out.println("za -> " + increment("za"));
        System.out.println("zb -> " + increment("zb"));
        System.out.println("zz -> " + increment("zz"));
        System.out.println("zzzb -> " + increment("zzzb"));

        System.out.println(String.format("leg%s_direction", 1));

        String test = "12345678901";
        System.out.println("SUBSTRING: " + test.substring(0, 10));
    }

}
