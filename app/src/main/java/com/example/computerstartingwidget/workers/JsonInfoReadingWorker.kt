package com.example.computerstartingwidget.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.computerstartingwidget.R
import com.example.computerstartingwidget.utils.JsonParser
import com.example.computerstartingwidget.utils.ServerData

class JsonInfoReadingWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    companion object {
        const val JSON_KEY = "json key"
        const val RESULT_MASSAGE_KEY = "result massage key"
    }

    override suspend fun doWork(): Result {
        val jsonStr = inputData.getString(JSON_KEY)
        val parser = JsonParser()
        val serverData = parser.parseJson(jsonStr, ServerData::class.java)

        return when {
            serverData == null -> {
                Result.failure(resultMassageDataBuilder(R.string.json_troubles))
            }
            serverData.listenerStatus != "online" -> {
                Result.failure(resultMassageDataBuilder(R.string.router_offline))
            }
            else -> {
                Result.success(resultMassageDataBuilder(R.string.start))
            }
        }

    }

    private fun resultMassageDataBuilder(massage: Int): Data {
        return Data.Builder()
            .putInt(RESULT_MASSAGE_KEY, massage)
            .build()
    }
}