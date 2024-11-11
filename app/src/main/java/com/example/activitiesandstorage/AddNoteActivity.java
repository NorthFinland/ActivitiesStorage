package com.example.activitiesandstorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    EditText edNote; // declaration EditText for entering a note

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate method called when the activity is created
        super.onCreate(savedInstanceState); // called the superclass onCreate to restore the activity state
        setContentView(R.layout.add_note_activity); // set the layout file for this activity

        edNote = findViewById(R.id.editTextNoteName); // initialized EditText by finding it in the layout
    }

    // method called when the Save and Close button is clicked
    public void onBtnSaveAndCloseClick(View view) {
        String noteToAdd = edNote.getText().toString(); // get the text from EditText and convert it to a string

        Date c = Calendar.getInstance().getTime(); // get the current date and time
        System.out.println("Current time => " + c); // print the current date and time to the console

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        // define the date format
        String formattedDate = df.format(c); // format the current date to a string

        // retrieve the shared preferences file named NOTES_FILE
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit(); // get an editor to modify the shared preferences

        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);
        // retrieve the existing set of notes
        Set<String> newSet = new HashSet<>(); // create a new set to hold the notes

        if (savedSet != null) { // check if there are any saved notes
            newSet.addAll(savedSet); // add all existing notes to the new set
        }
        newSet.add(noteToAdd); // add the new note to the set

        editor.putString(Constants.NOTE_KEY, noteToAdd); // save the note text
        editor.putString(Constants.NOTE_KEY_DATE, formattedDate); // save the formatted date
        editor.putStringSet(Constants.NOTES_ARRAY_KEY, newSet); // save the updated set of notes
        editor.apply(); // applied changes to the shared preferences

        finish();
    }
}

