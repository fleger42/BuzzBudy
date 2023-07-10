package com.example.buzzbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val firstNameView = findViewById<EditText>(R.id.add_contact_firstname)
        val lastNameView = findViewById<EditText>(R.id.add_contact_lastname)
        val phoneFieldView = findViewById<EditText>(R.id.add_contact_phonefield)
        val addContactButton = findViewById<Button>(R.id.add_contact_button)
        val errorView = findViewById<TextView>(R.id.add_contact_error)

        addContactButton.setOnClickListener{
            errorView.visibility = View.GONE
            errorView.text = ""
            val firstNameText = firstNameView.text.toString()
            val lastNameText = lastNameView.text.toString()
            val phoneFieldText = phoneFieldView.text.toString()

            if (firstNameText.isBlank() || lastNameText.isBlank() || phoneFieldText.isBlank())
            {
                errorView.visibility = View.VISIBLE
                errorView.text = "Veuillez remplir tout les champs."
            }
            else if(!PhoneNumberUtils.isGlobalPhoneNumber(phoneFieldText))
            {
                errorView.visibility = View.VISIBLE
                errorView.text = "Veuillez entrer un numéro de téléphone valide."
            }
            else
            {
                Toast.makeText(this, "First name:$firstNameText Last name:$lastNameText Phone number:$phoneFieldText", Toast.LENGTH_LONG).show()
            }
        }
    }
}