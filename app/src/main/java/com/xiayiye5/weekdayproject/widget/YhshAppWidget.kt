package com.xiayiye5.weekdayproject.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.xiayiye5.weekdayproject.MainActivity
import com.xiayiye5.weekdayproject.R
import com.xiayiye5.weekdayproject.service.WidgetService

/**
 * Implementation of App Widget functionality.
 */
class YhshAppWidget : AppWidgetProvider() {
    private val TAG = "YhshAppWidget"
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId)
            Log.d(TAG, "onUpdate")
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d(TAG, "onReceive")
    }

    override fun onEnabled(context: Context) {
        Log.d(TAG, "onEnabled")
        //桌面小部件可用，创建小部件的时候开启服务
        context.startService(Intent(context, WidgetService::class.java))
    }

    override fun onDisabled(context: Context) {
        Log.d(TAG, "onDisabled")
        //桌面小部件不可用的时候，被移除的时候停止服务
        context.stopService(Intent(context, WidgetService::class.java))
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.yhsh_app_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}