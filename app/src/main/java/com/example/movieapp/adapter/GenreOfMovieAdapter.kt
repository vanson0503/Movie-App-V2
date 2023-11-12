package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.movieDetail.Genre

class GenreOfMovieAdapter(private val genres:List<Genre>):RecyclerView.Adapter<GenreOfMovieAdapter.GenreOfMovieViewHolder>() {
    class GenreOfMovieViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.txtNameGenreOfmovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreOfMovieViewHolder {
        return GenreOfMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.genre_of_movie_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: GenreOfMovieViewHolder, position: Int) {
        holder.name.text = genres[position].name
    }
}