package com.mhurd.scratch;

public class ReThrowException {

    public static void main(String[] args) {
        try {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                System.out.println("OUTER CATCH!");
            }
        } catch (Exception ex) {
            System.out.println("OUTER OUTER  CATCH!");
        }
    }
}
