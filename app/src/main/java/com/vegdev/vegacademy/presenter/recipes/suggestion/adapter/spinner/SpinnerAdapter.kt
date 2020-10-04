package com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.vegdev.vegacademy.helpers.utils.Utils

class SpinnerAdapter(context: Context, resource: Int, private val types: List<String>) :
    ArrayAdapter<String>(context, resource) {

    override fun getCount(): Int = types.size
    override fun getItem(position: Int): String? = types[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = Utils.getStringResourceByName(types[position], context)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = Utils.getStringResourceByName(types[position], context)
        return view
    }
}