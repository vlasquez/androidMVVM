package com.example.andres.androidmvvm;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface NoteDAO {

  @Insert
  void insert(Note note);

  @Update
  void update(Note note);

  @Delete
  void delete(Note note);

  @Query("DELETE FROM tb_note")
  void deleteAllNotes();

  @Query("SELECT * FROM tb_note ORDER BY priority DESC")
  LiveData<List<Note>> getAllNotes();
}
