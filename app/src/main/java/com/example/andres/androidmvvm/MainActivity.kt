package com.example.andres.androidmvvm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  var noteViewModel: NoteViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val recyclerView: RecyclerView =
      findViewById(com.example.andres.androidmvvm.R.id.recycler_view)

    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.setHasFixedSize(true)

    var adapter = NoteAdapter()
    recyclerView.adapter = adapter

    noteViewModel = ViewModelProviders.of(this)
        .get(NoteViewModel::class.java)
    noteViewModel!!.allNotes.observe(this, Observer {
      adapter.setNotes(it)
    })
  }
}
