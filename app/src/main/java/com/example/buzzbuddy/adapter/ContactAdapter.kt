package com.example.buzzbuddy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.buzzbuddy.R
import com.example.buzzbuddy.data.UserDto

class ContactAdapter(
    var mcontext: Context,
    var ressources: Int,
    var values: ArrayList<UserDto>
): ArrayAdapter<UserDto>(mcontext, ressources, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val contact = values[position]
        val view = LayoutInflater.from(mcontext).inflate(ressources, parent, false)

        val firstName = view.findViewById<TextView>(R.id.itemContact_firstName)
        var lastName = view.findViewById<TextView>(R.id.itemContact_lastName)
        var image = view.findViewById<ImageView>(R.id.itemContact_image)
        var phone = view.findViewById<TextView>(R.id.itemContact_phone)

        firstName.text = contact.user_first_name
        lastName.text = contact.user_last_name
        phone.text = contact.user_phone
        return view
    }
}