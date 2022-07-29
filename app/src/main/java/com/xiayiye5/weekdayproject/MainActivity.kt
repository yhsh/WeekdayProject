package com.xiayiye5.weekdayproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvTime = findViewById<TextView>(R.id.tv_time)
        val intent = intent.extras
        if (null != intent) {
            val time = intent.getString("time")
            tvTime.text = time
        }
    }
}