package com.example.qlsv_v2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val studentList = mutableListOf(
        SinhVien("Nguyễn Văn A", "SV001", "0123456789", "a@gmail.com"),
        SinhVien("Trần Thị B", "SV002", "0987654321", "b@gmail.com"),
        SinhVien("Lê Văn C", "SV003", "0111222333", "c@gmail.com")
    )

    private lateinit var listView: ListView
    private lateinit var adapter: SinhVienAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.studentList)

        adapter = SinhVienAdapter(this,studentList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            // Tạo PopupMenu với view của item làm view neo
            Toast.makeText(this,"Nut da dc bam",Toast.LENGTH_SHORT).show()
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

            // Xử lý sự kiện khi chọn item trong PopupMenu
            popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.update -> {
                        showUpdateDialog(position)
                        Toast.makeText(this, "Update thanh cong", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.delete -> {
                        studentList.removeAt(position)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "Xoa thanh cong", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.dial -> {
                        val phoneNumber = studentList[position].phonenumber
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                        startActivity(intent)
                        Toast.makeText(this, "Goi dien", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.email -> {
                        val email = studentList[position].email
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                        startActivity(intent)
                        Toast.makeText(this, "Email", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflate: MenuInflater = menuInflater
        inflate.inflate(R.menu.sv_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_sv -> {
                // Tạo AlertDialog
                val dialogBuilder = AlertDialog.Builder(this)

                // Nạp layout tùy chỉnh
                val inflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_add_sv, null)
                dialogBuilder.setView(dialogView)

                // Lấy các thành phần trong layout
                val nameEdt = dialogView.findViewById<EditText>(R.id.name)
                val mssvEdt = dialogView.findViewById<EditText>(R.id.mssv)
                val phonenumberEdt = dialogView.findViewById<EditText>(R.id.phonenumber)
                val emailEdt = dialogView.findViewById<EditText>(R.id.email)


                // Cấu hình dialog
                dialogBuilder.setTitle("Thêm sinh viên")
                    .setPositiveButton("Add") { dialog, which ->
                        // Xử lý khi nhấn Add
                        val name = nameEdt.text.toString()
                        val mssv = mssvEdt.text.toString()
                        val phoneNumber = phonenumberEdt.text.toString()
                        val email = emailEdt.text.toString()
                        studentList.add(SinhVien(name,mssv,phoneNumber,email))
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "Them sinh vien thanh cong!", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        // Xử lý khi nhấn Cancel
                        dialog.dismiss()
                    }

                // Tạo và hiển thị dialog
                dialogBuilder.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showUpdateDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_sv, null)
        dialogBuilder.setView(dialogView)

        // Lấy các thành phần
        val nameEdt = dialogView.findViewById<EditText>(R.id.name)
        val mssvEdt = dialogView.findViewById<EditText>(R.id.mssv)
        val phonenumberEdt = dialogView.findViewById<EditText>(R.id.phonenumber)
        val emailEdt = dialogView.findViewById<EditText>(R.id.email)

        // Điền thông tin hiện tại của sinh viên
        val student = studentList[position]
        nameEdt.setText(student.name)
        mssvEdt.setText(student.mssv)
        phonenumberEdt.setText(student.phonenumber)
        emailEdt.setText(student.email)

        dialogBuilder.setTitle("Sửa sinh viên")
            .setPositiveButton("Lưu") { dialog, which ->
                val name = nameEdt.text.toString().trim()
                val mssv = mssvEdt.text.toString().trim()
                val phoneNumber = phonenumberEdt.text.toString().trim()
                val email = emailEdt.text.toString().trim()

                // Kiểm tra dữ liệu (tương tự khi thêm)
                if (name.isEmpty()) {
                    nameEdt.error = "Vui lòng nhập tên"
                    return@setPositiveButton
                }
                if (mssv.isEmpty()) {
                    mssvEdt.error = "Vui lòng nhập MSSV"
                    return@setPositiveButton
                }
                if (phoneNumber.isEmpty() || !phoneNumber.matches("\\d{10,11}".toRegex())) {
                    phonenumberEdt.error = "Số điện thoại không hợp lệ"
                    return@setPositiveButton
                }
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEdt.error = "Email không hợp lệ"
                    return@setPositiveButton
                }

                // Cập nhật sinh viên
                studentList[position] = SinhVien(name, mssv, phoneNumber, email)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Hủy") { dialog, which -> dialog.dismiss() }
            .create()
            .show()
    }
}

