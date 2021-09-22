package com.example.computerstartingwidget.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.computerstartingwidget.activity.App
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ComputerStartingWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    companion object {
        const val URL_STRING_KEY = "url string key"
    }

    override suspend fun doWork(): Result {
        val connectTimeout = 10000
        val urlString = inputData.getString(URL_STRING_KEY)
        val database = App.instance!!.getDatabase()!!
        val databaseDao = database.macAddressDao()
        val macAddress = databaseDao!!.getById(0) ?: return Result.failure()
        val url = URL(urlString + macAddress.address)
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = connectTimeout
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                Log.i(javaClass.simpleName, "Bad response code: " + connection.responseCode)
            }

            val inputStream = connection.inputStream
            val input = StringBuilder()
            BufferedReader(InputStreamReader(inputStream)).use {
                var line = it.readLine()
                while (line != null) {
                    input.append(line)
                    line = it.readLine()
                }
                it.close()
                Log.i(javaClass.simpleName, "Massage: $input")
            }
            inputStream.close()

            val startingData = Data.Builder()
                .putString(JsonInfoReadingWorker.JSON_KEY, input.toString())
                .build()

            return Result.success(startingData)
        } catch (e: IOException) {
            Log.i(javaClass.simpleName, e.stackTrace.toString())
            return Result.failure()
        }

    }
}