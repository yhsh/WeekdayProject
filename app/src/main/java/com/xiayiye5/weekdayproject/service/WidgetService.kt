package com.xiayiye5.weekdayproject.service

import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import com.xiayiye5.weekdayproject.MainActivity
import com.xiayiye5.weekdayproject.R
import com.xiayiye5.weekdayproject.widget.YhshAppWidget
import java.text.SimpleDateFormat
import java.util.*

class WidgetService : Service() {
    private val TAG = "WidgetService"
    val appWidgetManager: AppWidgetManager by lazy { AppWidgetManager.getInstance(applicationContext) }
    val componentName by lazy { ComponentName(applicationContext, YhshAppWidget::class.java) }
    val remoteView by lazy { RemoteViews(applicationContext.packageName, R.layout.yhsh_app_widget) }
    private val bundle by lazy { Bundle() }
    private val countDownTimer = object : CountDownTimer(60000L, 1_00) {
        override fun onTick(p0: Long) {
            Log.d(TAG, "onTick:$p0")
            val date = Date()
            val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA)
            val time = simpleDateFormat.format(date)
            //设置文本信息
            remoteView.setTextViewText(R.id.appwidget_text, time)
            bundle.putString("time", time)
            //为了下带参数必须这样处理每次都设置click
            val pendingIntent =
                PendingIntent.getActivity(
                    applicationContext,
                    0,
                    Intent(applicationContext, MainActivity::class.java).putExtras(bundle),
                    PendingIntent.FLAG_UPDATE_CURRENT, bundle
                )
            remoteView.setOnClickPendingIntent(R.id.bt_click_me, pendingIntent)
            //更新小部件信息
            appWidgetManager.updateAppWidget(componentName, remoteView)
        }

        override fun onFinish() {
            //继续定时
            start()
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        countDownTimer.start()
        //点击事件
        /*val pendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                0,
                Intent(applicationContext, MainActivity::class.java).putExtras(bundle),
                PendingIntent.FLAG_UPDATE_CURRENT, bundle
            )
        remoteView.setOnClickPendingIntent(R.id.bt_click_me, pendingIntent)*/
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        countDownTimer.cancel()
    }
}