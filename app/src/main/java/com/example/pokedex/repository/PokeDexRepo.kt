package com.example.pokedex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.sample.PokemonsQuery
import com.apollographql.apollo.sample.fragment.PokemonDtl
import com.example.pokedex.utils.CustomResponse

interface PokeDexRepo {

    fun fetchAllPokemons(): LiveData<CustomResponse<List<PokemonsQuery.Pokemon>>>

    fun fetchPokemonById(pkmnId: String): MutableLiveData<CustomResponse<PokemonDtl>>




    }