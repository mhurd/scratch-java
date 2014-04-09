package mhurd.scratch;

public class Bitwise {

    public static void main(String[] args) {
        int one = 1; // 001
        int two = 2; // 010
        int three = 3; // 011
        int four = 4; // 100
        System.out.println("1 | 1 = " + (one | 1) + ", expected: 1");
        System.out.println("2 | 1 = " + (two | 1) + ", expected: 3");
        System.out.println("3 | 1 = " + (three | 1) + ", expected: 3");
        System.out.println("4 | 1 = " + (four | 1) + ", expected: 5");
        System.out.println("1 & 1 = " + (one & 1) + ", expected: 1");
        System.out.println("2 & 1 = " + (two & 1) + ", expected: 0");
        System.out.println("3 & 1 = " + (three & 1) + ", expected: 1");
        System.out.println("4 & 1 = " + (four & 1) + ", expected: 0");
    }

}
