package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.reviewMovie.Reviews
import com.example.movieapp.utils.Constrain.BASE_URL_IMAGE
import com.google.android.material.imageview.ShapeableImageView

class ReviewAdapter(private val reviews: Reviews):RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.imgUserImage)
        val userName:TextView = itemView.findViewById(R.id.txtUserName)
        val content:TextView = itemView.findViewById(R.id.txtContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.review_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return reviews.results.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews.results[position]
        holder.apply {
            Glide.with(image)
                .load(BASE_URL_IMAGE+review.author_details.avatar_path)
                .into(image)
            userName.text = review.author_details.username
            content.text = review.content
        }
    }
}