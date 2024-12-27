package com.example.listpj

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.listpj.R

/* 原始內容 */
/*
class MyAdapter(
    context: Context,
    private val layout: Int,
    data: List<item> ): ArrayAdapter<item>(context, layout, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view
        val photo = view.findViewById<ImageView>(R.id.imgPhoto)
        photo.setImageResource(item.photo)
        val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }
        return view
    }
}
*/

/* 小考內容 */
class MyAdapter(
    context: Context,
    private val layout: Int,
    data: List<item>
) : ArrayAdapter<item>(context, layout, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view

        val photo = view.findViewById<ImageView>(R.id.imgPhoto)
        photo.setImageResource(item.photo)

        val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = item.name

        return view
    }
}