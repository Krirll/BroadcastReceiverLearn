package ru.krirll.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            "loaded" -> {
                val percent = intent.getIntExtra("percent", 0)
                Toast.makeText(context, "Progress = $percent%", Toast.LENGTH_SHORT).show()
            }
            ACTION_CLICKED -> {
                val click = intent.getIntExtra(CLICK_COUNT, 0)
                Toast.makeText(context, "Clicked $click", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                val turnedOn = intent.getBooleanExtra("state", false)
                Toast.makeText(
                    context,
                    "airplane mode changed. Turned on: $turnedOn",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(context, "Battery low", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        const val ACTION_CLICKED = "clicked"
        private const val CLICK_COUNT = "count"


        fun newIntent(click: Int) = Intent().apply {
            putExtra(CLICK_COUNT, click)
            action = ACTION_CLICKED
        }
    }
}