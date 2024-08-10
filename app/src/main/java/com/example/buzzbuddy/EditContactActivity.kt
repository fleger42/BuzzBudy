package com.example.buzzbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.buzzbuddy.data.UserDto
import com.example.buzzbuddy.db.BuzzBudyDatabase

class EditContactActivity : AppCompatActivity() {
    lateinit var db: BuzzBudyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        db = BuzzBudyDatabase(this)

        val firstNameView = findViewById<TextView>(R.id.edit_contact_firstname)
        val lastNameView = findViewById<TextView>(R.id.edit_contact_lastname)
        val phoneFieldView = findViewById<TextView>(R.id.edit_contact_phonefield)
        val editContactButton = findViewById<Button>(R.id.edit_contact_button)
        val errorView = findViewById<TextView>(R.id.edit_contact_error)

        firstNameView.text = intent.getStringExtra("first_name").toString()
        lastNameView.text = intent.getStringExtra("last_name").toString()
        phoneFieldView.text = intent.getStringExtra("phone").toString()
        val savePhoneNumber = phoneFieldView.text.toString()
        val changedUser = db.findUser(savePhoneNumber)
        val changedUserId = changedUser!!.id

        editContactButton.setOnClickListener {
            errorView.visibility = View.GONE
            errorView.text = ""
            val firstNameText = firstNameView.text.toString()
            val lastNameText = lastNameView.text.toString()
            val phoneFieldText = phoneFieldView.text.toString()

            if (firstNameText.isBlank() || lastNameText.isBlank() || phoneFieldText.isBlank()) {
                errorView.visibility = View.VISIBLE
                errorView.text = "Veuillez remplir tout les champs."
            } else if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneFieldText)) {
                errorView.visibility = View.VISIBLE
                errorView.text = "Veuillez entrer un numéro de téléphone valide."
            } else if (savePhoneNumber != phoneFieldText && db.findUser(phoneFieldText) != null) {
                errorView.visibility = View.VISIBLE
                errorView.text = "Vous avez déjà ajouté ce numéro de téléphone."
            } else {
                val user = UserDto(changedUserId, firstNameText, lastNameText, phoneFieldText)
                val isInserted = db.updateUser(user)

                if (isInserted)
                    Toast.makeText(this, "First name:$firstNameText Last name:$lastNameText Phone number:$phoneFieldText", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}