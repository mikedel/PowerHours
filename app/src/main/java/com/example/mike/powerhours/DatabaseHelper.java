package com.example.mike.powerhours;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "powerhours";

    // Table Names
    private static final String TABLE_TRACKS = "tracks";
    private static final String TABLE_PLAYLISTS = "playlists";
    private static final String TABLE_SETLISTS = "setlists";
    private static final String TABLE_POWER_HOURS = "power_hours";

    // Universal Column Name
    private static final String KEY_ID = "id";

    // TABLE_TRACKS - column names
    private static final String KEY_TRACKS_NAME = "name";
    private static final String KEY_TRACKS_ARTIST = "artist";
    private static final String KEY_TRACKS_LENGTH = "length";
    private static final String KEY_TRACKS_LOCATION = "location";

    // TABLE_PLAYLISTS - column names
    private static final String KEY_PLAYLISTS_NAME = "name";
    private static final String KEY_PLAYLISTS_LAST_PLAYED = "last_played";

    // TABLE_PLAYLIST_TRACKS - column names
    private static final String KEY_SETLISTS_PLAYLIST = "playlist";
    private static final String KEY_SETLISTS_TRACK = "track";

    // TABLE_POWER_HOURS - column names
    private static final String KEY_POWER_HOURS_PLAYLIST = "playlist";
    private static final String KEY_POWER_HOURS_LAST_PLAYED = "last_played";
    private static final String KEY_POWER_HOURS_LENGTH = "length";
    private static final String KEY_POWER_HOURS_SONG_LENGTH = "song_length";
    private static final String KEY_POWER_HOURS_SOUND = "sound";


    //CREATE TABLE statements
    private static final String CREATE_TABLE_TRACKS = "CREATE TABLE " + TABLE_TRACKS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_TRACKS_NAME + " TEXT, " +
            KEY_TRACKS_ARTIST + " TEXT, " +
            KEY_TRACKS_LENGTH + " INTEGER, " +
            KEY_TRACKS_LOCATION + " TEXT);";
    private static final String CREATE_TABLE_PLAYLISTS = "CREATE TABLE " + TABLE_PLAYLISTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_PLAYLISTS_NAME + " TEXT, " +
            KEY_PLAYLISTS_LAST_PLAYED + " TEXT);";
    private static final String CREATE_TABLE_SETLISTS = "CREATE TABLE " + TABLE_SETLISTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_SETLISTS_PLAYLIST + " INTEGER, " +
            KEY_SETLISTS_TRACK + " INTEGER, " +
            "FOREIGN KEY(" + KEY_SETLISTS_PLAYLIST + ") REFERENCES " + TABLE_PLAYLISTS + "(" + KEY_ID + "), " +
            "FOREIGN KEY(" + KEY_SETLISTS_TRACK + ") REFERENCES " + TABLE_TRACKS + "(" + KEY_ID + "));";
    private static final String CREATE_TABLE_POWER_HOURS = "CREATE TABLE " + TABLE_POWER_HOURS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_POWER_HOURS_PLAYLIST + " INTEGER, " +
            KEY_POWER_HOURS_LAST_PLAYED + " TEXT, " +
            KEY_POWER_HOURS_LENGTH + " INTEGER, " +
            KEY_POWER_HOURS_SONG_LENGTH + " INTEGER, " +
            KEY_POWER_HOURS_SOUND + " TEXT, " +
            "FOREIGN KEY(" + KEY_POWER_HOURS_PLAYLIST + ") REFERENCES " + TABLE_PLAYLISTS + "(" + KEY_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB onCreate:", CREATE_TABLE_TRACKS);
        db.execSQL(CREATE_TABLE_TRACKS);
        Log.d("DB onCreate:", CREATE_TABLE_PLAYLISTS);
        db.execSQL(CREATE_TABLE_PLAYLISTS);
        Log.d("DB onCreate:", CREATE_TABLE_SETLISTS);
        db.execSQL(CREATE_TABLE_SETLISTS);
        Log.d("DB onCreate:", CREATE_TABLE_POWER_HOURS);
        db.execSQL(CREATE_TABLE_POWER_HOURS);
        Log.d("DB onCreate:", "Tables created successfully");
    }

    @Override //TODO Correct later - should save db on update, and later push to cloud
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB onUpgrade:", "Dropping table : " + TABLE_TRACKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKS);
        Log.d("DB onUpgrade:", "Dropping table : " + TABLE_PLAYLISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLISTS);
        Log.d("DB onUpgrade:", "Dropping table : " + TABLE_SETLISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETLISTS);
        Log.d("DB onUpgrade:", "Dropping table : " + TABLE_POWER_HOURS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POWER_HOURS);
        Log.d("DB onUpgrade:", "All tables successfully dropped");
        onCreate(db);
    }

    // ------------------ TRACKS methods --------------------- //
    public long addTrack(Track track) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TRACKS_NAME, track.getName());
        values.put(KEY_TRACKS_ARTIST, track.getArtist());
        values.put(KEY_TRACKS_LENGTH, track.getLength());
        values.put(KEY_TRACKS_LOCATION, track.getLocation());
        long result = db.insertWithOnConflict(TABLE_TRACKS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return result;
    }

    public Track getTrack(long track_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TRACKS + " WHERE " + KEY_ID + " = " + track_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null)
            return null;
        if(c.moveToFirst()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID));
            String name = c.getString(c.getColumnIndex(KEY_TRACKS_NAME));
            String artist = c.getString(c.getColumnIndex(KEY_TRACKS_ARTIST));
            int length = c.getInt(c.getColumnIndex(KEY_TRACKS_LENGTH));
            String location = c.getString(c.getColumnIndex(KEY_TRACKS_LOCATION));
            db.close();
            return new Track(id, name, artist, length, location);
        }
        else
            return null;
    }

    public ArrayList<Track> getTracks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Track> tracks = new ArrayList<Track>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRACKS;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String name = c.getString(c.getColumnIndex(KEY_TRACKS_NAME));
                String artist = c.getString(c.getColumnIndex(KEY_TRACKS_ARTIST));
                int length = c.getInt(c.getColumnIndex(KEY_TRACKS_LENGTH));
                String location = c.getString(c.getColumnIndex(KEY_TRACKS_LOCATION));
                Track track = new Track(id, name, artist, length, location);
                tracks.add(track);
            } while (c.moveToNext());
        }
        db.close();
        return tracks;
    }

    public int updateTrack(Track track) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, track.getTrack_id());
        values.put(KEY_TRACKS_NAME, track.getName());
        values.put(KEY_TRACKS_ARTIST, track.getArtist());
        values.put(KEY_TRACKS_LENGTH, track.getLength());
        values.put(KEY_TRACKS_LOCATION, track.getLocation());
        int result = db.update(TABLE_TRACKS, values, KEY_ID + " = ?", new String[] {String.valueOf(track.getTrack_id())});
        db.close();
        return result;
    }

    public void deleteTrack(long track_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRACKS, KEY_ID + " = ?", new String[] {String.valueOf(track_id)});
        db.close();
    }

    // ------------------ PLAYLISTS methods --------------------- //
    public long addPlaylist(Playlist playlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYLISTS_NAME, playlist.getName());
        values.put(KEY_PLAYLISTS_LAST_PLAYED, playlist.getLast_played());
        long result = db.insertWithOnConflict(TABLE_PLAYLISTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return result;
    }

    public Playlist getPlaylist(long playlist_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYLISTS + " WHERE " + KEY_ID + " = " + playlist_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null)
            return null;
        if(c.moveToFirst()) {
            int id = c.getInt(c.getColumnIndex(KEY_ID));
            String name = c.getString(c.getColumnIndex(KEY_PLAYLISTS_NAME));
            String last_played = c.getString(c.getColumnIndex(KEY_PLAYLISTS_LAST_PLAYED));
            db.close();
            return new Playlist(id, name, last_played);
        }
        else
            return null;
    }

    public ArrayList<Playlist> getPlaylists() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYLISTS;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String name = c.getString(c.getColumnIndex(KEY_PLAYLISTS_NAME));
                String last_played = c.getString(c.getColumnIndex(KEY_PLAYLISTS_LAST_PLAYED));
                Playlist playlist = new Playlist(id, name, last_played);
                playlists.add(playlist);
            } while (c.moveToNext());
        }
        db.close();
        return playlists;
    }

    public int updatePlaylist(Playlist playlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, playlist.getPlaylist_id());
        values.put(KEY_PLAYLISTS_NAME, playlist.getName());
        values.put(KEY_PLAYLISTS_LAST_PLAYED, playlist.getLast_played());
        int result = db.update(TABLE_PLAYLISTS, values, KEY_ID + " = ?", new String[] {String.valueOf(playlist.getPlaylist_id())});
        db.close();
        return result;
    }

    public void deletePlaylist(long playlist_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYLISTS, KEY_ID + " = ?", new String[] {String.valueOf(playlist_id)});
        db.close();
    }

    // ------------------ SETLIST methods --------------------- //
    public long addTrackToPlaylist(Playlist p, Track t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SETLISTS_PLAYLIST, p.getPlaylist_id());
        values.put(KEY_SETLISTS_TRACK, t.getTrack_id());
        long result = db.insertWithOnConflict(TABLE_SETLISTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return result;
    }

    public ArrayList<Track> getTracks(Playlist p) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Track> tracks = new ArrayList<Track>();
        String selectQuery = "SELECT * FROM " + TABLE_SETLISTS + " WHERE " +
                KEY_SETLISTS_PLAYLIST + " + " + p.getPlaylist_id();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c == null)
            return null;
        if (c.moveToFirst()) {
            do {
                Track track = getTrack(c.getInt(c.getColumnIndex(KEY_SETLISTS_TRACK)));
                if(track != null)
                    tracks.add(track);
            } while (c.moveToNext());
        }
        db.close();
        return tracks;
    }

    public ArrayList<Playlist> getPlaylists(Track t) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        String selectQuery = "SELECT * FROM " + TABLE_SETLISTS + " WHERE "
                + KEY_SETLISTS_TRACK + " = " + t.getTrack_id();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c == null)
            return null;
        if (c.moveToFirst()) {
            do {
                Playlist playlist = getPlaylist(c.getInt(c.getColumnIndex(KEY_SETLISTS_PLAYLIST)));
                if(playlist != null)
                    playlists.add(playlist);
            } while (c.moveToNext());
        }
        db.close();
        return playlists;
    }

    public void deleteSetlist(long setlist_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SETLISTS, KEY_ID + " = ?", new String[] {String.valueOf(setlist_id)});
        db.close();
    }

    // ------------------ POWER_HOURS methods --------------------- //
    public long addPowerHour(PowerHour power_hour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_POWER_HOURS_PLAYLIST, power_hour.getPlaylist_id());
        values.put(KEY_POWER_HOURS_LAST_PLAYED, power_hour.getLast_played());
        values.put(KEY_POWER_HOURS_LENGTH, power_hour.getLength());
        values.put(KEY_POWER_HOURS_SONG_LENGTH, power_hour.getSong_length());
        values.put(KEY_POWER_HOURS_SOUND, power_hour.getSound());
        long result = db.insertWithOnConflict(TABLE_POWER_HOURS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return result;
    }

    public PowerHour getPowerHour(long power_hour_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_POWER_HOURS + " WHERE " + KEY_ID + " = " + power_hour_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c == null)
            return null;
        c.moveToFirst();
        int id = c.getInt(c.getColumnIndex(KEY_ID));
        int playlist = c.getInt(c.getColumnIndex(KEY_POWER_HOURS_PLAYLIST));
        String last_played = c.getString(c.getColumnIndex(KEY_POWER_HOURS_LAST_PLAYED));
        int length = c.getInt(c.getColumnIndex(KEY_POWER_HOURS_LENGTH));
        int song_length = c.getInt(c.getColumnIndex(KEY_POWER_HOURS_SONG_LENGTH));
        String sound = c.getString(c.getColumnIndex(KEY_POWER_HOURS_SOUND));
        db.close();
        return new PowerHour(id, playlist, last_played, length, song_length, sound);
    }

    public ArrayList<PowerHour> getPowerHours() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PowerHour> power_hours = new ArrayList<PowerHour>();
        String selectQuery = "SELECT  * FROM " + TABLE_POWER_HOURS;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                int playlist = c.getInt(c.getColumnIndex(KEY_POWER_HOURS_PLAYLIST));
                String last_played = c.getString(c.getColumnIndex(KEY_POWER_HOURS_LAST_PLAYED));
                int length = c.getInt(c.getColumnIndex(KEY_POWER_HOURS_LENGTH));
                int song_length = c.getInt(c.getColumnIndex(KEY_POWER_HOURS_SONG_LENGTH));
                String sound = c.getString(c.getColumnIndex(KEY_POWER_HOURS_SOUND));
                PowerHour power_hour = new PowerHour(id, playlist, last_played, length, song_length, sound);
                power_hours.add(power_hour);
            } while (c.moveToNext());
        }
        db.close();
        return power_hours;
    }

    public int updatePowerHour(PowerHour power_hour) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, power_hour.getPower_hour_id());
        values.put(KEY_POWER_HOURS_PLAYLIST, power_hour.getPlaylist_id());
        values.put(KEY_POWER_HOURS_LAST_PLAYED, power_hour.getLast_played());
        values.put(KEY_POWER_HOURS_LENGTH, power_hour.getLength());
        values.put(KEY_POWER_HOURS_SONG_LENGTH, power_hour.getSong_length());
        values.put(KEY_POWER_HOURS_SOUND, power_hour.getSound());
        int result = db.update(TABLE_POWER_HOURS, values, KEY_ID + " = ?", new String[] {String.valueOf(power_hour.getPower_hour_id())});
        db.close();
        return result;
    }

    public void deletePowerHour(long power_hour_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POWER_HOURS, KEY_ID + " = ?", new String[] {String.valueOf(power_hour_id)});
        db.close();
    }

    //---------------- close -------------------//
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}