package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.movieapp.adapter.ListMovieAdapter
import com.example.movieapp.adapter.TrendingTvAdapter
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.model.trendingTv.TrendingTvs
import com.example.movieapp.network.MovieServices
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.viewModel.MovieViewModel
import com.example.movieapp.viewModel.MovieViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieViewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel = ViewModelProvider(this,MovieViewModelFactory(MovieRepository()))[MovieViewModel::class.java]
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTransformer()

        movieViewModel.trendingMovies.observe(viewLifecycleOwner){ movies ->
            binding.viewPageTrendingMovie.adapter = ListMovieAdapter(movies){
                val intent = Intent(this@HomeFragment.context,MovieDetailActivity::class.java)
                    .putExtra("id",it)
                startActivity(intent)
            }
            binding.viewPageTrendingMovie.offscreenPageLimit = 3
            binding.viewPageTrendingMovie.clipToPadding = false
            binding.viewPageTrendingMovie.clipChildren = false
            binding.viewPageTrendingMovie.getChildAt(0).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
            binding.viewPageTrendingMovie.setCurrentItem(1, false)
        }
        movieViewModel.getUpComing.observe(viewLifecycleOwner){movies ->
            binding.viewPageUpComingMovies.adapter = ListMovieAdapter(movies){
                val intent = Intent(this@HomeFragment.context,MovieDetailActivity::class.java)
                    .putExtra("id",it)
                startActivity(intent)
            }
            binding.viewPageUpComingMovies.offscreenPageLimit = 3
            binding.viewPageUpComingMovies.clipToPadding = false
            binding.viewPageUpComingMovies.clipChildren = false
            binding.viewPageUpComingMovies.getChildAt(0).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
            binding.viewPageUpComingMovies.setCurrentItem(1, false)
        }
        movieViewModel.getMoviesTopRate.observe(viewLifecycleOwner){movies ->
            binding.viewPageTopRateMovies.adapter = ListMovieAdapter(movies){
                val intent = Intent(this@HomeFragment.context,MovieDetailActivity::class.java)
                    .putExtra("id",it)
                startActivity(intent)
            }
            binding.viewPageTopRateMovies.offscreenPageLimit = 3
            binding.viewPageTopRateMovies.clipToPadding = false
            binding.viewPageTopRateMovies.clipChildren = false
            binding.viewPageTopRateMovies.getChildAt(0).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
            binding.viewPageTopRateMovies.setCurrentItem(1, false)
        }
        movieViewModel.getMoviesPopular.observe(viewLifecycleOwner){movies ->
            binding.viewPagePopularMovies.adapter = ListMovieAdapter(movies){
                val intent = Intent(this@HomeFragment.context,MovieDetailActivity::class.java)
                    .putExtra("id",it)
                startActivity(intent)
            }
            binding.viewPagePopularMovies.offscreenPageLimit = 3
            binding.viewPagePopularMovies.clipToPadding = false
            binding.viewPagePopularMovies.clipChildren = false
            binding.viewPagePopularMovies.getChildAt(0).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
            binding.viewPagePopularMovies.setCurrentItem(1, false)
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(100))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.8f + r * 0.19f
        }
        binding.viewPageTrendingMovie.setPageTransformer(transformer)
        binding.viewPageUpComingMovies.setPageTransformer(transformer)
        binding.viewPageTopRateMovies.setPageTransformer(transformer)
        binding.viewPagePopularMovies.setPageTransformer(transformer)
    }
}