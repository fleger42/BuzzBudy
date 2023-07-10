package com.example.buzzbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonHome = findViewById<Button>(R.id.button_home)
        val phone = findViewById<EditText>(R.id.phone)
        val username = findViewById<EditText>(R.id.username)
        val error = findViewById<TextView>(R.id.error_text)

        buttonHome.setOnClickListener{
            error.visibility = View.GONE
            error.text = ""
            val txtPhone = phone.text.toString()
            val txtUsername = username.text.toString()
            if(txtPhone.isBlank() || txtUsername.isBlank())
            {
                error.visibility = View.VISIBLE
                error.text = "Veuillez remplir tout les champs"
            }
            else
            {
                val intentToHomeActivity = Intent(this, HomeActivity::class.java)
                startActivity(intentToHomeActivity)
            }
        }
    }
}