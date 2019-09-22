package com.example.pokedex.ui.PokemonDetail.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.apollographql.apollo.sample.fragment.PokemonDtl
import com.bumptech.glide.Glide
import com.example.pokedex.R
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

class EvolutionListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonDtl.Evolution>() {

        override fun areItemsTheSame(oldItem: PokemonDtl.Evolution, newItem: PokemonDtl.Evolution): Boolean {
            return oldItem.id() == newItem.id()
        }

        override fun areContentsTheSame(oldItem: PokemonDtl.Evolution, newItem: PokemonDtl.Evolution): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return EvolutionListViewHolder(
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
            is EvolutionListViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PokemonDtl.Evolution>) {
        differ.submitList(list)
    }

    class EvolutionListViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PokemonDtl.Evolution) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.pkmnsName.text = item.name()
            itemView.pkmnsType.text = item.types()?.joinToString()
            itemView.pkmnsNumber.text = item.number()
            Glide.with(itemView.context)
                .load(item.image())
                .into(itemView.pkmnsImgView)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PokemonDtl.Evolution)
    }
}