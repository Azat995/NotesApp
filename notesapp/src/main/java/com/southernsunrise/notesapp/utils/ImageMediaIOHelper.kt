package com.southernsunrise.notesapp.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.southernsunrise.notesapp.fragments.CreateNoteBottomSheetDialogFragment


class ImageMediaIOHelper(
    private val bottomSheetDialogFragment: CreateNoteBottomSheetDialogFragment,
    singlePhotoPickSuccessCallback: (uri: Uri) -> Unit,
    cameraImageTakeSuccessCallback: (uri: Uri) -> Unit
) {
    val permissionReadExternalResult =
        bottomSheetDialogFragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                // Permission is granted, so open the image picker.
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            } else {
                // Permission is not granted, so show a toast message to the user.
                bottomSheetDialogFragment.context?.showToast("Permission not granted to open the picker")
            }
        }


    val singlePhotoPickerLauncher =
        bottomSheetDialogFragment.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("pickedMediaUri", uri.toString())
                takePersistableUriPermission(uri)
                singlePhotoPickSuccessCallback(uri)

            }
        }


    val openCameraLauncher =
        bottomSheetDialogFragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {

                if (result.data != null) {
                    val bitmap = result.data?.extras?.get("data") as Bitmap
                    bottomSheetDialogFragment.apply {

                        val uri = bitmap.saveImageBitmapAndGetUri(requireContext())
                        takePersistableUriPermission(uri)
                        cameraImageTakeSuccessCallback(uri)

                    }
                }
            }
        }


    private fun takePersistableUriPermission(uri: Uri) {

        if (uri.hasPermission(bottomSheetDialogFragment.requireContext())) {
            // Permission for this uri exists, no need to request additional permission
            Log.i("Uri permission state :", "the uri \"${uri.toString()}\" has permission!")
        } else try {
            // permission for this uri does not exist so permission is requested
            Log.i(
                "Uri permission state :",
                "the uri \"${uri.toString()}\" does not have permission!"
            )
            val contentResolver =
                bottomSheetDialogFragment.requireContext().contentResolver
            bottomSheetDialogFragment.requireContext().apply {
                grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
            contentResolver.takePersistableUriPermission(uri, takeFlags)
        } catch (e: Exception) {
            Log.e("takePersistableUriPermissionError:", e.stackTraceToString())
        }
    }


}

