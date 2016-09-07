package com.mhurd.scratch;

public class LeadingZeros {

    public static void main(String[] args) {
        String withLeadingZeros = "CF0000073434";
        System.out.println(Integer.parseInt(withLeadingZeros.replaceAll("[A-Za-z-_]", "")));
    }

}
