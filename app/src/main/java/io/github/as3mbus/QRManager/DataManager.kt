package io.github.as3mbus.QRManager

import android.os.Environment
import android.support.v7.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream


/**
 * Created by as3mbus on 31/10/17. GGWP HEYEAYEAYEAAA
 */

class DataManager {
    companion object {
        fun writeInternal(activity: AppCompatActivity, FILENAME: String, content: String) {
            val path = activity.applicationContext.filesDir.path
            val saveTarget = File(path, FILENAME)
            FileOutputStream(saveTarget).use {
                it.write(content.toByteArray())
            }
        }

        fun readInternal(activity: AppCompatActivity, FILENAME: String) {
            val path = activity.applicationContext.filesDir.path
            val saveTarget = File(path, FILENAME)
            println(saveTarget.readText())
        }

        /* Checks if external storage is available for read and write */
        fun isExternalStorageWritable(): Boolean {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == (state)
        }

        /* Checks if external storage is available to at least read */
        fun isExternalStorageReadable(): Boolean {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == (state) || Environment.MEDIA_MOUNTED_READ_ONLY == (state)
        }

        fun writePhone(activity: AppCompatActivity?, FILENAME: String, parentFolder: String?, content: ByteArray) {
            val path = activity?.applicationContext?.getExternalFilesDir(parentFolder)?.path
            val saveTarget = File(path, FILENAME)
            FileOutputStream(saveTarget).use {
                it.write(content)
            }
        }

        fun readPhone(activity: AppCompatActivity, FILENAME: String, parentFolder: String?) {
            val path = activity.applicationContext.getExternalFilesDir(parentFolder).path
            val saveTarget = File(path, FILENAME)
            println(saveTarget.readText())
        }


    }
}