package com.example.activitiesandstorage;

public class Constants { // Define the Constants class to hold static final variables used throughout the app.

    // key for storing and retrieving the last added note from SharedPreferences
    public static final String NOTE_KEY = "LastNote";
    // key for storing and retrieving the date of the last note addition from SharedPreferences
    public static final String NOTE_KEY_DATE = "LastNoteAdditionDate";
    // key for storing and retrieving the set of all notes from SharedPreferences
    public static final String NOTES_ARRAY_KEY = "NotesArray";
    // name of the SharedPreferences file where all notes data will be stored
    public static final String NOTES_FILE = "NotesFile";
}

