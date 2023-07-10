package com.example.buzzbuddy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        val listContact = findViewById<ListView>(R.id.list_contact)
        val contactArray = arrayListOf("Contact 1", "contact 2", "Contact 3")
        var adapter = ContactAdapter(this, R.layout.item_contact, contactArray)

        listContact.adapter = adapter

        listContact.setOnItemClickListener { adapterView, view, i, l ->
            val clickContact = contactArray[i]
            Intent(this, ConversationActivity::class.java).also {
                it.putExtra("contact_name", clickContact)
                println("test")

                startActivity(it)
                Toast.makeText(this, "option", Toast.LENGTH_LONG)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.conversation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        print("test")
        when (item.itemId) {
            R.id.item_add -> {
                Toast.makeText(this, "add", Toast.LENGTH_LONG).show()
            }
            R.id.item_option -> {
                Toast.makeText(this, "option", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}