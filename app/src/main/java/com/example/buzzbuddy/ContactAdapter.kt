package com.example.buzzbuddy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ContactAdapter(
    var mcontext: Context,
    var ressources: Int,
    var values: ArrayList<ContactDto>
): ArrayAdapter<ContactDto>(mcontext, ressources, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val contact = values[position]
        val view = LayoutInflater.from(mcontext).inflate(ressources, parent, false)

        val firstName = view.findViewById<TextView>(R.id.itemContact_firstName)
        var lastName = view.findViewById<TextView>(R.id.itemContact_lastName)
        var image = view.findViewById<ImageView>(R.id.itemContact_image)
        var phone = view.findViewById<TextView>(R.id.itemContact_phone)

        firstName.text = contact.firstName
        lastName.text = contact.lastName
        image.setImageResource(contact.image)
        phone.text = contact.phone
        return view
    }
}