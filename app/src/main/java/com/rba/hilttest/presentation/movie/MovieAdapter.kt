package com.rba.hilttest.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rba.hilttest.databinding.ItemMovieBinding
import com.rba.hilttest.domain.model.MovieModel

class MovieAdapter(private val clickListener: (MovieModel) -> Unit) :
    ListAdapter<MovieModel, MovieAdapter.MoviePopularViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePopularViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MoviePopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviePopularViewHolder, position: Int) {
        getItem(position).let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                clickListener.invoke(movie)
            }
        }
    }

    class MoviePopularViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieModel) {
            binding.movieTextView.text = movie.title
        }

    }

    internal class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {

        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }

}
