package com.mhurd.scratch.jdk8.model;

import java.util.Collections;
import java.util.List;

public class Band {

    private final String name;
    private final List<Member> members;
    private final List<Album> albums;

    public static final Band LED_ZEPPELIN =
        new Band("Led Zeppelin",
                 Member.members("John Bonham", "Jimmy Page", "Robert Plant", "John Paul Jones"),
                 Album.albums("Houses of the Holy",
                              "Physical Graffiti",
                              "In Through the Out Door"));

    public static final Band EELS =
        new Band("Eels",
                 Member.members("Mark Everett Smith" , "The Chet"),
                 Album.albums("Blinking Lights and Other Revelations",
                              "Wonderful, Glorious",
                              "The Cautionary Tales of Mark Oliver Everett"));

    public static final Band BOWIE =
        new Band("David Bowie",
                 Member.members("David Bowie"),
                 Album.albums("The Rise and Fall of Ziggy Stardust and the Spiders from Mars"));

    public String getName() {
        return name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public boolean isSolo() {
        return getMembers().size() == 1;
    }

    public Band(String name, List<Member> members, List<Album> albums) {
        this.name = name;
        this.members = Collections.unmodifiableList(members);
        this.albums = Collections.unmodifiableList(albums);
    }

}
