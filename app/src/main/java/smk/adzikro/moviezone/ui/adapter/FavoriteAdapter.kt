package smk.adzikro.moviezone.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import smk.adzikro.moviezone.BuildConfig
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.utils.options
import smk.adzikro.moviezone.databinding.ItemMoviesBinding

class FavoriteAdapter (
    private val onItemClickCallback: OnItemClickCallback
): RecyclerView.Adapter<FavoriteAdapter.Holder>() {

    inner class Holder (
        private val binding: ItemMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(BuildConfig.IMG_SMALL.plus(movie?.poster_path))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(options)
                    .into(ivItemPhoto)
                tvItemName.text = movie?.title
                tvItemDescription.text = movie?.overview
                tvItemDate.text = movie?.release_date
                valueRating.rating = movie?.vote_average?.toFloat()!!
                txRating.text = movie.vote_average.toString()
                imgFavorite.visibility = View.INVISIBLE
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(
                        movie,
                        ivItemPhoto,
                        tvItemDescription,
                        tvItemName,
                        tvItemDate,
                        imgFavorite,
                        valueRating
                    )
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int =
        differ.currentList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(true)
    }
    interface OnItemClickCallback {
        fun onItemClicked(
            movie: Movie?,
            image: ImageView,
            desc: TextView,
            name: TextView,
            date: TextView,
            favo : AppCompatImageView,
            valueRating : AppCompatRatingBar
        )
    }

    private val differCallback = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)
}