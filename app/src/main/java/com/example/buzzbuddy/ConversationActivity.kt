package com.example.buzzbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ConversationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        val contactNameView = findViewById<TextView>(R.id.contact_name)
        val contactName = intent.getStringExtra("contact_name")

        contactNameView.text = contactName
        supportActionBar!!.title = contactName
    }
}