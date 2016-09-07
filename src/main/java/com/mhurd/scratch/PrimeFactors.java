package com.mhurd.scratch;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {

    private final int[] primes;

    private PrimeFactors() {
        primes = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
                            37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
                            79, 83, 89, 97, 101, 103, 107, 109, 113, 127,
                            131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
                            181, 191, 193, 197, 199, 211, 223, 227, 229,
                            233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
                            283, 293, 307, 311, 313, 317, 331, 337, 347, 349,
                            353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
                            419, 421, 431, 433, 439, 443, 449, 457, 461, 463};
    }

    private List<Integer> getPrimeFactorsRecur(
            final int originalNumber,
            final int currentNumber,
            final int currentPrimeIndex,
            final List<Integer> accumulator,
            final int product) {
        if (currentPrimeIndex == primes.length) {
            throw new IllegalArgumentException("Not enough primes to factorise: " + originalNumber);
        }
        final int currentPrime = primes[currentPrimeIndex];
        if (product == originalNumber) {
            return accumulator;
        } else {
            if (currentNumber % currentPrime == 0) {
                accumulator.add(currentPrime);
                return getPrimeFactorsRecur(originalNumber, currentNumber / currentPrime, currentPrimeIndex, accumulator, product * currentPrime);
            } else {
                return getPrimeFactorsRecur(originalNumber, currentNumber, currentPrimeIndex+1, accumulator, product);
            }
        }
    }

    private List<Integer> getPrimeFactorsOf(final int number) {
        return getPrimeFactorsRecur(number, number, 0, new ArrayList<>(), 1);
    }

    public static void main(String[] args) {
//        try {
//            PrimeFactors primeFactors = new PrimeFactors();
//            System.out.println("Prime Factors of 1224: " + primeFactors.getPrimeFactorsOf(1224));
//            System.out.println("Prime Factors of 300: " + primeFactors.getPrimeFactorsOf(300));
//            System.out.println("Prime Factors of 1309: " + primeFactors.getPrimeFactorsOf(1309));
//            System.out.println("Prime Factors of 482: " + primeFactors.getPrimeFactorsOf(482));
//            System.out.println("Prime Factors of 540: " + primeFactors.getPrimeFactorsOf(540));
//            System.out.println("Prime Factors of 14340: " + primeFactors.getPrimeFactorsOf(14340));
//            System.out.println("Prime Factors of 7: " + primeFactors.getPrimeFactorsOf(7));
//            System.out.println("Prime Factors of 544326473: " + primeFactors.getPrimeFactorsOf(544326473));
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
        try {
            PrimeFactors primeFactors = new PrimeFactors();
            for (int i = 1; i < 1000000; i++) {
                System.out.println(i + " = " + primeFactors.getPrimeFactorsOf(i));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
