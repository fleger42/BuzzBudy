package com.example.buzzbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val listContact = findViewById<ListView>(R.id.list_contact)
        val contactArray = arrayListOf("Contact 1", "contact 2", "Contact 3")
        var adapter = ContactAdapter(this, R.layout.item_contact, contactArray)

        listContact.adapter = adapter
    }
}