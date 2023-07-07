package com.example.buzzbuddy

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
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val error = findViewById<TextView>(R.id.error_text)

        button_home.setOnClickListener(View.OnClickListener {
            error.visibility = View.INVISIBLE
            error.text = ""
            var txtEmail = email.text.toString()
            var txtPassword = password.text.toString()
            if(txtEmail.isBlank() || txtPassword.isBlank())
            {
                error.visibility = View.VISIBLE
                error.text = "Veuillez remplir tout les champs"
            }
            else
            {
                Toast.makeText(this, "$txtEmail et $txtPassword", Toast.LENGTH_LONG).show()
            }
        })
    }
}