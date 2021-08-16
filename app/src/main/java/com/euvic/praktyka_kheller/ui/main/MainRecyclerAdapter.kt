package com.euvic.praktyka_kheller.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.db.model.HeroDetails

class MainRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HeroDetails>() {

        override fun areItemsTheSame(
            oldItem: HeroDetails,
            newItem: HeroDetails
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HeroDetails,
            newItem: HeroDetails
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return HeroViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_heroeslist_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeroViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<HeroDetails>) {
        differ.submitList(list)
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: HeroDetails)
    }
}
