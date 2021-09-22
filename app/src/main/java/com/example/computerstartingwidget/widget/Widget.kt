package com.example.computerstartingwidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.computerstartingwidget.R
import com.example.computerstartingwidget.workers.StartWorkerTasks.startWorkManager

class Widget : AppWidgetProvider() {

    private val SYNC_CLICKED = "WidgetImageClick"

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val remoteViews = RemoteViews(context?.packageName, R.layout.widget)
        val componentName = ComponentName(context!!, Widget::class.java)

        remoteViews.setImageViewResource(R.id.imageView, R.drawable.power)
        remoteViews.setOnClickPendingIntent(R.id.imageView, getPendingSelfIntent(context, SYNC_CLICKED))
        appWidgetManager?.updateAppWidget(componentName, remoteViews)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (SYNC_CLICKED == intent?.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)

            val remoteViews = RemoteViews(context!!.packageName, R.layout.widget)

            val componentName = ComponentName(context, Widget::class.java)
            remoteViews.setImageViewResource(R.id.imageView, R.drawable.power)
            remoteViews.setOnClickPendingIntent(R.id.imageView, getPendingSelfIntent(context, SYNC_CLICKED))

            /*
            val intentClick = Intent(context, MainActivity::class.java)

            intentClick.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MainActivity.startedFromWidget = true
            context.startActivity(intentClick)

             */

            startWorkManager(context)

            appWidgetManager?.updateAppWidget(componentName, remoteViews)

        }
    }


    private fun getPendingSelfIntent(context: Context?, action: String) : PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0 , intent, 0)
    }
}