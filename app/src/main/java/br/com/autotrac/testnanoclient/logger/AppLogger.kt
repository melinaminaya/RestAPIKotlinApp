package br.com.autotrac.testnanoclient.logger

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppLogger {
    private const val TAG = "AppLogger"
    private var logFile: File? = null
    private var lines = mutableListOf<String>()

    fun init(context: Context?) {
        logFile = createLogFile(context)
    }

    private fun createLogFile(context: Context?): File? {
        if (context == null) {
            return null
        }

        val logDir = File(context.filesDir, "logs")
        logDir.mkdirs()
        return File(logDir, "app_logs.txt")
    }

    fun log(message: String) {
        val logLine = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())} $message"
        try {
            val logFile = logFile ?: return
            FileWriter(logFile, true).use { writer ->
                writer.append(logLine).append('\n')
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error writing log", e)
        }
        lines.add(logLine)
//        Log.d(TAG, logLine)
    }
    fun getLogs(): List<String> {
//        try {
//            return logFile?.readLines() ?: emptyList()
//        } catch (e: IOException) {
//            Log.e(TAG, "Error reading logs", e)
//        }
//        return emptyList()
        return lines.toList()
    }


}
