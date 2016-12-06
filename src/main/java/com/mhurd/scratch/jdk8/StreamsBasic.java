package com.mhurd.scratch.jdk8;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamsBasic {

    @Test
    public void collect() {
        List<String> collected = Stream.of("foo", "bar", "foobar")
                                       .collect(Collectors.toList());
        assertEquals(asList("foo", "bar", "foobar"), collected);
    }

    @Test
    public void map() {
        List<String> collected = Stream.of("foo", "bar", "foobar")
                                       .map(String::toUpperCase)
                                       .collect(Collectors.toList());
        assertEquals(Arrays.asList("FOO", "BAR", "FOOBAR"), collected);
    }

    @Test
    public void filter() {
        List<String> beginningWithNumbers = Stream.of("foo", "bar", "1foobar")
                                                  .filter(value -> Character.isDigit(value.charAt(0)))
                                                  .collect(Collectors.toList());
        assertEquals(Collections.singletonList("1foobar"), beginningWithNumbers);
    }

    @Test
    public void flatMap() {
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                                       .flatMap(Collection::stream) // note flatMap expects the result to be a stream
                                       .collect(Collectors.toList());
        assertEquals(asList(1, 2, 3, 4), together);
    }

    private static class Track {
        private final String name;
        private final int length;

        Track(String name, int length) {
            this.name = name;
            this.length = length;
        }

        String getName() {
            return name;
        }

        int getLength() {
            return length;
        }

    }

    @Test
    public void maxAndMin() {
        List<Track> tracks = asList(new Track("Heartbreaker", 255),
                                    new Track("Stairway to Heaven", 465),
                                    new Track("The Song Remains the Same", 329));
        Track shortestTrack = tracks.stream()
                                    .min(Comparator.comparing(Track::getLength))
                                    .get();
        assertEquals(tracks.get(0), shortestTrack);
    }

    @Test
    public void reduce() {
        int count = Stream.of(1, 2, 3).reduce(0, (acc, val) -> acc+val);
        assertEquals(6, count);
    }

    @Test
    public void mapUsingReduce() {
        Stream<Integer> numberStream = Stream.of(1, 2, 3);
        List<Integer> mapResult = numberStream.map(n -> n * 2).collect(Collectors.toList());
        // although shouldn't really mutate the arguments to the combiner / accumulator
        List<Integer> reduceResult = numberStream.reduce(new ArrayList<Integer>(),
                                                             (acc, num) -> {
                                                                 acc.add(num * 2);
                                                                 return acc;
                                                             },
                                                             (left, right) -> {
                                                                left.addAll(right);
                                                                return left;
                                                             });
        assertEquals(mapResult, reduceResult);
    }

    @Test
    public void filterUsingReduce() {
        Stream<Integer> numberStream = Stream.of(1, 2, 3);
        List<Integer> filterResult = numberStream.filter(n -> n > 2).collect(Collectors.toList());
        // although shouldn't really mutate the arguments to the combiner / accumulator
        List<Integer> reduceResult = numberStream.reduce(new ArrayList<Integer>(),
                                                             (acc, num) -> {
                                                                 if (num > 2) {
                                                                     acc.add(num);
                                                                 }
                                                                 return acc;
                                                             },
                                                             (left, right) -> {
                                                                 left.addAll(right);
                                                                 return left;
                                                             });
        assertEquals(filterResult, reduceResult);
    }

    @Test
    public void summaryStatistics() {
        IntSummaryStatistics stats = Stream.of(2, 6, 3, 5, 8, 45 , 4 , 1).mapToInt(n -> n).summaryStatistics();
        assertEquals(45, stats.getMax());
        assertEquals(1, stats.getMin());
        assertEquals(8, stats.getCount());
        assertEquals(74, stats.getSum());
        assertEquals(9.25d, stats.getAverage(), 0.1d);
    }



}
