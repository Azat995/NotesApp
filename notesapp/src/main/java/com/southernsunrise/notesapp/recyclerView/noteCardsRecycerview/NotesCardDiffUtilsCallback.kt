package com.southernsunrise.notesapp.recyclerView.noteCardsRecycerview

import androidx.recyclerview.widget.DiffUtil
import com.southernsunrise.notesapp.models.NoteModel

class NotesCardDiffUtilsCallback : DiffUtil.ItemCallback<NoteModel>() {

    override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem == newItem
    }


}