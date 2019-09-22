package com.example.pokedex.ui.mainActivity

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.apollographql.apollo.sample.PokemonsQuery
import com.bumptech.glide.Glide
import com.example.pokedex.R
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

class PokemonsListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonsQuery.Pokemon>() {

        override fun areItemsTheSame(oldItem: PokemonsQuery.Pokemon, newItem: PokemonsQuery.Pokemon): Boolean {
            return oldItem.id() == newItem.id()
        }

        override fun areContentsTheSame(oldItem: PokemonsQuery.Pokemon, newItem: PokemonsQuery.Pokemon): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PokemonsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonsViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PokemonsQuery.Pokemon>) {
        differ.submitList(list)
    }

    class PokemonsViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PokemonsQuery.Pokemon) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.pkmnsName.text = item.name()
            itemView.pkmnsNumber.text = item.number()
            itemView.pkmnsType.text = item.types()?.joinToString() ?: "Unknown"

            Glide.with(itemView.context)
                .load(item.image())
                .into(itemView.pkmnsImgView)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PokemonsQuery.Pokemon)
    }
}