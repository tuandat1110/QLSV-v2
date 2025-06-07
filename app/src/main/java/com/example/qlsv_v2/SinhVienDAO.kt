package com.example.qlsv_v2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// File: SinhVienDAO.kt (trong thư mục com.example.qlsv_v2)
@Dao
interface SinhVienDAO {
    @Insert
    fun addSinhVien(sinhVien: SinhVien)

    @Delete
    fun delSinhVien(sinhVien: SinhVien)

    @Update
    fun updateSinhVien(sinhVien: SinhVien)

    @Query("SELECT * FROM SinhVien")
    fun getAllSinhVien(): List<SinhVien>
}
