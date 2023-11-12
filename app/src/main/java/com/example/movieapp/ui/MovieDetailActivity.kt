package com.example.movieapp.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
                        binding.playTrailer.visibility = View.GONE
                        binding.youTubePlayerView.visibility = View.VISIBLE
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
                    binding.youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }else{
                    Toast.makeText(this@MovieDetailActivity, "Looix", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TrailerList>, t: Throwable) {
                TODO("Not yet implemented")
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