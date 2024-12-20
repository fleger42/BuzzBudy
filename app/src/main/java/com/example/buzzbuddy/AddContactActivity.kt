package com.example.buzzbuddy

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.buzzbuddy.data.UserDto
import com.example.buzzbuddy.db.BuzzBudyDatabase

class AddContactActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_add_contact)
        db = BuzzBudyDatabase(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val color = ColorDrawable(db.getHeaderColor())
        supportActionBar!!.setBackgroundDrawable(color)
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

            if (firstNameText.isBlank() || lastNameText.isBlank() || phoneFieldText.isBlank()) {
                errorView.visibility = View.VISIBLE
                errorView.text = getString(R.string.empty_field)
            }
            else if(firstNameText.length >= 12 || lastNameText.length >= 12 || phoneFieldText.length != 10)
            {
                errorView.visibility = View.VISIBLE
                errorView.text = getString(R.string.invalid_length)
            }
            else if(!PhoneNumberUtils.isGlobalPhoneNumber(phoneFieldText))
            {
                errorView.visibility = View.VISIBLE
                errorView.text = getString(R.string.valid_phone)
            }
            else if(db.findUser(phoneFieldText) != null)
            {
                errorView.visibility = View.VISIBLE
                errorView.text = getString(R.string.already_added_phone)
            }
            else
            {
                val user = UserDto(firstNameText, lastNameText, phoneFieldText)
                db.addUser(user)

                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}