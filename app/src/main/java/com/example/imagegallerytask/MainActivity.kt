package com.example.imagegallerytask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallerytask.adapter.RecyclerImagesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private var imagesAdapter: RecyclerImagesAdapter? = null

    var isLoading: Boolean = false
    private var visible = 4
    private val fullList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.fetchData()

        viewModel.imagesRetrieved.observe(this, Observer {
            if (it){
                setRecyclerView(viewModel.imagesList)
            }
        })
    }

    private fun setRecyclerView(imagesList: MutableList<String>) {
        fullList.addAll(imagesList)
        val list = imagesList.subList(0, visible)
        imagesAdapter = RecyclerImagesAdapter(this, imagesList)
        val layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = imagesAdapter
        recycler_view.layoutManager = layoutManager
        recycler_view.setHasFixedSize(true)
        recycler_view.setItemViewCacheSize(50)

//        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                val visibleItemCount = layoutManager.childCount
//                val totalItemCount = layoutManager.itemCount
//                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//                if (!isLoading) {
//                    if (firstVisibleItemPosition + visibleItemCount >= totalItemCount && firstVisibleItemPosition >= 0) {
//                        updateData()
//                    }
//                }
//            }
//        })
    }

    fun updateData(){
        isLoading = true
        val newList = fullList.subList(visible, visible + 5)
        visible += 5
        imagesAdapter?.updateRecycler(newList)
        isLoading = false
    }
}