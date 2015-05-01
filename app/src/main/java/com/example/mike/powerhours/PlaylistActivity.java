package com.example.mike.powerhours;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class PlaylistActivity extends Activity {
    public static final String EXTRA_EDIT_PLAYLIST = "com.example.mike.powerhours.EXTRA_EDIT_PLAYLIST";
    private String new_playlist_name;
    private DatabaseHelper db;
    private ListView playlist_list;
    private ArrayList<Playlist> playlists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
    }

    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        playlists = db.getPlaylists();
        playlist_list = (ListView)findViewById(R.id.playlists_list);
        playlist_list.setAdapter(new ArrayAdapter<Playlist>(this,
                android.R.layout.simple_list_item_1, playlists));
        playlist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Playlist selected = playlists.get(position);
                Intent intent = new Intent(getBaseContext(), EditPlaylistActivity.class);
                intent.putExtra(EXTRA_EDIT_PLAYLIST, selected.getPlaylist_id());
                startActivity(intent);
            }
        });
    }


    public void new_playlist(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Playlist");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new_playlist_name = input.getText().toString();
                Time now = new Time();
                now.setToNow();
                Playlist playlist = new Playlist(new_playlist_name, now.toString());
                playlist.setPlaylist_id((int)db.addPlaylist(playlist));
                playlists.add(playlist);
                ((ArrayAdapter<Playlist>)playlist_list.getAdapter()).notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playlist, menu);
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
