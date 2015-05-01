package com.example.mike.powerhours;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class EditPlaylistActivity extends Activity {
    private final int ACTIVITY_CHOOSE_FILE = 1;
    private DatabaseHelper db;
    private Playlist playlist;
    private ArrayList<Track> tracks;
    private ListView track_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);
    }

    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        int playlist_id = intent.getIntExtra(PlaylistActivity.EXTRA_EDIT_PLAYLIST, -1);
        if(playlist_id == -1)
            finish();
        playlist = db.getPlaylist(playlist_id);
        TextView playlist_title = (TextView)findViewById(R.id.edit_playlist_title);
        playlist_title.setText(playlist.getName());
        tracks = db.getTracks(playlist);
        track_list = (ListView)findViewById(R.id.playlist_tracks);
        track_list.setAdapter(new ArrayAdapter<Track>(this,
                android.R.layout.simple_list_item_1, tracks));
    }

    public void add_songs(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    public void save_playlist(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_CHOOSE_FILE) {
            if(resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if(uri != null) { // only one file
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(this, uri);
                    String name = (String) retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                    String artist = (String) retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    String length_string = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    int length = Integer.parseInt(length_string);
                    Track track = new Track(name, artist, length, uri.toString());
                    track.setTrack_id((int) db.addTrack(track));
                    db.addTrackToPlaylist(playlist, track);
                    tracks.add(track);
                    ((ArrayAdapter<Track>) track_list.getAdapter()).notifyDataSetChanged();
                }
                else { // multiple files uploaded
                    ClipData clipData = data.getClipData();
                    int count = clipData.getItemCount();
                    for (int i = 0; i < count; ++i) {
                        uri = clipData.getItemAt(i).getUri();
                        if (uri != null) {
                            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                            retriever.setDataSource(this, uri);
                            String name = (String) retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                            String artist = (String) retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                            String length_string = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                            int length = Integer.parseInt(length_string);
                            Track track = new Track(name, artist, length, uri.toString());
                            track.setTrack_id((int) db.addTrack(track));
                            db.addTrackToPlaylist(playlist, track);
                            tracks.add(track);
                            ((ArrayAdapter<Track>) track_list.getAdapter()).notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_playlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
