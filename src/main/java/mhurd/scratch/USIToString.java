package mhurd.scratch;

import java.util.ArrayList;
import java.util.List;

public class USIToString {

    private static final int ALL_BUSINESSES = -1;

    private static final List<Integer> usiList = new ArrayList<Integer>();

    static {
        usiList.add(ALL_BUSINESSES);
        usiList.add(125);
        usiList.add(171);
    }

    public String toString() {
        StringBuilder b = new StringBuilder("USI enabled buinesses: ");
        int count = 1;
        for (Integer businessId : usiList) {
            b.append((businessId == ALL_BUSINESSES)? "All" : businessId);
            if (count < usiList.size()) {
                b.append(", ");
            }
            count++;
        }
        return b.toString();
    }

    public static void main(String[] args) {
        System.out.println(new USIToString().toString());
    }

}
