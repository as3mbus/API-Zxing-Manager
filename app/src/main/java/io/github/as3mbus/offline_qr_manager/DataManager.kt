package io.github.as3mbus.offline_qr_manager

import android.app.assist.AssistContent
import android.support.v7.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream


/**
 * Created by as3mbus on 31/10/17.
 */

class DataManager {
    companion object {
        fun writeInternal(activity :AppCompatActivity, FILENAME : String, content: String){
            val path = activity.applicationContext.getFilesDir()
            val FILENAME = "hello_file"
            val string = "hello world!"
            val saveTarget= File(path, FILENAME)
            FileOutputStream(saveTarget).use {
                it.write(string.toByteArray())
            }
        }
        fun readInternal(activity :AppCompatActivity){
            val path = activity.applicationContext.getFilesDir()
            val FILENAME = "hello_file"
            val saveTarget = File(path, FILENAME)
            println(saveTarget.readText())
        }

    }
}