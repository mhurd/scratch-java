package mhurd.scratch;

public class SwitchFallThrough {

    public static void main(String[] args) {
        int value = 3;
        switch (value) {
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
                break;
            case 3:
                System.out.println(3);
                break;
            case 4:
                System.out.println(4);
                break;
        }
    }

}
