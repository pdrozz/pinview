package com.pdrozz.pinviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.pdrozz.view.PinView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val pin = findViewById<PinView>(R.id.pinview)
        button.setOnClickListener {
            pin.setTextVisible(!pin.isTextVisible())
        }
    }
}