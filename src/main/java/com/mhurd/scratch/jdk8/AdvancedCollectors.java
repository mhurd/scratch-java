package com.mhurd.scratch.jdk8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class AdvancedCollectors {

    private static class Member {

        private final String name;

        private Member(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        static List<Member> members(String... names) {
            return Arrays.stream(names).map(Member::new).collect(Collectors.toList());
        }

    }

    private static class Band {

        private final String name;
        private final List<Member> members;

        String getName() {
            return name;
        }

        List<Member> getMembers() {
            return members;
        }

        boolean isSolo() {
            return getMembers().size() == 1;
        }

        private Band(String name, List<Member> members) {
            this.name = name;
            this.members = Collections.unmodifiableList(members);
        }
    }

    private final Band ledZeppelin = new Band("Led Zeppelin", Member.members("John Bonham"
        , "Jimmy Page"
        , "Robert Plant"
        , "John Paul Jones"));

    private final Band eels = new Band("Eels", Member.members("Mark Everett Smith"
        , "The Chet"));

    private final Band bowie = new Band("David Bowie", Member.members("David Bowie"));

    @Test
    public void toCollection() {
        TreeSet<Integer> result = Stream.of(1, 2, 3, 4).collect(Collectors.toCollection(TreeSet::new));
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void max() {
        Optional<Band> largest = Stream.of(ledZeppelin, eels, bowie)
                                       .max(Comparator.comparing(band -> band.getMembers().size()));
        assertTrue(largest.isPresent());
        assertEquals(ledZeppelin, largest.orElse(null));
    }

    @Test
    public void average() {
        double average = Stream.of(ledZeppelin, eels, bowie)
                               .collect(Collectors.averagingInt(band -> band.getMembers().size()));
        assertEquals(2.3d, average, 0.04d);
    }

    @Test
    public void partition() {
        Map<Boolean, List<Band>> partitioned = Stream.of(ledZeppelin, eels, bowie)
                                                     .collect(Collectors.partitioningBy(Band::isSolo));
        assertEquals(Collections.singletonList(bowie), partitioned.get(true));
        assertEquals(Arrays.asList(ledZeppelin, eels), partitioned.get(false));
    }

}
