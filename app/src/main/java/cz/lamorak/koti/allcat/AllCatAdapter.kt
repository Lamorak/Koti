package cz.lamorak.koti.allcat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import kotlinx.android.synthetic.main.item_allcat.view.*

class AllCatAdapter : PagingDataAdapter<Cat, AllCatAdapter.CatViewHolder>(COMPARATOR) {

    private val channel = MutableLiveData<String>()

    fun selectedCats() = channel

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)!!
        holder.bind(cat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_allcat, parent, false)
        return CatViewHolder(view)
    }

    inner class CatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(cat: Cat) {
            itemView.cat_thumbnail.load(cat.url) {
                crossfade(true)
                placeholder(R.drawable.ic_cat_silhouette)
            }
            itemView.setOnClickListener {
                channel.value = cat.url
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem == newItem
            }
        }
    }
}