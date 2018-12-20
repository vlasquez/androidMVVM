package com.example.andres.androidmvvm

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.andres.androidmvvm.AddEditNoteActivity.Companion.EXTRA_ID

class MainActivity : AppCompatActivity() {
  private
  val ADD_NOTE_REQUEST = 1
  val EDIT_NOTE_REQUEST = 2

  var noteViewModel: NoteViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var mAddNoteBtn: FloatingActionButton = findViewById(R.id.add_note_btn)
    mAddNoteBtn.setOnClickListener {
      var intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
      startActivityForResult(intent, ADD_NOTE_REQUEST)

    }
    val recyclerView: RecyclerView =
      findViewById(R.id.recycler_view)

    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.setHasFixedSize(true)

    var adapter = NoteAdapter()
    recyclerView.adapter = adapter

    noteViewModel = ViewModelProviders.of(this)
        .get(NoteViewModel::class.java)
    noteViewModel!!.allNotes.observe(this, Observer {
      adapter.submitList(it)
    })

    ItemTouchHelper(object : SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
      override fun onMove(
        p0: RecyclerView,
        p1: ViewHolder,
        p2: ViewHolder
      ): Boolean {
        return false
      }

      override fun onSwiped(
        p0: ViewHolder,
        p1: Int
      ) {
        noteViewModel!!.delete(adapter.getNoteAt(p0.adapterPosition))
        Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT)
            .show()
      }
    }).attachToRecyclerView(recyclerView)
    adapter.setOnItemClickListener {
      val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
      intent.putExtra(AddEditNoteActivity.EXTRA_ID, it.id)

      intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, it.title)
      intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, it.description)
      intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, it.priority)

      startActivityForResult(intent, EDIT_NOTE_REQUEST)
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
      var title = data!!.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
      var description = data!!.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
      var priority = data!!.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

      var note = Note(title, description, priority)
      noteViewModel!!.insert(note)

      Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT)
          .show()
    } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
      var id = data!!.getIntExtra(EXTRA_ID, -1)
      if (id == -1) {
        Toast.makeText(this, "Note couldn't be updated", Toast.LENGTH_SHORT)
            .show()
        return
      }
      var title = data!!.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
      var description = data!!.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
      var priority = data!!.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

      var note = Note(title, description, priority)
      note.id = id
      noteViewModel!!.update(note)
      Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT)
          .show()
    } else {
      Toast.makeText(this, "Couldn't Save the Note", Toast.LENGTH_SHORT)
          .show()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val menuInflater = menuInflater
    menuInflater.inflate(R.menu.main_manu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item!!.itemId) {
      R.id.delete_all_notes -> {
        noteViewModel!!.deleteAllNotes()
        Toast.makeText(this@MainActivity, "All notes deleted", Toast.LENGTH_SHORT)
            .show()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }

  }
}
