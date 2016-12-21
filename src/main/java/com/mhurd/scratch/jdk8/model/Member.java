package com.mhurd.scratch.jdk8.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Member {

    private final String name;

    private Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Member> members(String... names) {
        return Arrays.stream(names).map(Member::new).collect(Collectors.toList());
    }

}
