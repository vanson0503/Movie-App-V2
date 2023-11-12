package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.recomendMovies.Movies
import com.example.movieapp.utils.Constrain.BASE_URL_IMAGE
import com.google.android.material.imageview.ShapeableImageView

class RecommendAdapter(
    private val movies: Movies,
    val onItemClick: (Int) -> Unit
):RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {
    class RecommendViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.imgMovie)
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val item: ConstraintLayout = itemView.findViewById(R.id.recommendItemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        return RecommendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recommendations_movie_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return movies.results.size
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val movie = movies.results[position]
        holder.apply {
            Glide.with(image)
                .load(BASE_URL_IMAGE+movie.poster_path)
                .into(image)
            title.text = movie.title
            item.setOnClickListener { onItemClick(movie.id) }
        }
    }
}