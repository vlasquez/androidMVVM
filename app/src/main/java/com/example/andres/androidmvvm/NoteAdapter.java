package com.example.andres.androidmvvm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

  private List<Note> notes = new ArrayList<>();

  @NonNull @Override public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View itemView =
        LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
    return new NoteHolder(itemView);
  }

  @Override public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
    Note currentNote = notes.get(i);
    noteHolder.tvTitle.setText(currentNote.getTitle());
    noteHolder.tvPriority.setText(String.valueOf(currentNote.getPriority()));
    noteHolder.tvDescription.setText(currentNote.getDescription());
  }

  @Override public int getItemCount() {
    return notes.size();
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
    notifyDataSetChanged();
  }

  class NoteHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvPriority;

    public NoteHolder(View itemView) {
      super(itemView);

      tvTitle = itemView.findViewById(R.id.text_view_title);
      tvDescription = itemView.findViewById(R.id.text_view_description);
      tvPriority = itemView.findViewById(R.id.text_view_priority);
    }
  }
}
