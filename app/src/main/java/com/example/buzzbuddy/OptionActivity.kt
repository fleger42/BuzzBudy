package com.example.buzzbuddy

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.buzzbuddy.db.BuzzBudyDatabase

class OptionActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_option)
        db = BuzzBudyDatabase(this)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        val actualcolor = ColorDrawable(db.getHeaderColor())
        supportActionBar!!.setBackgroundDrawable(actualcolor)
        val aqua = Color.parseColor("aqua")
        val olive = Color.parseColor("olive")

        val aquaDrawable = ColorDrawable(Color.parseColor("aqua"))
        val oliveDrawable = ColorDrawable(Color.parseColor("olive"))
        var switchView = findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.switch_header)

        switchView.setOnCheckedChangeListener { compoundButton, b ->
            if(b && db.getHeaderColor() == aqua)
            {
                supportActionBar!!.setBackgroundDrawable(oliveDrawable)
                db.editColor(olive)
            }
            else if (b)
            {
                supportActionBar!!.setBackgroundDrawable(aquaDrawable)
                db.editColor(aqua)
            }
            else
            {
                supportActionBar!!.setBackgroundDrawable(oliveDrawable)
                db.editColor(olive)
            }
        }
    }

}