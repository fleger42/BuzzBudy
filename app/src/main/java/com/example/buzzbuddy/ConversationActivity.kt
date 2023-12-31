package com.example.buzzbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast

class ConversationActivity : AppCompatActivity() {
    private var firstName = String()
    private var lastName = String()
    private var phone = String()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        val contactNameView = findViewById<TextView>(R.id.contact_name)
        firstName = intent.getStringExtra("first_name").toString()
        lastName = intent.getStringExtra("last_name").toString()
        phone = intent.getStringExtra("phone").toString()

        contactNameView.text = firstName
        supportActionBar!!.title = phone
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.conversation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.contact_edit -> {
                Intent(this, EditContactActivity::class.java).also {
                    it.putExtra("first_name", firstName)
                    it.putExtra("last_name", lastName)
                    it.putExtra("phone", phone)
                    startActivity(it)
                }
            }
            R.id.contact_delete -> {
                Toast.makeText(this, "Need to put an alert confirmation", Toast.LENGTH_LONG).show()
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed();
                return true;
            }
        }
        return true
    }

}