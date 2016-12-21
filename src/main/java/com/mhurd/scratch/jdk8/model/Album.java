package com.mhurd.scratch.jdk8.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Album {

    private final String name;

    private Album(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Album> albums(String... names) {
        return Arrays.stream(names).map(Album::new).collect(Collectors.toList());
    }

}
