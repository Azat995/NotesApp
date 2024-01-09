package com.southernsunrise.notesapp.recyclerView.notesImageRecyclerView

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.southernsunrise.notesapp.databinding.NotesImageRecyclerviewItemBinding
import com.southernsunrise.notesapp.models.NotesImageModel


class NotesImageViewHolder(private val itemViewBinding: NotesImageRecyclerviewItemBinding) :
    RecyclerView.ViewHolder(itemViewBinding.root) {
    fun bind(imageModel: NotesImageModel) = with(itemViewBinding) {
        val uri = Uri.parse(imageModel.uriString)
        ibImageNote.setImageURI(uri)
    }
}