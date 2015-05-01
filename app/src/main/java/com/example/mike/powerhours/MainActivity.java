package com.example.mike.powerhours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
//        deleteDatabase(db.getDatabaseName());
    }

    public void open_playlists(View view) {
        Intent intent = new Intent(this, PlaylistActivity.class);
        startActivity(intent);
    }

    public void new_power_hour(View view) {
        Toast.makeText(this, "New Power Hour Pressed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NewPowerHourActivity.class);
        startActivity(intent);
    }

    public void quick_power_hour(View view) {
        Toast.makeText(this, "Quick Power Hour Pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
