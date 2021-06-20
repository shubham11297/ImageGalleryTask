package com.example.imagegallerytask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagegallerytask.data.ImagesDataItem


@Database(entities = [ImagesDataItem::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract val imageDao: ImageDao

    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "database_name"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}