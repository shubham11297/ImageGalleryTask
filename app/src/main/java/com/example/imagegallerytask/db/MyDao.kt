package com.example.imagegallerytask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imagegallerytask.data.ImagesDataItem

@Dao
abstract class ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: List<ImagesDataItem>)

    @Query("DELETE FROM ImageTable")
    abstract fun delete()

    @Query("SELECT * from ImageTable")
    @JvmSuppressWildcards
    abstract fun getAllData(): List<ImagesDataItem>
}
