package com.example.andres.androidmvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class AddEditNoteActivity : AppCompatActivity() {
  companion object {
    val EXTRA_TITLE: String = "com.example.andres.androidmvvm.EXTRA_TITLE"
    val EXTRA_ID: String = "com.example.andres.androidmvvm.EXTRA_ID"
    val EXTRA_DESCRIPTION: String = "com.example.andres.androidmvvm.DESCRIPTION"
    val EXTRA_PRIORITY: String = "com.example.andres.androidmvvm.PRIORITY"
  }

  private var mTitleEt: EditText? = null
  private var mDescriptionEt: EditText? = null
  private var mNumberPicker: NumberPicker? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_note)

    mTitleEt = findViewById(R.id.title_et)
    mDescriptionEt = findViewById(R.id.description_et)
    mNumberPicker = findViewById(R.id.priority_np)

    mNumberPicker!!.minValue = 1
    mNumberPicker!!.maxValue = 10

    supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

    val intent = getIntent()
    if (intent.hasExtra(EXTRA_ID)) {
      setTitle("Edit Note")
      mDescriptionEt!!.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
      mTitleEt!!.setText(intent.getStringExtra(EXTRA_TITLE))
      mNumberPicker!!.value = intent.getIntExtra(EXTRA_DESCRIPTION, 1)
    } else {
      setTitle("Add Note")
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    var menuInflater = menuInflater
    menuInflater.inflate(R.menu.menu, menu)

    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item!!.itemId) {
      R.id.save_note -> {
        saveNote()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }

  }

  private fun saveNote() {
    var title = mTitleEt!!.text.toString()
    var description = mDescriptionEt!!.text.toString()
    var priority = mNumberPicker!!.value

    if (title.trim().isEmpty() || description.trim().isEmpty()) {
      Toast.makeText(this, "empty values", Toast.LENGTH_SHORT)
          .show()
      return
    }

    var data = Intent()
    data.putExtra(EXTRA_TITLE, title)
    data.putExtra(EXTRA_DESCRIPTION, description)
    data.putExtra(EXTRA_PRIORITY, priority)

    var id = intent.getIntExtra(EXTRA_ID, -1)
    if (id != -1) {
      data.putExtra(EXTRA_ID, id)
    }
    setResult(Activity.RESULT_OK, data)
    finish()
  }
}
