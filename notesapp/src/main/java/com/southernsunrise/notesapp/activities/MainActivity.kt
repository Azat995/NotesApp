package com.southernsunrise.notesapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.fragments.NotesMainContainerFragment
import com.southernsunrise.notesapp.utils.startFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFragment(R.id.activity_main_container, NotesMainContainerFragment())
    }
}