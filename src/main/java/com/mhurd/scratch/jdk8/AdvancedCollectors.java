package com.mhurd.scratch.jdk8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
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

    private static class Album {

        private final String name;

        private Album(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        static List<Album> albums(String... names) {
            return Arrays.stream(names).map(Album::new).collect(Collectors.toList());
        }

    }

    private static class Band {

        private final String name;
        private final List<Member> members;
        private final List<Album> albums;

        String getName() {
            return name;
        }

        List<Member> getMembers() {
            return members;
        }

        List<Album> getAlbums() {
            return albums;
        }

        boolean isSolo() {
            return getMembers().size() == 1;
        }

        private Band(String name, List<Member> members, List<Album> albums) {
            this.name = name;
            this.members = Collections.unmodifiableList(members);
            this.albums = Collections.unmodifiableList(albums);
        }
    }

    private final Band ledZeppelin =
        new Band("Led Zeppelin",
                 Member.members("John Bonham", "Jimmy Page", "Robert Plant", "John Paul Jones"),
                 Album.albums("Houses of the Holy",
                              "Physical Graffiti",
                              "In Through the Out Door"));

    private final Band eels =
        new Band("Eels",
                 Member.members("Mark Everett Smith" , "The Chet"),
                 Album.albums("Blinking Lights and Other Revelations",
                              "Wonderful, Glorious",
                              "The Cautionary Tales of Mark Oliver Everett"));

    private final Band bowie =
        new Band("David Bowie",
                 Member.members("David Bowie"),
                 Album.albums("The Rise and Fall of Ziggy Stardust and the Spiders from Mars"));

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

    @Test
    public void grouping() {
        Map<Integer, List<Band>> partitioned = Stream.of(ledZeppelin, eels, bowie)
                                                     .collect(Collectors.groupingBy(band -> band.getMembers().size()));
        assertEquals(Collections.singletonList(bowie), partitioned.get(1));
        assertEquals(Collections.singletonList(eels), partitioned.get(2));
        assertEquals(Collections.singletonList(ledZeppelin), partitioned.get(4));
    }

    @Test
    public void strings() {
        String bandNames = Stream.of(ledZeppelin, eels, bowie)
              .map(Band::getName)
              .collect(Collectors.joining(", ", "[", "]"));
        assertEquals("[Led Zeppelin, Eels, David Bowie]", bandNames);
    }


    @Test
    public void composingCollectors() {
        final Map<Integer, Long> bandSizes = Stream.of(ledZeppelin, eels, bowie)
                                                   .collect(Collectors.groupingBy(band -> band.getAlbums().size(),
                                                                                Collectors.counting()));
        assertEquals(Long.valueOf(1), bandSizes.get(1)); // only 1 band has 1 album
        assertEquals(Long.valueOf(2), bandSizes.get(3)); // 2 bands have 3 albums
        assertTrue(bandSizes.size() == 2);
    }

    @Test
    public void composingMappingCollectors() {
        final Map<Integer, List<String>> bandSizes =
            Stream.of(ledZeppelin, eels, bowie)
                  .collect(Collectors.groupingBy(band -> band.getAlbums().size(),
                                                 Collectors.mapping(Band::getName, Collectors.toList())));
        assertEquals(Collections.singletonList("David Bowie"), bandSizes.get(1)); // only 1 band has 1 album
        assertEquals(Arrays.asList("Led Zeppelin", "Eels"), bandSizes.get(3)); // 2 bands have 3 albums
        assertTrue(bandSizes.size() == 2);
    }

    private static class StringCombiner {

        final StringBuilder builder = new StringBuilder();
        private final String separator;
        private final String prefix;
        private final String suffix;

        private StringCombiner(String separator, String prefix, String suffix) {
            this.separator = separator;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        StringCombiner add(String element) {
            if (builder.length() == 0) {
                builder.append(prefix);
            } else {
                builder.append(separator);
            }
            builder.append(element);
            return this;
        }

        StringCombiner merge(StringCombiner other) {
            builder.append(other.builder);
            return this;
        }

        public String toString() {
            return builder.toString() + suffix;
        }

    }

    /**
     * Turns the StringCombiner into a Collector that can be used with the Stream::collect method rather than
     * doing a reduce.
     */
    private static class StringCollector implements Collector<String, StringCombiner, String> {

        private final String separator;
        private final String prefix;
        private final String suffix;

        private StringCollector(String separator, String prefix, String suffix) {
            this.separator = separator;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        @Override
        public Supplier<StringCombiner> supplier() {
            return () -> new StringCombiner(separator, prefix, suffix);
        }

        @Override
        public BiConsumer<StringCombiner, String> accumulator() {
            return StringCombiner::add;
        }

        @Override
        public BinaryOperator<StringCombiner> combiner() {
            return StringCombiner::merge;
        }

        @Override
        public Function<StringCombiner, String> finisher() {
            return StringCombiner::toString;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return new HashSet<>();
        }
    }

    @Test
    public void customCollectors() {
        String result = Stream.of(ledZeppelin, eels, bowie)
                              .map(Band::getName)
                              .collect(new StringCollector(", ", "[", "]"));
        assertEquals("[Led Zeppelin, Eels, David Bowie]", result);
    }

    @Test
    public void newMapIterators() {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("foo", "bar");
        aMap.put("a", "b");
        aMap.put("1", "2");
        // this is far nicer that using KeySets...
        aMap.forEach((key, value) -> System.out.println(key + "=" + value));
    }

}
