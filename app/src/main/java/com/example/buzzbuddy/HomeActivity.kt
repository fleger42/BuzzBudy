package com.example.buzzbuddy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    private var contactArray = ArrayList<ContactDto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val listContact = findViewById<ListView>(R.id.list_contact)
        var contactArray = arrayListOf(
            ContactDto("Florian", "Leger", R.drawable.buzzbuddy_logo, "0767874585"),
            ContactDto("Justine", "Giroix", R.drawable.buzzbuddy_logo, "0767874585"),
            ContactDto("Paul","Correia", R.drawable.buzzbuddy_logo, "0767874585"),
            ContactDto("Hugo", "Lentin", R.drawable.buzzbuddy_logo, "0767874585"),
            ContactDto("Anis", "Remili", R.drawable.buzzbuddy_logo, "0767874585"),
            ContactDto("Amine", "Doghmane", R.drawable.buzzbuddy_logo, "0767874585"),
            ContactDto("Charli", "Huchard", R.drawable.buzzbuddy_logo, "0767874585")
        )
        val adapter = ContactAdapter(this, R.layout.item_contact, contactArray)

        listContact.adapter = adapter

        listContact.setOnItemClickListener { adapterView, view, i, l ->
            val clickContact = contactArray[i]
            Intent(this, ConversationActivity::class.java).also {
                it.putExtra("first_name", clickContact.firstName)
                it.putExtra("last_name", clickContact.lastName)
                it.putExtra("phone", clickContact.phone)
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