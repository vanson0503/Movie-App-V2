package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.genre.Genre
import com.example.movieapp.model.genre.Genres

class GenreAdapter(
    private val genres: Genres,
    private val onGenreClick: (Genre) -> Unit
):RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    var selectedPosition: Int = 0

    class GenreViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var name:TextView = itemView.findViewById(R.id.txtGenreName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.genre_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return genres.genres.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.name.text = genres.genres[position].name
        val textColor = if (position == selectedPosition) {
            R.color.selectedTextColor
        } else {
            R.color.white
        }

        holder.name.setTextColor(holder.itemView.resources.getColor(textColor))

        holder.name.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            onGenreClick(genres.genres[position])
        }
    }
}