package com.example.movieapp.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapter.GenreOfMovieAdapter
import com.example.movieapp.adapter.RecommendAdapter
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.model.movieDetail.Movie
import com.example.movieapp.model.recomendMovies.Movies
import com.example.movieapp.model.trailerMovie.TrailerList
import com.example.movieapp.network.MovieServices
import com.example.movieapp.utils.Constrain.BASE_URL_IMAGE
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra("id",0)
        binding.btnBack.setOnClickListener { finish() }



        MovieServices.api.getDetailMovieById(id).enqueue(object : Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    loadData(response.body())
                    binding.playTrailer.setOnClickListener {
                        showVideo(response.body()!!.id)
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.toString()}", )
            }

        })
    }

    private fun showVideo(id: Int) {
        MovieServices.api.getListTrailer(id).enqueue(object : Callback<TrailerList>{
            override fun onResponse(call: Call<TrailerList>, response: Response<TrailerList>) {
                if(response.isSuccessful){
                    val trailers = response.body()
                    var videoId:String = ""
                    for (vd in trailers!!.results){
                        if(vd.site=="Trailer" || vd.official){
                            videoId = vd.key
                            break
                        }
                    }
                    //option1(videoId)
                    option2(videoId)

                }else{
                    Toast.makeText(this@MovieDetailActivity, "Looix", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TrailerList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun option1(id:String){
        binding.playTrailer.visibility = View.GONE
        binding.webView.visibility = View.VISIBLE
        val html2 = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/QLM-0vkckG4?si=KaavQxIjquanM5H8\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        val html = "<iframe style=\"width: 100%; height: 100%; margin: 0; padding: 0;\" src=\"https://www.youtube.com/embed/$id\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadData(html, "text/html", "utf-8")
    }
    fun option2(id:String){
        binding.playTrailer.visibility = View.GONE
        binding.youTubePlayerView.visibility = View.VISIBLE
        binding.youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(id, 0f)
            }
        })
    }


    private fun loadData(movie: Movie?) {
        Glide.with(binding.imgImage)
            .load(BASE_URL_IMAGE+movie!!.poster_path)
            .into(binding.imgImage)
        binding.txtTileMovie.text = movie.title
        binding.txtIMDb.text = "${movie.vote_average} IMDb"
        binding.txtOverView.text = movie.overview
        binding.txtTime.text = "${movie.runtime} minutes"
        binding.txtreleaseDate.text = movie.release_date
        binding.rcvListGenre.adapter = GenreOfMovieAdapter(movie.genres)
        MovieServices.api.getRecommendMovieById(id=movie.id)
            .enqueue(object : Callback<Movies>{
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    if(response.isSuccessful){
                        binding.rcvRecommendationsMovie.adapter = RecommendAdapter(response.body()!!){
                            val intent = Intent(this@MovieDetailActivity,MovieDetailActivity::class.java)
                                .putExtra("id",it)
                            startActivity(intent)
                        }
                    }
                }

                override fun onFailure(call: Call<Movies>, t: Throwable) {
                    Log.e("ERROR", "onFailure: ${t.toString()}", )
                }

            })
    }
}