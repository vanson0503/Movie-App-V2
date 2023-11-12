package com.example.movieapp.network

import com.example.movieapp.model.genre.Genres
import com.example.movieapp.model.movieDetail.Movie
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.model.trailerMovie.TrailerList
import com.example.movieapp.model.trendingTv.TrendingTvs
import com.example.movieapp.utils.Constrain.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("trending/movie/day")
    fun getMoviesTrending(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<Movies>

    @GET("movie/popular")
    fun getMoviesPopular(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<Movies>

    @GET("movie/top_rated")
    fun getMoviesTopRate(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<Movies>

    @GET("movie/upcoming")
    fun getUpComing(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<Movies>

    @GET("search/movie")
    fun getSearchMovies(
        @Query("query") query:String,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<Movies>

    @GET("movie/{id}/videos")
    fun getListTrailer(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = API_KEY
    ):Call<TrailerList>

    @GET("trending/tv/day")
    fun getTvsTrending(
        @Query("api_key") apiKey: String = API_KEY
    ):Call<TrendingTvs>

    @GET("genre/movie/list")
    fun getGenreMovies(
        @Query("api_key") apiKey: String = API_KEY
    ):Call<Genres>

    @GET("discover/movie")
    fun getMoviesByGenreId(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("with_genres") genreId:Int
    ):Call<Movies>

    @GET("movie/{id}")
    fun getDetailMovieById(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = API_KEY
    ):Call<Movie>

    @GET("movie/{id}/recommendations")
    fun getRecommendMovieById(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = API_KEY
    ):Call<com.example.movieapp.model.recomendMovies.Movies>
}