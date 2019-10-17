package com.sn.quizapp.utilities

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.sn.quizapp.ui.models.Words

/**
 * File reader utility.
 * Converts readed string to json.
 */
class FileReaderUtils(var context: Context) {

    val TAG = FileReaderUtils::class.java.simpleName

    //Reads file content
    fun readFileContent(fileId: Int): String {
        var outputString = ""
        try {

            val inputStream = context.resources.openRawResource(fileId)
            outputString = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            Log.e(TAG, "Error in reading file = $ex")
        }
        return outputString
    }

    //converts input string to words json
    fun convertStringToJSON(stringContent: String): Array<Words> {
        val gson = Gson()
        return gson.fromJson(stringContent, Array<Words>::class.java)

    }
}