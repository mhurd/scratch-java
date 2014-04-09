package mhurd.scratch;

public class StrangeCode {

    public static void main(String[] args) {
        final boolean flag[] = new boolean[] { false, false, true };
        boolean elec = true;
        int counterPartyRole = 3;
        System.out.println("Elec: " + elec);
        System.out.println("Counter-party role: " + counterPartyRole);
        System.out.println("Mangled counter-party role: " + String.valueOf(counterPartyRole & 1));
        flag[counterPartyRole & 1] |= elec;
        flag[2] &= elec;
        for (boolean b : flag) {
            System.out.println(b);
        }
        counterPartyRole = 4;
        elec = false;
        System.out.println("Elec: " + elec);
        System.out.println("Counter-party role: " + counterPartyRole);
        System.out.println("Mangled counter-party role: " + String.valueOf(counterPartyRole & 1));
        flag[counterPartyRole & 1] |= elec;
        flag[2] &= elec;
        for (boolean b : flag) {
            System.out.println(b);
        }
    }

}
