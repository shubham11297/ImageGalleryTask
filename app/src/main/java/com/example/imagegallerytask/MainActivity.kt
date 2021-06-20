package com.example.imagegallerytask

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        if (isNetworkConnected(this)) {
            viewModel.fetchData()
        } else {
            viewModel.getDataFromDb()
        }

        viewModel.imagesRetrieved.observe(this, Observer {
            if (it) {
                setRecyclerView(viewModel.imagesList)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater.inflate(R.menu.main_menu, menu)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            menu!!.findItem(R.id.action_dark).isChecked = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dark -> {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView(imagesList: MutableList<String>) {
        fullList.addAll(imagesList)
//        val list = imagesList.subList(0, visible)
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

    fun updateData() {
        isLoading = true
        val newList = fullList.subList(visible, visible + 5)
        visible += 5
        imagesAdapter?.updateRecycler(newList)
        isLoading = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
            }
        }
        return false
    }
}

// Link - https://github.com/shubham11297/ImageGalleryTask