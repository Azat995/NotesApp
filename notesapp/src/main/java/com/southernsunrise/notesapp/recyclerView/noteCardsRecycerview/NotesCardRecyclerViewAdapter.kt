package com.southernsunrise.notesapp.recyclerView.noteCardsRecycerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.southernsunrise.notesapp.databinding.NotesCardRecyclerViewItemBinding
import com.southernsunrise.notesapp.models.NoteModel

class NotesCardRecyclerViewAdapter(val deleteNoteCallback: (noteModel: NoteModel) -> Unit) :
    ListAdapter<NoteModel, NotesCardViewHolder>(NotesCardDiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesCardViewHolder {
        return NotesCardViewHolder(
            deleteNoteCallback,
            NotesCardRecyclerViewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}