package com.example.buzzbuddy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.buzzbuddy.adapter.ContactAdapter
import com.example.buzzbuddy.data.UserDto
import com.example.buzzbuddy.db.BuzzBudyDatabase

class HomeActivity : AppCompatActivity() {
    lateinit var db: BuzzBudyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        db = BuzzBudyDatabase(this)

        val listContact = findViewById<ListView>(R.id.list_contact)
        var contactArray = db.getAllUsers()
        val adapter = ContactAdapter(this, R.layout.item_contact, contactArray)

        listContact.adapter = adapter

        listContact.setOnItemClickListener { adapterView, view, i, l ->
            val clickContact = contactArray[i]
            Intent(this, ConversationActivity::class.java).also {
                it.putExtra("first_name", clickContact.user_first_name)
                it.putExtra("last_name", clickContact.user_last_name)
                it.putExtra("phone", clickContact.user_phone)
                startActivity(it)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                Intent(this, AddContactActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.item_option -> {
                Intent(this, OptionActivity::class.java).also {
                    startActivity(it)
                }
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed();
                return true;
            }
        }
        return true
    }
}