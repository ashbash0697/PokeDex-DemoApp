package com.example.pokedex.ui.PokemonDetail.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.apollographql.apollo.sample.fragment.PokemonDtl
import com.example.pokedex.R
import kotlinx.android.synthetic.main.attack_list_item.view.*

class AttackListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonDtl.Fast>() {

        override fun areItemsTheSame(oldItem: PokemonDtl.Fast, newItem: PokemonDtl.Fast): Boolean {
            return oldItem.name() == newItem.name()
        }

        override fun areContentsTheSame(oldItem: PokemonDtl.Fast, newItem: PokemonDtl.Fast): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return AttackListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.attack_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AttackListViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PokemonDtl.Fast>) {
        differ.submitList(list)
    }

    class AttackListViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PokemonDtl.Fast) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.attackName.text = "Name : " + item.name()
            itemView.attackType.text = "Type : " + item.type()
            itemView.attackDamage.text = "Damage : " + item.damage().toString()
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: PokemonDtl.Fast)
    }
}