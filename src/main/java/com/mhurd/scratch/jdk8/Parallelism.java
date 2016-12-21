package com.mhurd.scratch.jdk8;

import static com.mhurd.scratch.jdk8.model.Band.BOWIE;
import static com.mhurd.scratch.jdk8.model.Band.EELS;
import static com.mhurd.scratch.jdk8.model.Band.LED_ZEPPELIN;
import static org.junit.Assert.assertEquals;

import java.util.stream.Stream;

import org.junit.Test;

/**
 * NOTE: in most case the benefits of parallelism will not be apparent until the size of the collection
 * gets large, before then the cost of context switching will likely make the serial version quicker (unless the work
 * is very slow).
 */
public class Parallelism {

    @Test
    public void parallelArraySum() {
        long count = Stream.of(LED_ZEPPELIN, EELS, BOWIE)
                     .parallel()
                     .mapToLong(band -> band.getAlbums().size())
                     .sum();
        assertEquals(7, count);
    }

}
