package com.example.lapokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

class PokemonAdapter(
        var pokemonList: List<Pokemon>,
        val listener: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(
                                    R.layout.pokemon_list_item,
                                    parent,
                                    false
                            )
            )

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemonList[position], listener)
    }

    class ViewHolder(var v: View) : RecyclerView.ViewHolder(v) {
        fun bind(pokemon: Pokemon, listener: (Pokemon) -> Unit) {
            with(v) {
                pokemon_id.text = pokemon.id
                pokemon_name.text = pokemon.name
                setOnClickListener {
                    listener(pokemon)
                }
            }
        }

    }
}