package cz.lamorak.koti.allcat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.databinding.ItemAllcatBinding

class AllCatAdapter : PagingDataAdapter<Cat, AllCatAdapter.CatViewHolder>(COMPARATOR) {

    private val channel = MutableLiveData<Cat?>()

    fun selectedCats(): LiveData<Cat?> {
        channel.value = null
        return channel
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)!!
        holder.bind(cat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemAllcatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    inner class CatViewHolder(private val binding: ItemAllcatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat) {
            binding.catThumbnail.load(cat.url) {
                crossfade(true)
                placeholder(R.drawable.ic_cat_silhouette)
            }
            itemView.setOnClickListener {
                channel.value = cat
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