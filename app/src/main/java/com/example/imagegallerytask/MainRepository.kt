package com.example.imagegallerytask

import android.app.Application
import com.example.imagegallerytask.data.ImagesDataItem
import com.example.imagegallerytask.db.MyDatabase
import com.example.imagegallerytask.network.ApiServices

class MainRepository(application: Application) {

    val api = ApiServices()
    val db = MyDatabase.getInstance(application.applicationContext)

    suspend fun getImagesData(): List<ImagesDataItem>? {
       val response =  api.getImagesData()
        if (response.isSuccessful){
            db.imageDao.insert(response.body()!!)
        }
        return response.body()
    }

}