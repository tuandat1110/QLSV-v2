package com.example.qlsv_v2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView

class SinhVienAdapter(private val context: Context, private val data: MutableList<SinhVien>) : BaseAdapter() {

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.thong_tin_sinh_vien, parent, false)

        val name = view.findViewById<EditText>(R.id.name)
        val mssv = view.findViewById<EditText>(R.id.mssv)
        val phoneNumber = view.findViewById<EditText>(R.id.phonenumber)
        val email = view.findViewById<EditText>(R.id.email)

        val sv = data[position]
        name.setText(sv.name)
        mssv.setText(sv.mssv)
        phoneNumber.setText(sv.phonenumber)
        email.setText(sv.email)

        return view
    }
}
