package cz.lamorak.koti.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import cz.lamorak.koti.R
import cz.lamorak.koti.favourites.FavouritesAdapter.FavouriteCatViewHolder
import cz.lamorak.koti.favourites.model.FavouriteCat
import kotlinx.android.synthetic.main.item_allcat.view.*

class FavouritesAdapter: ListAdapter<FavouriteCat, FavouriteCatViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteCatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_allcat, parent, false)
        return FavouriteCatViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteCatViewHolder, position: Int) {
        val favouriteCat = getItem(position)!!
        holder.bind(favouriteCat)
    }

    inner class FavouriteCatViewHolder(itemView: View): ViewHolder(itemView) {

        fun bind(cat: FavouriteCat) {
            itemView.cat_thumbnail.load(cat.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_cat_silhouette)
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