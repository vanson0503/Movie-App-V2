package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.moviesList.Movies
import com.example.movieapp.utils.Constrain.BASE_URL_IMAGE
import com.google.android.material.imageview.ShapeableImageView

class MovieAdapter(
    private var movies: Movies,
    val onItemClick: (Int) -> Unit
):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.imgMovie)
        val title:TextView = itemView.findViewById(R.id.txtTitle)
        val item:ConstraintLayout = itemView.findViewById(R.id.movieItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return movies.results.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.image)
            .load(BASE_URL_IMAGE+movies.results[position].poster_path)
            .into(holder.image)
        holder.title.text = movies.results[position].title
        holder.item.setOnClickListener { onItemClick(movies.results[position].id) }

    }
    fun updateData(newData: Movies) {
        movies = newData
        notifyDataSetChanged()
    }

}