package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.movieapp.adapter.ListMovieAdapter
import com.example.movieapp.adapter.TrendingTvAdapter
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.model.trendingTv.TrendingTvs
import com.example.movieapp.network.MovieServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class HomeFragment : Fragment() {
    private lateinit var trendingTvs: TrendingTvs
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTransformer()
        MovieServices.api.getMoviesTrending().enqueue(object : Callback<Movies>{
            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                if(response.isSuccessful){
                    val trendingMovies = response.body()!!
                    Log.i("TAG", "onResponse: "+response.body()!!.toString())
                    binding.viewPageTrendingMovie.adapter = ListMovieAdapter(trendingMovies){
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
                else{
                    Toast.makeText(this@HomeFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.i("TAG", "onFailure: "+t.message.toString())
            }
        })
        MovieServices.api.getUpComing().enqueue(object : Callback<Movies>{
            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                if(response.isSuccessful){
                    val upComingMovies = response.body()!!
                    Log.i("TAG", "onResponse: "+response.body()!!.toString())
                    binding.viewPageUpComingMovies.adapter = ListMovieAdapter(upComingMovies){
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
                else{
                    Toast.makeText(this@HomeFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.i("TAG", "onFailure: "+t.message.toString())
            }
        })
        MovieServices.api.getMoviesTopRate().enqueue(object : Callback<Movies>{
            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                if(response.isSuccessful){
                    val topRateMovies = response.body()!!
                    Log.i("TAG", "onResponse: "+response.body()!!.toString())
                    binding.viewPageTopRateMovies.adapter = ListMovieAdapter(topRateMovies){
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
                else{
                    Toast.makeText(this@HomeFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.i("TAG", "onFailure: "+t.message.toString())
            }
        })
        MovieServices.api.getMoviesPopular().enqueue(object : Callback<Movies>{
            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                if(response.isSuccessful){
                    val popularMovies = response.body()!!
                    Log.i("TAG", "onResponse: "+response.body()!!.toString())
                    binding.viewPagePopularMovies.adapter = ListMovieAdapter(popularMovies){
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
                else{
                    Toast.makeText(this@HomeFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.i("TAG", "onFailure: "+t.message.toString())
            }
        })
        MovieServices.api.getTvsTrending().enqueue(object : Callback<TrendingTvs>{
                override fun onResponse(call: Call<TrendingTvs>, response: Response<TrendingTvs>) {
                    if(response.isSuccessful){
                        trendingTvs = response.body()!!
                        Log.i("TAG", "onResponse: "+response.body()!!.toString())
                        binding.viewPageTrendingTv.adapter = TrendingTvAdapter(trendingTvs)
                        binding.viewPageTrendingTv.offscreenPageLimit = 3
                        binding.viewPageTrendingTv.clipToPadding = false
                        binding.viewPageTrendingTv.clipChildren = false
                        binding.viewPageTrendingTv.getChildAt(0).overScrollMode =
                            RecyclerView.OVER_SCROLL_NEVER
                        binding.viewPageTrendingTv.setCurrentItem(1, false)
                    }
                    else{
                        Toast.makeText(this@HomeFragment.context, "Looix", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<TrendingTvs>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(100))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.8f + r * 0.19f
        }
        binding.viewPageTrendingMovie.setPageTransformer(transformer)
        binding.viewPageTrendingTv.setPageTransformer(transformer)
        binding.viewPageUpComingMovies.setPageTransformer(transformer)
        binding.viewPageTopRateMovies.setPageTransformer(transformer)
        binding.viewPagePopularMovies.setPageTransformer(transformer)
    }
}