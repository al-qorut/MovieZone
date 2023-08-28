package smk.adzikro.moviezone.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import smk.adzikro.moviezone.BuildConfig
import smk.adzikro.moviezone.core.domain.model.Actor
import smk.adzikro.moviezone.databinding.ItemCastBinding

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorHolder>() {

    inner class ActorHolder(
        private val binding: ItemCastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) {
            binding.apply {
                Glide.with(binding.root)
                    .load(BuildConfig.IMG_SMALL.plus(actor.profile_path))
                    .into(imageCast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Actor>(){
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

}