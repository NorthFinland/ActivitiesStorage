package com.example.activitiesandstorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> listNoteItems = new ArrayList<>();  // list to store the note items
    ArrayAdapter<String> adapter; // adapter to bridge between the data (listNoteItems) and the ListView
    ListView lvNotes;  // ListView widget to display the list of notes


    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate method called when the activity is created
        super.onCreate(savedInstanceState); // call the superclass onCreate to restore the activity state
        setContentView(R.layout.activity_main); // set the layout file for this activity

        lvNotes = findViewById(R.id.lvNotes); // initialize the ListView by finding it in the layout
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNoteItems);// initialize the ArrayAdapter with the context, layout for each item, and data list
        lvNotes.setAdapter(adapter); // set the adapter to the ListView to display the note
    }


    @Override
    protected void onStart() { // onStart method called when the activity becomes visible
        super.onStart(); // call the superclass onStart method

        // retrieve the shared preferences file named NOTES_FILE.
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);

        // get the last saved note, defaulting to "NA" if not found
        String lastSavedNote = sharedPref.getString(Constants.NOTE_KEY, "NA");
        // get the last saved note's date, defaulting to "1900-01-01" if not found
        String lastSavedNoteDate = sharedPref.getString(Constants.NOTE_KEY_DATE, "1900-01-01");
        // get the set of saved notes, defaulting to null if not found
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);


        if (savedSet != null) { // check if there are any saved notes
            listNoteItems.clear(); // clear the current list of notes
            listNoteItems.addAll(savedSet); // add all saved notes to the list
            adapter.notifyDataSetChanged();  // notify the adapter that the data has changed
        }

        // show a snackbar with the last saved note message.
        Snackbar.make(lvNotes, String.format("%s: %s", getString(R.string.msg_last_saved_note), lastSavedNote), Snackbar.LENGTH_LONG).show();
        // show a toast with the last saved note date
        Toast.makeText(this, lastSavedNoteDate, Toast.LENGTH_LONG).show();
    }


    // the options menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu); // inflate the menu resource file into the Menu object
        return true;
    }

    //  handle menu item selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_note) { // if the Add Note menu item is selected
            startActivity(new Intent(this, AddNoteActivity.class)); // start AddNoteActivity
            return true; // indicate that the event was handled
        } else if (item.getItemId() == R.id.delete_note) { // if the Delete Note menu item is selected
            startActivity(new Intent(this, DeleteNoteActivity.class)); // start DeleteNoteActivity
            return true; // indicate that the event was handled
        } else {
            return super.onOptionsItemSelected(item); // if the menu item is not recognized, call the superclass method
        }
    }
}