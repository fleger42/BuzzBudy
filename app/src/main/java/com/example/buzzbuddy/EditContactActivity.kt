package com.example.buzzbuddy

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.buzzbuddy.data.UserDto
import com.example.buzzbuddy.db.BuzzBudyDatabase

class EditContactActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_edit_contact)
        db = BuzzBudyDatabase(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val color = ColorDrawable(db.getHeaderColor())
        supportActionBar!!.setBackgroundDrawable(color)

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
                errorView.text = getString(R.string.empty_field)

            }
            else if(firstNameText.length >= 12 || lastNameText.length >= 12 || phoneFieldText.length != 10)
            {
                errorView.visibility = View.VISIBLE
                errorView.text = getString(R.string.invalid_length)
            }
            else if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneFieldText)) {
                errorView.visibility = View.VISIBLE
                errorView.text = getString(R.string.valid_phone)
            } else if (savePhoneNumber != phoneFieldText && db.findUser(phoneFieldText) != null) {
                errorView.visibility = View.VISIBLE
                errorView.text = getString(R.string.already_added_phone)
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