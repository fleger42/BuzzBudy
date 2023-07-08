package com.example.buzzbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_home = findViewById<Button>(R.id.button_home)
        val create_account = findViewById<TextView>(R.id.create_account)
        val phone = findViewById<EditText>(R.id.phone)
        val username = findViewById<EditText>(R.id.username)
        val error = findViewById<TextView>(R.id.error_text)

        button_home.setOnClickListener(View.OnClickListener {
            error.visibility = View.GONE
            error.text = ""
            var txtPhone = phone.text.toString()
            var txtUsername = username.text.toString()
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
        })
    }
}