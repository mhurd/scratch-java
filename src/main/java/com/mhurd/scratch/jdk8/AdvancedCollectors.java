package com.mhurd.scratch.jdk8;

import static com.mhurd.scratch.jdk8.model.Band.BOWIE;
import static com.mhurd.scratch.jdk8.model.Band.EELS;
import static com.mhurd.scratch.jdk8.model.Band.LED_ZEPPELIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mhurd.scratch.jdk8.model.Band;
import org.junit.Test;

public class AdvancedCollectors {

    @Test
    public void toCollection() {
        TreeSet<Integer> result = Stream.of(1, 2, 3, 4).collect(Collectors.toCollection(TreeSet::new));
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void max() {
        Optional<Band> largest = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
                                       .max(Comparator.comparing(band -> band.getMembers().size()));
        assertTrue(largest.isPresent());
        assertEquals(LED_ZEPPELIN, largest.orElse(null));
    }

    @Test
    public void average() {
        double average = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
                               .collect(Collectors.averagingInt(band -> band.getMembers().size()));
        assertEquals(2.3d, average, 0.04d);
    }

    @Test
    public void partition() {
        Map<Boolean, List<Band>> partitioned = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
                                                     .collect(Collectors.partitioningBy(Band::isSolo));
        assertEquals(Collections.singletonList(BOWIE), partitioned.get(true));
        assertEquals(Arrays.asList(LED_ZEPPELIN, EELS), partitioned.get(false));
    }

    @Test
    public void grouping() {
        Map<Integer, List<Band>> partitioned = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
                                                     .collect(Collectors.groupingBy(band -> band.getMembers().size()));
        assertEquals(Collections.singletonList(BOWIE), partitioned.get(1));
        assertEquals(Collections.singletonList(EELS), partitioned.get(2));
        assertEquals(Collections.singletonList(LED_ZEPPELIN), partitioned.get(4));
    }

    @Test
    public void orderedMapGrouping() {
        List<Band> bands = Arrays.asList(LED_ZEPPELIN, EELS, BOWIE);
        Map<String, List<Band>> map = bands.stream().collect(
            Collectors.groupingBy(Band::getName,
                                  LinkedHashMap::new, // Key change!
                                  Collectors.toList()));
        AtomicInteger i = new AtomicInteger(0);
        map.forEach((k, v) -> {
            assertEquals(bands.get(i.get()), v.get(0));
            i.set(i.get() + 1);
        });
    }

    @Test
    public void strings() {
        String bandNames = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
              .map(Band::getName)
              .collect(Collectors.joining(", ", "[", "]"));
        assertEquals("[Led Zeppelin, Eels, David Bowie]", bandNames);
    }


    @Test
    public void composingCollectors() {
        final Map<Integer, Long> bandSizes = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
                                                   .collect(Collectors.groupingBy(band -> band.getAlbums().size(),
                                                                                Collectors.counting()));
        assertEquals(Long.valueOf(1), bandSizes.get(1)); // only 1 band has 1 album
        assertEquals(Long.valueOf(2), bandSizes.get(3)); // 2 bands have 3 albums
        assertTrue(bandSizes.size() == 2);
    }

    @Test
    public void composingMappingCollectors() {
        final Map<Integer, List<String>> bandSizes =
            Stream.of(LED_ZEPPELIN, EELS, BOWIE)
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
        String result = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
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
