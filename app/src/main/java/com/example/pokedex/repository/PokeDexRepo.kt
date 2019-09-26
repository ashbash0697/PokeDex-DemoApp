package com.example.pokedex.repository

import androidx.lifecycle.LiveData
import com.example.pokedex.utils.PokemonState

interface PokeDexRepo {

    fun fetchAllPokemons(): LiveData<PokemonState>

    fun fetchPokemonById(pkmnId: String): LiveData<PokemonState>

    }