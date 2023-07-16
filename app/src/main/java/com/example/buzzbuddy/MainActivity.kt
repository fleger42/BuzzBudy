package com.example.buzzbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
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
                error.text = "Veuillez renseigner votre numéro de téléphone."
            }
            else
            {
                val intentToHomeActivity = Intent(this, HomeActivity::class.java)
                phoneField.text = ""
                startActivity(intentToHomeActivity)
            }
        }
    }
}