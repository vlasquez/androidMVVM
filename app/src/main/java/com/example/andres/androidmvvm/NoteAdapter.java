package com.example.andres.androidmvvm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

  private OnItemClickListener listener;

  public NoteAdapter() {
    super(DIFF_CALLBACK);
  }

  private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Note>() {
        @Override public boolean areItemsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
          return oldNote.getId() == newNote.getId();
        }

        @Override public boolean areContentsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {

          return oldNote.getTitle().equals(newNote.getTitle()) && oldNote.getDescription()
              .equals(newNote.getDescription()) && oldNote.getPriority() == newNote.getPriority();
        }
      };

  @NonNull @Override public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View itemView =
        LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
    return new NoteHolder(itemView);
  }

  @Override public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
    Note currentNote = getItem(i);
    noteHolder.tvTitle.setText(currentNote.getTitle());
    noteHolder.tvPriority.setText(String.valueOf(currentNote.getPriority()));
    noteHolder.tvDescription.setText(currentNote.getDescription());
  }

  public Note getNoteAt(int pos) {
    return getItem(pos);
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

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          int pos = getAdapterPosition();
          if (listener != null && pos != RecyclerView.NO_POSITION) {
            listener.onItemClick(getItem(pos));
          }
        }
      });
    }
  }

  public interface OnItemClickListener {
    void onItemClick(Note note);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }
}
