package com.example.qlsv_v2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SinhVien::class],version = 1)
abstract class DatabaseSinhVien: RoomDatabase() {
    abstract fun sinhVienDao(): SinhVienDAO
    companion object {
        @Volatile
        private var INSTANCE: DatabaseSinhVien? = null
        fun getInstance(context: Context): DatabaseSinhVien {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseSinhVien::class.java, "sinhvien_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}