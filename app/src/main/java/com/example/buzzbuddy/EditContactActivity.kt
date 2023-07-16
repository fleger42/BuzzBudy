package com.example.buzzbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EditContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        val contactFirstNameView = findViewById<TextView>(R.id.edit_contact_firstname)
        val contactLastNameView = findViewById<TextView>(R.id.edit_contact_lastname)
        val contactPhoneView = findViewById<TextView>(R.id.edit_contact_phonefield)

        contactFirstNameView.text = intent.getStringExtra("first_name").toString()
        contactLastNameView.text = intent.getStringExtra("last_name").toString()
        contactPhoneView.text = intent.getStringExtra("phone").toString()
    }
}