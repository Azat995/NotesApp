package com.southernsunrise.notesapp.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.southernsunrise.notesapp.R
import com.southernsunrise.notesapp.databinding.FragmentCreateNoteBottomSheetDialogBinding
import com.southernsunrise.notesapp.models.NoteModel
import com.southernsunrise.notesapp.models.NotesImageModel
import com.southernsunrise.notesapp.recyclerView.notesImageRecyclerView.NotesImageRecyclerViewAdapter
import com.southernsunrise.notesapp.utils.ImageMediaIOHelper
import com.southernsunrise.notesapp.utils.getDisplayHeight
import com.southernsunrise.notesapp.utils.getDisplayWidth


class CreateNoteBottomSheetDialogFragment(val addNoteCallback: (NoteModel) -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentCreateNoteBottomSheetDialogBinding? = null
    val binding get() = _binding!!

    private lateinit var notesImageRecyclerViewAdapter: NotesImageRecyclerViewAdapter
    private val notesImageList: ArrayList<NotesImageModel> = ArrayList()
    private lateinit var imageMediaIOHelper: ImageMediaIOHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageMediaIOHelper = ImageMediaIOHelper(
            this,
            singlePhotoPickSuccessCallback = ::updateNotesImageRecyclerView,
            cameraImageTakeSuccessCallback = ::updateNotesImageRecyclerView
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheet()
        setupViews()

    }

    override fun getTheme(): Int {
        return R.style.CreateNoteBottomSheetStyle
    }


    private fun setupViews() {
        setupEditTexts()
        setupButtons()
        setupNotesImageRecyclerView()

    }


    private fun setupEditTexts() {
        binding.apply {
            etNoteTitle.setBottomSheetEditTextListener()
            etNoteContent.setBottomSheetEditTextListener()
        }
    }

    private fun setupButtons() {
        setupDoneButton()
        setupChoosePhotoButton()
        setupOpenCameraButton()
    }

    private fun setupOpenCameraButton() {
        binding.ibCamera.setOnClickListener {
            openCamera()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageMediaIOHelper.openCameraLauncher.launch(cameraIntent)
    }


    private fun setupDoneButton() {

        val colorList = resources.getStringArray(R.array.notes_cards_background_color_hex_codes)

        binding.ibDone.setOnClickListener {
            addNoteCallback(
                NoteModel(
                    System.currentTimeMillis() * (1..999).random(),
                    binding.etNoteTitle.text.toString().trim(),
                    binding.etNoteContent.text.toString().trim(),
                    imageList = notesImageList,
                    backgroundTintHex = colorList.random()
                )
            )
            dismiss()
        }
    }

    private fun EditText.setBottomSheetEditTextListener() {
        this.addTextChangedListener {
            it?.let {
                binding.ibDone.isEnabled = editTextsInputValid()
            }
        }
    }

    private fun editTextsInputValid() =
        binding.etNoteTitle.text.toString()
            .isNotBlank() && binding.etNoteContent.text.toString().isNotBlank()


    private fun setupChoosePhotoButton() {

        binding.ibChoosePhoto.setOnClickListener {

            if (ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable(requireContext())) {
                imageMediaIOHelper.singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            } else {
                imageMediaIOHelper.permissionReadExternalResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun updateNotesImageRecyclerView(uri: Uri) {
        binding.rvNotePhotos.isVisible = true
        notesImageList.add(NotesImageModel(System.currentTimeMillis(), uri.toString()))
        Log.d("NoteImagesList:::", notesImageList.toString())
        notesImageRecyclerViewAdapter.apply {
            submitList(
                notesImageList
            )
            notifyItemInserted(currentList.lastIndex)
        }
    }


    private fun setupNotesImageRecyclerView() {

        notesImageRecyclerViewAdapter = NotesImageRecyclerViewAdapter()

        activity?.apply {
            // set recyclerView start padding to the screen width's 5 percent
            val startPaddingValue = (getDisplayWidth() * 0.05).toInt()
            binding.rvNotePhotos.apply {
                setPadding(startPaddingValue, 0, 0, 0);
                clipToPadding = false
                adapter = notesImageRecyclerViewAdapter
            }
        }

    }


    private fun setupBottomSheet() {
        (dialog as? BottomSheetDialog)?.apply {
            setCanceledOnTouchOutside(true)
            with(behavior) {
                state = BottomSheetBehavior.STATE_HALF_EXPANDED
                isDraggable = true
                isFitToContents = false
            }

            activity?.apply {
                changeRootViewHeight(getDisplayHeight())
            }
        }
    }

    private fun changeRootViewHeight(height: Int) {
        binding.root.minimumHeight = height
    }
}



