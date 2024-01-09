package com.southernsunrise.notesapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class NoteModel(
    val id: Long,
    val title: String ,
    val content: String,
    val imageList: ArrayList<NotesImageModel>,
    val backgroundTintHex: String
) : Parcelable, Serializable


