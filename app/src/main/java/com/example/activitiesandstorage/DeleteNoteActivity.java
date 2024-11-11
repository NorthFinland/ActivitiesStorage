package com.example.activitiesandstorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

import java.util.HashSet;

public class DeleteNoteActivity extends AppCompatActivity {

    private ListView lvNotesToDelete; // listView to show notes
    private ArrayList<String> noteList = new ArrayList<>(); // initialize the note list

    private ArrayAdapter<String> adapter; // adapter for ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_note_activity);

        lvNotesToDelete = findViewById(R.id.lvNotesToDelete); // initialize ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteList);// initialize the ArrayAdapter with the context, layout for each item, and data list
        lvNotesToDelete.setAdapter(adapter); // set the adapter to the ListView to display the note

        // load the saved notes from SharedPreferences
        loadNotes();

        // set an item click listener for deleting notes
        lvNotesToDelete.setOnItemClickListener((parent, view, position, id) -> {
            // get the selected note
            String noteToDelete = noteList.get(position);
            // delete the note
            deleteNoteFromSharedPreferences(noteToDelete);
        });
    }

    private void loadNotes() {
        SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());

        // clear and reload notes into the list
        noteList.clear();
        noteList.addAll(savedSet);
        adapter.notifyDataSetChanged();
    }



    private void deleteNoteFromSharedPreferences(String noteToDelete) {
        // access SharedPreferences file where notes are saved
        SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // retrieve the current set of notes from SharedPreferences
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());

        //  if the note exists in the set and remove it
        if (savedSet.contains(noteToDelete)) {

            // remove the note from the set
            savedSet.remove(noteToDelete);

            // save the updated set back into SharedPreferences
            editor.putStringSet(Constants.NOTES_ARRAY_KEY, savedSet);
            editor.apply();  // Save changes asynchronously

            // notify the user that the note was deleted
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();

            // refresh the ListView by reloading notes
            loadNotes();
        } else {
            // note was not found
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
        }
    }

}

