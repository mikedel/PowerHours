package com.example.mike.powerhours;

public class Track {
    private int track_id;
    private String name;
    private String artist;
    private int length;
    private String location;

    public Track(int t, String n, String a, int l, String loc) {
        this.track_id = t;
        this.name = n;
        this.artist = a;
        this.length = l;
        this.location = loc;
    }

    public Track(String n, String a, int l, String loc) {
        this(-1, n, a, l, loc);
    }

    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        return this.name + " - " + this.artist;
    }
}
