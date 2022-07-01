package cz.lamorak.koti.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.databinding.ItemAllcatBinding
import cz.lamorak.koti.favourites.FavouritesAdapter.FavouriteCatViewHolder
import cz.lamorak.koti.favourites.model.FavouriteCat

class FavouritesAdapter : ListAdapter<FavouriteCat, FavouriteCatViewHolder>(COMPARATOR) {

    private val channel = MutableLiveData<Cat>()

    fun selectedCats(): LiveData<Cat> = channel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteCatViewHolder {
        val binding = ItemAllcatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteCatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteCatViewHolder, position: Int) {
        val favouriteCat = getItem(position)!!
        holder.bind(favouriteCat)
    }

    inner class FavouriteCatViewHolder(private val binding: ItemAllcatBinding) :
        ViewHolder(binding.root) {

        fun bind(cat: FavouriteCat) {
            binding.catThumbnail.load(cat.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_cat_silhouette)
            }
            itemView.setOnClickListener {
                channel.value = Cat(cat.id, cat.imageUrl)
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<FavouriteCat>() {
            override fun areItemsTheSame(oldItem: FavouriteCat, newItem: FavouriteCat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavouriteCat, newItem: FavouriteCat): Boolean {
                return oldItem == newItem
            }
        }
    }
}