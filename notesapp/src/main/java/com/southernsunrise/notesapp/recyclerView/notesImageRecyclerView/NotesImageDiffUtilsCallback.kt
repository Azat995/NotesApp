package com.southernsunrise.notesapp.recyclerView.notesImageRecyclerView

import androidx.recyclerview.widget.DiffUtil
import com.southernsunrise.notesapp.models.NotesImageModel

class NotesImageDiffUtilsCallback : DiffUtil.ItemCallback<NotesImageModel>() {
    override fun areItemsTheSame(oldItem: NotesImageModel, newItem: NotesImageModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotesImageModel, newItem: NotesImageModel): Boolean {
        return oldItem == newItem
    }
}