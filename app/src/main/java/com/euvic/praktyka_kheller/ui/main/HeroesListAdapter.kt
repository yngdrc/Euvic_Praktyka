package com.euvic.praktyka_kheller.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.euvic.praktyka_kheller.R
import com.euvic.praktyka_kheller.db.model.HeroDetails
import com.euvic.praktyka_kheller.util.Constants.Companion.STEAM_DOTA_IMAGES_URL
import com.google.accompanist.appcompattheme.AppCompatTheme

class HeroesListAdapter(private val interaction: Interaction? = null) :
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

        return HeroDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_heroeslist_item,
                parent,
                false
            ),
            interaction
        )
    }

    class HeroDetailsViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: HeroDetails) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            val greeting = findViewById<ComposeView>(R.id.hero_name)
            greeting.setContent {
                AppCompatTheme() {
                    item.localized_name?.let {
                        setHeroItem(item.localized_name, item.roles,null?: STEAM_DOTA_IMAGES_URL.plus(
                            item.name?.replace(
                                "npc_dota_hero_",
                                ""
                            ).plus("_vert.jpg")
                        ))
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeroDetailsViewHolder -> {
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
