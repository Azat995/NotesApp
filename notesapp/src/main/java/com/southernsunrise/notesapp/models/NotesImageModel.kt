package com.southernsunrise.notesapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class NotesImageModel(
    val id: Long,
    val uriString: String
) : Parcelable, Serializable
