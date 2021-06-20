package com.example.imagegallerytask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.imagegallerytask.utils.Coroutines

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MainRepository = MainRepository(application)
    val imagesList: MutableList<String> = mutableListOf()
    var imagesRetrieved = MutableLiveData<Boolean>().apply { postValue(false) }

    fun fetchData(){
        Coroutines.io {
            val response = getImagesData()
            if (response!= null){
                response.forEach {
                    imagesList.add(it.download_url) }
                imagesRetrieved.postValue(true)
            }

        }
    }

    fun getDataFromDb(){
        Coroutines.io {
            repository.db.imageDao.getAllData().forEach {
                imagesList.add(it.download_url)
            }
            imagesRetrieved.postValue(true)
        }
    }

    private suspend fun getImagesData() = repository.getImagesData()
}