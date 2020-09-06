package com.example.notepad.Utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG= "storage"

fun persisteNote(context: Context, note: Note) {

    if (TextUtils.isEmpty(note.filename)) {
        note.filename= UUID.randomUUID().toString() + ".note"
    }

    val fileOutPut= context.openFileOutput(note.filename, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream (fileOutPut)
    outputStream.writeObject(note)
    outputStream.close()
}

fun loadNotes(context: Context) : MutableList<Note> {
    val notes = mutableListOf<Note>()
    Log.i(TAG, "Loading notes...")

    val notesDir = context.filesDir
    for(filename in notesDir.list()) {
        val note = loadNote(context, filename)
        Log.i(TAG, "Loaded note $note")
        notes.add(note)
    }
    return notes
}

fun deleteNote(context:Context, note:Note){
    context.deleteFile(note.filename)
}

private fun loadNote(context: Context, filename:String): Note {
    val fileInput= context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()

    return note
}