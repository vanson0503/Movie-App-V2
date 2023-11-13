package com.example.movieapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.genre.Genres
import com.example.movieapp.model.movieDetail.Movie
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.model.reviewMovie.Reviews
import com.example.movieapp.model.trailerMovie.TrailerList
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
):ViewModel() {
    private val _trendingMovies = MutableLiveData<Movies>()
    val trendingMovies: LiveData<Movies>
        get() = _trendingMovies

    private val _getMoviesPopular = MutableLiveData<Movies>()
    val getMoviesPopular: LiveData<Movies>
        get() = _getMoviesPopular

    private val _getMoviesTopRate = MutableLiveData<Movies>()
    val getMoviesTopRate: LiveData<Movies>
        get() = _getMoviesTopRate

    private val _getUpComing = MutableLiveData<Movies>()
    val getUpComing: LiveData<Movies>
        get() = _getUpComing

    private val _searchedMovies = MutableLiveData<Movies>()
    val searchedMovies: LiveData<Movies>
        get() = _searchedMovies
    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val movies = movieRepository.getSearchMoviesMVVM(query)
                _searchedMovies.value = movies
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getMoviesByGenreIdMVVM = MutableLiveData<Movies>()
    val getMoviesByGenreIdMVVM: LiveData<Movies>
        get() = _getMoviesByGenreIdMVVM
    fun getMoviesByGenreId(genreId:Int){
        viewModelScope.launch {
            try {
                val movies = movieRepository.getMoviesByGenreIdMVVM(genreId)
                _getMoviesByGenreIdMVVM.value = movies
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getDetailMovieByIdMVVM = MutableLiveData<Movie>()
    val getDetailMovieByIdMVVM:LiveData<Movie>
        get() = _getDetailMovieByIdMVVM
    fun getDetailMovieById(id:Int){
        viewModelScope.launch {
            try {
                val movie = movieRepository.getDetailMovieByIdMVVM(id)
                _getDetailMovieByIdMVVM.value = movie
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getRecommendMovieByIdMVVM = MutableLiveData<com.example.movieapp.model.recomendMovies.Movies>()
    val getRecommendMovieByIdMVVM:LiveData<com.example.movieapp.model.recomendMovies.Movies>
        get() = _getRecommendMovieByIdMVVM
    fun getRecommendMovieById(id:Int){
        viewModelScope.launch {
            try {
                val movies = movieRepository.getRecommendMovieByIdMVVM(id)
                _getRecommendMovieByIdMVVM.value = movies
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getListTrailerMVVM = MutableLiveData<TrailerList>()
    val getListTrailerMVVM: LiveData<TrailerList>
        get() = _getListTrailerMVVM
    fun getListTrailer(id:Int){
        viewModelScope.launch {
            try {
                val trailerList = movieRepository.getListTrailerMVVM(id)
                _getListTrailerMVVM.value = trailerList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getReviewsByMovieIdMVVM = MutableLiveData<Reviews>()
    val getReviewsByMovieIdMVVM:LiveData<Reviews>
        get() = _getReviewsByMovieIdMVVM
    fun getReviewsByMovieId(id:Int){
        viewModelScope.launch {
            try {
                val reviews = movieRepository.getReviewsByMovieId(id)
                _getReviewsByMovieIdMVVM.value = reviews
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _getGenreMoviesMVVM = MutableLiveData<Genres>()
    val getGenreMoviesMVVM: LiveData<Genres>
        get() = _getGenreMoviesMVVM




    init {
        viewModelScope.launch {
            _trendingMovies.value = movieRepository.getMoviesTrendingMVVM()
            _getMoviesPopular.value = movieRepository.getMoviesPopularMVVM()
            _getMoviesTopRate.value = movieRepository.getMoviesTopRateMVVM()
            _getUpComing.value = movieRepository.getUpComingMVVM()
            _getGenreMoviesMVVM.value = movieRepository.getGenreMoviesMVVM()
        }
    }
}