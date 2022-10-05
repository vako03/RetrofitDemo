package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retService: AlbumService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retService:AlbumService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getAlbums()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList!=null){
                while (albumList.hasNext()){
                    val albumItem = albumList.next()
                    val result:String = " "+"Album title : ${albumItem.title}"+"\n" +
                                        " "+"Album id : ${albumItem.id}"+"\n" +
                                        " "+"User id : ${albumItem.userId}"+"\n\n\n"
                    binding.textView.append(result)


                }
            }
        })
    }
}