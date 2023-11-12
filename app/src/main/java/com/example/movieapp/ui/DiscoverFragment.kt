package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.adapter.GenreAdapter
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentDiscoverBinding
import com.example.movieapp.model.genre.Genres
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.network.MovieServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverFragment : Fragment() {
    lateinit var binding: FragmentDiscoverBinding
    lateinit var genres: Genres
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnClickListener {
            val intent = Intent(this@DiscoverFragment.context,SearchMoviesActivity::class.java)
            startActivity(intent)
        }

        MovieServices.api.getGenreMovies().enqueue(object : Callback<Genres>{
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                if(response.isSuccessful){
                    genres = response.body()!!
                    loadRcvMovie(genres.genres[0].id)
                    binding.rcvGenres.adapter = GenreAdapter(genres){
                        loadRcvMovie2(it.id)
                    }

                }
                else{
                    Toast.makeText(this@DiscoverFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Genres>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.toString()}", )
            }

        })
    }
    fun loadRcvMovie(id:Int){
        MovieServices.api.getMoviesByGenreId(genreId=id).enqueue(object : Callback<Movies>{
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if(response.isSuccessful){
                    val movies = response.body()!!
                    binding.rcvMovies.adapter  = MovieAdapter(movies){
                        val intent = Intent(this@DiscoverFragment.context,MovieDetailActivity::class.java)
                            .putExtra("id",it)
                        startActivity(intent)
                    }
                }
                else{
                    Toast.makeText(this@DiscoverFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message}", t)
            }

        })

    }
    fun loadRcvMovie2(id:Int) {
        MovieServices.api.getMoviesByGenreId(genreId = id).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful) {
                    val movies = response.body()!!
                    (binding.rcvMovies.adapter as MovieAdapter).updateData(movies)
                } else {
                    Toast.makeText(this@DiscoverFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.e("ERROR", "onFailure: ${t.message}", t)
            }

        })
    }

}