package com.example.movieapp.repository

import android.util.Log
import com.example.movieapp.model.genre.Genres
import com.example.movieapp.model.movieDetail.Movie
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.model.trailerMovie.TrailerList
import com.example.movieapp.network.MovieServices

class MovieRepository {
    suspend fun getMoviesTrendingMVVM():Movies{
        return try{
            MovieServices.api.getMoviesTrendingMVVM()
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getMoviesPopularMVVM():Movies{
        return try{
            MovieServices.api.getMoviesPopularMVVM()
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getMoviesTopRateMVVM():Movies{
        return try{
            MovieServices.api.getMoviesTopRateMVVM()
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getUpComingMVVM():Movies{
        return try{
            MovieServices.api.getUpComingMVVM()
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getSearchMoviesMVVM(query:String):Movies{
        return try{
            MovieServices.api.getSearchMoviesMVVM(query)
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getGenreMoviesMVVM():Genres{
        return try{
            MovieServices.api.getGenreMoviesMVVM()
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getMoviesByGenreIdMVVM(genreId:Int):Movies{
        return try{
            MovieServices.api.getMoviesByGenreIdMVVM(genreId=genreId)
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getDetailMovieByIdMVVM(id:Int):Movie{
        return try{
            MovieServices.api.getDetailMovieByIdMVVM(id)
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getRecommendMovieByIdMVVM(id:Int):com.example.movieapp.model.recomendMovies.Movies{
        return try{
            MovieServices.api.getRecommendMovieByIdMVVM(id)
        }catch(e:Exception){
            throw e
        }
    }
    suspend fun getListTrailerMVVM(id:Int):TrailerList{
        return try{
            MovieServices.api.getListTrailerMVVM(id)
        }catch(e:Exception){
            throw e
        }
    }

}