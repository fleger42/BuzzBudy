package com.example.buzzbuddy

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class OptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

        val cdDark = ColorDrawable(ContextCompat.getColor(this, R.color.DarkBlueGreen))
        val cdLight = ColorDrawable(ContextCompat.getColor(this, R.color.BlueGreen))

        var switchView = findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switch_header)

        switchView.setOnCheckedChangeListener { compoundButton, b ->
            if(b)
                supportActionBar!!.setBackgroundDrawable(cdDark)
            else
                supportActionBar!!.setBackgroundDrawable(cdLight)
        }
    }
}