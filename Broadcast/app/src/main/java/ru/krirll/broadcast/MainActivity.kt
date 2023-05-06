package ru.krirll.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    /*private val receiver by lazy {
        MyReceiver()
    }*/

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    private val receiverProgress = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra("percent", 0)
                progressBar.setProgress(percent, true)
            }
        }

    }

    private var click = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress)
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = MyReceiver.newIntent(click++)
            localBroadcastManager.sendBroadcast(intent)
        }
    }

    override fun onStart() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(MyReceiver.ACTION_CLICKED)
            addAction("loaded")
        }
        //это динамическая регистрация ресивера
        //можно статически зарегистрировать ресивер в манифесте
        //минус его в том, что статически зарег. ресивер не реагирует на большинство action'ов
        localBroadcastManager.registerReceiver(/*receiver*/ receiverProgress, filter) //динамическая регистрация

        startService(Intent(this, MyService::class.java))
        super.onStart()
    }

    override fun onStop() {
        localBroadcastManager.unregisterReceiver(receiverProgress)
        super.onStop()
    }
}