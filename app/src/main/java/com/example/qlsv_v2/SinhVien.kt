package com.example.qlsv_v2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SinhVien")
data class SinhVien(
    var name:String,
    @PrimaryKey
    var mssv:String,
    var phonenumber:String,
    var email:String
)
