package com.example.movieapp.network

import com.example.movieapp.model.genre.Genres
import com.example.movieapp.model.movieDetail.Movie
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.model.reviewMovie.Reviews
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
    suspend fun getMoviesTrendingMVVM(
        @Query("api_key") apiKey: String = API_KEY
    ): Movies

    @GET("movie/popular")
    suspend fun getMoviesPopularMVVM(
        @Query("api_key") apiKey: String = API_KEY
    ): Movies

    @GET("movie/top_rated")
    suspend fun getMoviesTopRateMVVM(
        @Query("api_key") apiKey: String = API_KEY
    ): Movies

    @GET("movie/upcoming")
    suspend fun getUpComingMVVM(
        @Query("api_key") apiKey: String = API_KEY
    ): Movies

    @GET("search/movie")
    suspend fun getSearchMoviesMVVM(
        @Query("query") query:String,
        @Query("api_key") apiKey: String = API_KEY
    ): Movies

    @GET("movie/{id}/videos")
    suspend fun getListTrailerMVVM(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = API_KEY
    ):TrailerList


    @GET("genre/movie/list")
    suspend fun getGenreMoviesMVVM(
        @Query("api_key") apiKey: String = API_KEY
    ):Genres

    @GET("discover/movie")
    suspend fun getMoviesByGenreIdMVVM(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("with_genres") genreId:Int
    ):Movies

    @GET("movie/{id}")
    suspend fun getDetailMovieByIdMVVM(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = API_KEY
    ):Movie

    @GET("movie/{id}/recommendations")
    suspend fun getRecommendMovieByIdMVVM(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = API_KEY
    ):com.example.movieapp.model.recomendMovies.Movies

    @GET("movie/{id}/reviews")
    suspend fun getReviewsByMovieId(
        @Path("id") id:Int,
        @Query("api_key") apiKey: String = API_KEY
    ):Reviews
}