package com.example.computerstartingwidget.workers

import android.content.Context
import android.widget.Toast
import androidx.work.*
import com.example.computerstartingwidget.R

object StartWorkerTasks {

    private fun startComputerTask(): OneTimeWorkRequest {
        val data = Data.Builder()
            .putString(ComputerStartingWorker.URL_STRING_KEY, "http://152.70.190.52:8081/wol/add?key=dshjfnsndvcoksjzdkjfPOWDSJFOISAJDGFDFJNBVKJVB&mac=")
            .build()


        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        return OneTimeWorkRequestBuilder<ComputerStartingWorker>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()
    }

    private fun jsonInfoReadingTask(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<JsonInfoReadingWorker>().build()
    }

    fun startWorkManager(context: Context) {
        val jsonTask = jsonInfoReadingTask()

        val workManager = WorkManager.getInstance(context)
        workManager
            .beginWith(startComputerTask())
            .then(jsonTask)
            .enqueue()

        //TODO избавиться от вечного просмотра
        workManager.getWorkInfoByIdLiveData(jsonTask.id).observeForever {
            if (it.state == WorkInfo.State.SUCCEEDED || it.state == WorkInfo.State.FAILED) {
                Toast.makeText(
                    context,
                    it.outputData.getInt(JsonInfoReadingWorker.RESULT_MASSAGE_KEY, R.string.json_troubles),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
