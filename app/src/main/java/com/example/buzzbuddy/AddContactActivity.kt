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

class AddContactActivity : AppCompatActivity() {
    lateinit var db: BuzzBudyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        db = BuzzBudyDatabase(this)
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
            else if(db.findUser(phoneFieldText) != null)
            {
                errorView.visibility = View.VISIBLE
                errorView.text = "Vous avez déjà ajouté ce numéro de téléphone."
            }
            else
            {
                val user = UserDto(firstNameText, lastNameText, phoneFieldText)
                val isInserted = db.addUser(user)

                if(isInserted)
                    Toast.makeText(this, "Insert success", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}