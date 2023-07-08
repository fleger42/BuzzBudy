package com.example.buzzbuddy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(
    var mcontext: Context,
    var ressources: Int,
    var values: ArrayList<String>
): ArrayAdapter<String>(mcontext, ressources, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val newName = values[position]
        val view = LayoutInflater.from(mcontext).inflate(ressources, parent, false)
        val contactName = view.findViewById<TextView>(R.id.contact_name)

        contactName.text = newName
        return view
    }
}