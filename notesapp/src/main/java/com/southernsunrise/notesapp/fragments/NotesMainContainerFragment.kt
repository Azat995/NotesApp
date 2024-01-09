package com.southernsunrise.notesapp.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.southernsunrise.notesapp.databinding.FragmentNotesMainContainerBinding
import com.southernsunrise.notesapp.models.NoteModel
import com.southernsunrise.notesapp.recyclerView.noteCardsRecycerview.NotesCardRecyclerViewAdapter
import java.lang.reflect.Type


class NotesMainContainerFragment : Fragment() {

    private var _binding: FragmentNotesMainContainerBinding? = null
    private val binding get() = _binding!!
    private var notesList: ArrayList<NoteModel>? = null
    private lateinit var notesCardRecyclerViewAdapter: NotesCardRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesMainContainerBinding.inflate(inflater, container, false)
        val view = binding.root
        getNotesFromSharedPrefsIfExist()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAddButton()
        setupNoteCardRecyclerView()
    }


    private fun setupNoteCardRecyclerView() {
        notesCardRecyclerViewAdapter = NotesCardRecyclerViewAdapter(::deleteNoteCallBack)
        binding.notesCardRecyclerView.adapter = notesCardRecyclerViewAdapter
        notesCardRecyclerViewAdapter.submitList(notesList)
    }

    private fun deleteNoteCallBack(noteModel: NoteModel) {
        notesList?.apply {
            notesCardRecyclerViewAdapter.notifyItemRemoved(notesList!!.indexOf(noteModel))
            remove(noteModel)
            updateSharedPrefsNotes()
            binding.viewNoNotes.isGone = this.isNotEmpty()
        }
    }


    private fun setUpAddButton() {
        binding.ibAdd.apply {
            setOnClickListener {
                openCreateNoteBottomSheet()
            }
        }
    }

    private fun openCreateNoteBottomSheet() {
        val createNoteBottomSheetDialog =
            CreateNoteBottomSheetDialogFragment(addNoteCallback = ::addNoteToRecyclerViewCallBack)
        createNoteBottomSheetDialog.show(
            childFragmentManager,
            createNoteBottomSheetDialog.javaClass.simpleName
        )
    }

    private fun addNoteToRecyclerViewCallBack(noteModel: NoteModel) {
        binding.viewNoNotes.isGone = true
        notesList?.add(noteModel)
        notesList?.apply {
            updateSharedPrefsNotes()
            //Log.i("NoteModel::::", noteModel.toString())
            notesCardRecyclerViewAdapter.notifyItemInserted(lastIndex)
        }

    }

    private fun getNotesFromSharedPrefsIfExist() {
        val sharedPrefs: SharedPreferences? = activity?.getPreferences(MODE_PRIVATE)
        sharedPrefs?.let {

            val gson = Gson()
            val json: String? = it.getString("NotesList", "")
            val objectType: Type = object : TypeToken<ArrayList<NoteModel>?>() {}.type

            val notesList: ArrayList<NoteModel>? =
                gson.fromJson(json, objectType)

            if (!notesList.isNullOrEmpty()) {
                this@NotesMainContainerFragment.notesList = notesList
                binding.viewNoNotes.isVisible = false
            } else {
                this@NotesMainContainerFragment.notesList = ArrayList()
                binding.viewNoNotes.isVisible = true
            }
        }
    }

    private fun updateSharedPrefsNotes() {
        val sharedPrefs = activity?.getPreferences(MODE_PRIVATE)
        sharedPrefs?.let {
            val prefsEditor: Editor = it.edit()

            val gson = Gson()
            val json: String = gson.toJson(notesList)
            prefsEditor.putString("NotesList", json)
            prefsEditor.commit()
        }
    }
}