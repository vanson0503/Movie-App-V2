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

class ListMovieAdapter(
    private val movies:Movies,
    private val onItemClick: (Int) -> Unit
):RecyclerView.Adapter<ListMovieAdapter.ListMovieViewHolder>() {
    class ListMovieViewHolder(private val itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.imgTrendingMovie)
        val iDMb:TextView = itemView.findViewById(R.id.txtTrendingIMDb)
        val title:TextView = itemView.findViewById(R.id.txtTrendingTitleMovie)
        val item:ConstraintLayout = itemView.findViewById(R.id.itemTrendingMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieViewHolder {
        return ListMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trending_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return movies.results.size
    }

    override fun onBindViewHolder(holder: ListMovieViewHolder, position: Int) {
        val movie = movies.results[position]
        holder.apply {
            Glide.with(image)
                .load(BASE_URL_IMAGE+movie.poster_path)
                .into(image)
            title.text = movie.title
            iDMb.text = movie.vote_average.toString()
            item.setOnClickListener { onItemClick(movie.id) }
        }
    }
}