package com.example.mike.powerhours;

public class Setlist {
    private int setlist_id;
    private int playlist_id;
    private int track_id;

    public Setlist(int pt, int p, int t) {
        this.setlist_id = pt;
        this.playlist_id = p;
        this.track_id = t;

    }

    public int getSetlist_id() {
        return setlist_id;
    }

    public void setSetlist_id(int setlist_id) {
        this.setlist_id = setlist_id;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }
}
