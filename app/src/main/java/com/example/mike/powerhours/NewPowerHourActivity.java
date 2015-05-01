package com.example.mike.powerhours;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class NewPowerHourActivity extends Activity {
    private DatabaseHelper db;
    private ArrayList<Track> tracks;
    private ListView track_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_power_hour);
    }

    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(this);
        tracks = db.getTracks();
        track_list = (ListView)findViewById(R.id.track_list);
        track_list.setAdapter(new ArrayAdapter<Track>(this, android.R.layout.simple_list_item_1,
                tracks));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_power_hour, menu);
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
