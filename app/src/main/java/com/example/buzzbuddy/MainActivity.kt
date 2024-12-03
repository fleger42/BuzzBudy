package com.example.buzzbuddy

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.buzzbuddy.db.BuzzBudyDatabase

class MainActivity : AppCompatActivity() {

    lateinit var db: BuzzBudyDatabase

    private fun hideSystemBars() {
        val controller = WindowInsetsControllerCompat(
            window, window.decorView
        )

        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemBars()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        db = BuzzBudyDatabase(this)

        if(db.checkLogin() != null)
        {
            val intentToHomeActivity = Intent(this, HomeActivity::class.java)
            startActivity(intentToHomeActivity)
        }
        val buttonHome = findViewById<Button>(R.id.button_home)
        val phone = findViewById<EditText>(R.id.phone)
        val error = findViewById<TextView>(R.id.error_text)
        val phoneField = findViewById<TextView>(R.id.phone)

        buttonHome.setOnClickListener{
            error.visibility = View.GONE
            error.text = ""
            val txtPhone = phone.text.toString()
            if(txtPhone.isBlank())
            {
                error.visibility = View.VISIBLE
                error.text = getString(R.string.insert_phone)
            }
            else if(!PhoneNumberUtils.isGlobalPhoneNumber(phoneField.text.toString()) || txtPhone.length != 10)
            {
                error.visibility = View.VISIBLE
                error.text = getString(R.string.valid_phone)
            }
            else
            {
                db.firstLogin(phoneField.text.toString())
                val intentToHomeActivity = Intent(this, HomeActivity::class.java)
                phoneField.text = ""
                startActivity(intentToHomeActivity)
            }
        }
    }
}