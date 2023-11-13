package com.example.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.adapter.ListMovieAdapter
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.ActivitySearchMoviesBinding
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.network.MovieServices
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewModel.MovieViewModel
import com.example.movieapp.viewModel.MovieViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMoviesBinding
    private lateinit var movieViewModel: MovieViewModel
    private var flag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(MovieRepository()))[MovieViewModel::class.java]
        binding.btnBack.setOnClickListener { finish() }
        binding.btnClear.setOnClickListener {
            binding.searchView.text.clear()
        }
        movieViewModel.searchMovies("")
        movieViewModel.searchedMovies.observe(this){movies->
            if(flag){
                binding.rcvSearchMovies.adapter = MovieAdapter(movies){
                    val intent = Intent(this@SearchMoviesActivity,MovieDetailActivity::class.java)
                        .putExtra("id",it)
                    startActivity(intent)
                }
                flag=false
            }
            else{
                (binding.rcvSearchMovies.adapter as MovieAdapter).updateData(movies)
            }
        }
        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isEmpty()){
                    binding.btnClear.visibility = View.GONE
                }
                else{
                    binding.btnClear.visibility = View.VISIBLE
                }

                if(binding.searchView.text.isEmpty()){
                    binding.rcvSearchMovies.visibility = View.GONE
                }else{
                    binding.rcvSearchMovies.visibility = View.VISIBLE
                    movieViewModel.searchMovies(binding.searchView.text.toString())
                }
            }

        })

    }
}