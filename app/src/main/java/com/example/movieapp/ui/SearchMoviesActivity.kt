package com.example.movieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.adapter.ListMovieAdapter
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.ActivitySearchMoviesBinding
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.network.MovieServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMoviesBinding
    private var flag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnClear.setOnClickListener {
            binding.searchView.text.clear()
        }
        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

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
                    MovieServices.api.getSearchMovies(binding.searchView.text.toString())
                        .enqueue(object : Callback<Movies>{
                            override fun onResponse(
                                call: Call<Movies>,
                                response: Response<Movies>
                            ) {
                                if(response.isSuccessful){
                                    if(flag){
                                        binding.rcvSearchMovies.adapter = MovieAdapter(response.body()!!){
                                            val intent = Intent(this@SearchMoviesActivity,MovieDetailActivity::class.java)
                                                .putExtra("id",it)
                                            startActivity(intent)
                                        }
                                        flag=false
                                    }
                                    else{
                                        (binding.rcvSearchMovies.adapter as MovieAdapter).updateData(response.body()!!)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Movies>, t: Throwable) {
                                Toast.makeText(this@SearchMoviesActivity, t.toString(), Toast.LENGTH_SHORT).show()
                            }

                        })
                }
            }

        })

    }
}