package com.southernsunrise.notesapp.recyclerView.notesImageRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.southernsunrise.notesapp.databinding.NotesImageRecyclerviewItemBinding
import com.southernsunrise.notesapp.models.NotesImageModel

class NotesImageRecyclerViewAdapter :
    ListAdapter<NotesImageModel, NotesImageViewHolder>(
        NotesImageDiffUtilsCallback()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesImageViewHolder {
        return NotesImageViewHolder(
            NotesImageRecyclerviewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}