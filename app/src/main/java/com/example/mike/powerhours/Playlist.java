package com.example.mike.powerhours;

public class Playlist {
    private int playlist_id;
    private String name;
    private String last_played;

    public Playlist(int p, String n, String l) {
        this.playlist_id = p;
        this.name = n;
        this.last_played = l;
    }

    public Playlist(String n, String l) {
        this(-1, n, l);
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_played() {
        return last_played;
    }

    public void setLast_played(String last_played) {
        this.last_played = last_played;
    }

    public String toString() {
        return this.name;
    }
}
