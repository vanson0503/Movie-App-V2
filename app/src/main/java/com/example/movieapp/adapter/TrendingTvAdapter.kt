package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.trendingTv.TrendingTvs
import com.example.movieapp.utils.Constrain.BASE_URL_IMAGE
import com.google.android.material.imageview.ShapeableImageView

class TrendingTvAdapter(private val tvs:TrendingTvs):RecyclerView.Adapter<TrendingTvAdapter.TrendingTvViewHolder>() {
    class TrendingTvViewHolder(private val itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.imgTrendingMovie)
        val iDMb:TextView = itemView.findViewById(R.id.txtTrendingIMDb)
        val title:TextView = itemView.findViewById(R.id.txtTrendingTitleMovie)
        val item:ConstraintLayout = itemView.findViewById(R.id.itemTrendingMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingTvViewHolder {
        return TrendingTvViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trending_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return tvs.results.size
    }

    override fun onBindViewHolder(holder: TrendingTvViewHolder, position: Int) {
        val tvs = tvs.results[position]
        holder.apply {
            Glide.with(image)
                .load(BASE_URL_IMAGE+tvs.poster_path)
                .into(image)
            title.text = tvs.name
            iDMb.text = tvs.vote_average.toString()
        }
    }
}