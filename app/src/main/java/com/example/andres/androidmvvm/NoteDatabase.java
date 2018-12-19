package com.example.andres.androidmvvm;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = { Note.class }, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

  private static NoteDatabase instance;

  public abstract NoteDAO noteDAO();

  public static synchronized NoteDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context, NoteDatabase.class, "note_database")
          .fallbackToDestructiveMigration()
          .addCallback(roomCallback)
          .build();
    }
    return instance;
  }

  private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new PopulateDbAsyncTask(instance).execute();
    }
  };

  private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

    private NoteDAO noteDAO;

    private PopulateDbAsyncTask(NoteDatabase noteDatabase) {
      noteDAO = noteDatabase.noteDAO();
    }

    @Override
    protected Void doInBackground(Void... voids) {
      noteDAO.insert(new Note("Title 1", "Description 1", 1));
      noteDAO.insert(new Note("Title 2", "Description 2", 2));
      noteDAO.insert(new Note("Title 3", "Description 3", 3));
      return null;
    }
  }
}
