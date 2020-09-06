package com.example.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        val REQUEST_EDIT_NOTE = 1
        val EXTRA_NOTE= "note"
        val EXTRA_NOTE_INDEX = "noteIndex"

        val ACTION_SAVE_NOTE = "com.example.notepad.action.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "com.example.notepad.action.ACTION_DELETE_NOTE"
    }

    lateinit var note: Note
    var noteIndex: Int= -1

    lateinit var titleView: TextView
    lateinit var  textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        note= intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex= intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleView= findViewById<TextView>(R.id.title)!!
        textView = findViewById<TextView>(R.id.text)!!

        titleView.text=note.title
        textView.text=note.text
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save->{
                saveNote()
                return true
            }
            R.id.action_delete-> {
                showConfirmDeleteNoteDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

         fun showConfirmDeleteNoteDialog() {
            val confirmFragment = note.title.let { ConfirmDeleteNoteDialogFragment() }
            confirmFragment.listener = object :ConfirmDeleteNoteDialogFragment.ConfirmDeleteDialogListener{
                override fun onDialogPositiveClick() {
                    deleteNote()
                }
                override fun onDialogNegativeClick (){ }

            }
            confirmFragment.show(supportFragmentManager, "ConfimDelete")
        }


    fun saveNote() {
       note.title = titleView.text.toString()
        note.text = textView.text.toString()

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun deleteNote (){

        intent= Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}