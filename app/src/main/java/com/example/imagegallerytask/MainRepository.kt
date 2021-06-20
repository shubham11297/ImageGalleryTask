package com.example.imagegallerytask

import android.app.Application
import com.example.imagegallerytask.network.ApiServices

class MainRepository(application: Application) {

    val api = ApiServices()

    suspend fun getImagesData() = api.getImagesData()

}