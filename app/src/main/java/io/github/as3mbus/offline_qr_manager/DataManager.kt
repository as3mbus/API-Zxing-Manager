package io.github.as3mbus.offline_qr_manager

import android.app.assist.AssistContent
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import android.os.Environment.MEDIA_MOUNTED_READ_ONLY
import android.os.Environment.MEDIA_MOUNTED
import android.view.ViewParent


/**
 * Created by as3mbus on 31/10/17.
 */

class DataManager {
    companion object {
        fun writeInternal(activity :AppCompatActivity, FILENAME : String, content: String){
            val path = activity.applicationContext.filesDir.path
            val saveTarget= File(path, FILENAME)
            FileOutputStream(saveTarget).use {
                it.write(content.toByteArray())
            }
        }
        fun readInternal(activity :AppCompatActivity, FILENAME : String){
            val path = activity.applicationContext.filesDir.path
            val saveTarget = File(path, FILENAME)
            println(saveTarget.readText())
        }

        /* Checks if external storage is available for read and write */
        fun isExternalStorageWritable(): Boolean {
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED.equals(state)) {
                true
            } else false
        }

        /* Checks if external storage is available to at least read */
        fun isExternalStorageReadable(): Boolean {
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                true
            } else false
        }
        fun writePhone(activity: AppCompatActivity?,FILENAME: String, parentFolder: String?,content: ByteArray)
        {
            val path = activity?.applicationContext?.getExternalFilesDir(parentFolder)?.path
            val saveTarget= File(path, FILENAME)
            FileOutputStream(saveTarget).use {
                it.write(content)}
        }
        fun readPhone(activity :AppCompatActivity, FILENAME : String,parentFolder: String?)
        {
            val path = activity.applicationContext.getExternalFilesDir(parentFolder).path
            val saveTarget = File(path, FILENAME)
            println(saveTarget.readText())
        }


    }
}