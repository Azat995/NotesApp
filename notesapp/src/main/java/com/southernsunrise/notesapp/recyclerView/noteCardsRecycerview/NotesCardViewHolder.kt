package com.southernsunrise.notesapp.recyclerView.noteCardsRecycerview

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.southernsunrise.notesapp.databinding.NotesCardRecyclerViewItemBinding
import com.southernsunrise.notesapp.models.NoteModel
import com.southernsunrise.notesapp.recyclerView.notesImageRecyclerView.NotesImageRecyclerViewAdapter
import com.southernsunrise.notesapp.utils.getDisplayWidth

class NotesCardViewHolder(
    private val deleteNoteCallback: (noteModel: NoteModel) -> Unit,
    private val itemViewBinding: NotesCardRecyclerViewItemBinding
) :
    RecyclerView.ViewHolder(itemViewBinding.root) {
    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    fun bind(noteModel: NoteModel) = with(itemViewBinding) {

        noteModel.apply {
            val colorInt = Color.parseColor(backgroundTintHex)
            rootCard.apply {
                setCardBackgroundColor(colorInt)
            }
            tvNoteTitle.text = title
            tvNoteContent.text = content

            ibDelete.setOnClickListener {
                deleteNoteCallback(noteModel)
            }

            val notePhotosRecyclerViewAdapter = NotesImageRecyclerViewAdapter()
            val noteImagesList = imageList
            if (noteImagesList.isNotEmpty()) {
                notePhotosRecyclerView.apply {
                    isVisible = true
                    val startPaddingValue = (context.getDisplayWidth() * 0.05).toInt()
                    setPadding(startPaddingValue, 0, 0, 0);
                    clipToPadding = false
                    adapter = notePhotosRecyclerViewAdapter
                }
                notePhotosRecyclerViewAdapter.submitList(noteImagesList)
                notePhotosRecyclerViewAdapter.notifyItemInserted(noteImagesList.lastIndex)
            }
        }


    }
}
