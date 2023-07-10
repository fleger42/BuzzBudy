package com.example.buzzbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
    }
}