package com.southernsunrise.notesapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// Activity / Fragment
fun AppCompatActivity.startFragment(@IdRes containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(containerId, fragment)
        .commit()
}


// Context
fun Context.getDisplayWidth(): Int {
    val lDisplayMetrics: DisplayMetrics = resources.displayMetrics
    return lDisplayMetrics.widthPixels
}

fun Context.getDisplayHeight(): Int {
    val lDisplayMetrics: DisplayMetrics = resources.displayMetrics
    return lDisplayMetrics.heightPixels
}

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

// fun to save bitmap image into the app storage and return the corresponding uri
fun Bitmap.saveImageBitmapAndGetUri(context: Context): Uri {
    // val uri: Uri? = saveImageToFile(System.currentTimeMillis().toString(), bitmap)
    val fileName = "img_" + System.currentTimeMillis().toString()
    val fileOutputStream =
        context.openFileOutput(fileName, Context.MODE_PRIVATE)
    this.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
    fileOutputStream.close()
    val uri: Uri = Uri.fromFile(context.getFileStreamPath(fileName))
    return uri
}

// fun to check if the given uri has access permission
fun Uri.hasPermission(context: Context): Boolean {
    val permissions = context.contentResolver?.persistedUriPermissions
    permissions?.let {
        for (i in permissions.indices) {

            if (permissions[i].uri == this && permissions[i]
                    .isReadPermission
            ) {
                return@hasPermission true
            }
        }
    } ?: context.showToast("Error accessing permissions")

    return false
}
