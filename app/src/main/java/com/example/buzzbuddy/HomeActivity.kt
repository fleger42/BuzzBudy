package com.example.buzzbuddy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val listContact = findViewById<ListView>(R.id.list_contact)
        val contactArray = arrayListOf("Contact 1", "contact 2", "Contact 3")
        val adapter = ContactAdapter(this, R.layout.item_contact, contactArray)

        listContact.adapter = adapter

        listContact.setOnItemClickListener { adapterView, view, i, l ->
            val clickContact = contactArray[i]
            Intent(this, ConversationActivity::class.java).also {
                it.putExtra("contact_name", clickContact)
                startActivity(it)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.conversation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val intentToAddContactActivity = Intent(this, AddContactActivity::class.java)
                startActivity(intentToAddContactActivity)
            }
            R.id.item_option -> {
                val intentToOptionActivity = Intent(this, OptionActivity::class.java)
                startActivity(intentToOptionActivity)
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed();
                return true;
            }
        }
        return true
    }
}