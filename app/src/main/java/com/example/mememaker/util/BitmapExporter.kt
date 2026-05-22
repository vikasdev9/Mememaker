package com.example.mememaker.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import java.io.File
import java.io.FileOutputStream

object BitmapExporter {
    fun saveBitmapToDisk(context: Context, bitmap: Bitmap, filename: String): File? {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$filename.png")
        return try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
