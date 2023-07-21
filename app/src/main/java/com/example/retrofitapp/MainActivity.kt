package com.example.retrofitapp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitapp.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv.movementMethod = ScrollingMovementMethod()

        val retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getAlbums()
            val response2 = retrofitService.getSpecificAlbums(6)
            emit(response2)
        }

        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()

            if(albumsList != null){
                while (albumsList.hasNext()){
                    val albumItem = albumsList.next()
                    Log.i("TAGY", albumItem.title)
                    val result = "Album Title: ${albumItem.title} \n"
                    binding.tv.append(result)
                }
            }
        })



    }
}