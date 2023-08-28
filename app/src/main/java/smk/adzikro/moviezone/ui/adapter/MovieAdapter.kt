package smk.adzikro.moviezone.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import smk.adzikro.moviezone.BuildConfig
import smk.adzikro.moviezone.R
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.utils.debug
import smk.adzikro.moviezone.databinding.ItemMoviesBinding

class MovieAdapter(
    private val context: Context,
    private val onItemClickCallback: OnItemClickCallback
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if(movie!=null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(
        private val binding: ItemMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(BuildConfig.IMG_SMALL.plus(movie.poster_path))
                    .into(ivItemPhoto)
                tvItemName.text = movie.title
                tvItemDescription.text = movie.overview
                tvItemDate.text = movie.release_date
                val rating = (movie.vote_average?.toFloat() ?: 0f) * 0.5f
                debug("Rating ${rating} dikali ${movie.vote_average?.toFloat()}")
                valueRating.rating = rating
                txRating.text = movie.vote_average.toString()
                val favo = if(movie.favorite)
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_favorite_white_24dp) else
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_favorite_border_white_24dp
                    )
                imgFavorite.setImageDrawable(favo)
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(
                        movie,
                        ivItemPhoto,
                        tvItemDescription,
                        tvItemName,
                        tvItemDate,
                        imgFavorite
                    )
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(
            movie: Movie?,
            image: ImageView,
            desc: TextView,
            name: TextView,
            date: TextView,
            favo : AppCompatImageView
        )
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean = oldItem.id == newItem.id
        }
    }
}