package com.example.buzzbuddy

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.buzzbuddy.adapter.ContactAdapter
import com.example.buzzbuddy.db.BuzzBudyDatabase

class HomeActivity : AppCompatActivity() {
    lateinit var db: BuzzBudyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        db = BuzzBudyDatabase(this)
        val color = ColorDrawable(db.getHeaderColor())
        supportActionBar!!.setBackgroundDrawable(color)

        val listContact = findViewById<ListView>(R.id.list_contact)
        var contactArray = db.getAllUsers()
        val adapter = ContactAdapter(this, R.layout.item_contact, contactArray)

        listContact.adapter = adapter

        listContact.setOnItemClickListener { adapterView, view, i, l ->
            if(askPermission()) {
                val clickContact = contactArray[i]
                Intent(this, ConversationActivity::class.java).also {
                    it.putExtra("first_name", clickContact.user_first_name)
                    it.putExtra("last_name", clickContact.user_last_name)
                    it.putExtra("phone", clickContact.user_phone)
                    startActivity(it)
                }
            }
        }
    }

    private fun hasPermission(permission: String, activity : Activity) =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

    private fun askPermission() : Boolean{
        val notGranted = listOf(
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.RECEIVE_SMS
        ).filterNot { hasPermission(it, this) }
        if (notGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, notGranted.toTypedArray(), 0)
        }
        return notGranted.isEmpty()
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
        }
        return true
    }
    override fun onBackPressed() {
        if (false) {
            super.onBackPressed();
        }
    }

}