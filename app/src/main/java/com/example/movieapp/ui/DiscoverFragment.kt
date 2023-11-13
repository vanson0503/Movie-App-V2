package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.adapter.GenreAdapter
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentDiscoverBinding
import com.example.movieapp.model.genre.Genres
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.network.MovieServices
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewModel.MovieViewModel
import com.example.movieapp.viewModel.MovieViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverFragment : Fragment() {
    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var movieViewModel: MovieViewModel
    private var flag = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(MovieRepository()))[MovieViewModel::class.java]
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnClickListener {
            val intent = Intent(this@DiscoverFragment.context,SearchMoviesActivity::class.java)
            startActivity(intent)
        }
        movieViewModel.getMoviesByGenreIdMVVM.observe(viewLifecycleOwner){movies ->
            if(flag){
                binding.rcvMovies.adapter = MovieAdapter(movies){
                    val intent = Intent(this@DiscoverFragment.context,MovieDetailActivity::class.java)
                        .putExtra("id",it)
                    startActivity(intent)
                }
                flag=false
            }
            else{
                (binding.rcvMovies.adapter as MovieAdapter).updateData(movies)
            }
        }
        movieViewModel.getGenreMoviesMVVM.observe(viewLifecycleOwner){genres ->
            movieViewModel.getMoviesByGenreId(genres.genres[0].id)
            binding.rcvGenres.adapter = GenreAdapter(genres){
                movieViewModel.getMoviesByGenreId(it.id)
            }
        }
    }
}