package com.example.mike.powerhours;

public class PowerHour {
    private int power_hour_id;
    private int playlist_id;
    private String last_played;
    private int length;
    private int song_length;
    private String sound;

    public PowerHour(int ph, int p, String l, int len, int sl, String s) {
        this.power_hour_id = ph;
        this.playlist_id = p;
        this.last_played = l;
        this.length = len;
        this.song_length = sl;
        this.sound = s;
    }

    public int getPower_hour_id() {
        return power_hour_id;
    }

    public void setPower_hour_id(int power_hour_id) {
        this.power_hour_id = power_hour_id;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getLast_played() {
        return last_played;
    }

    public void setLast_played(String last_played) {
        this.last_played = last_played;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSong_length() {
        return song_length;
    }

    public void setSong_length(int song_length) {
        this.song_length = song_length;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String toString() {
        return this.last_played;
    }
}
