package mhurd.scratch;

/**
 * Created with IntelliJ IDEA.
 * User: mhurd
 * Date: 07/09/12
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
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
