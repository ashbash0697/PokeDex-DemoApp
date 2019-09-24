package com.example.pokedex.ui.PokemonDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.sample.fragment.PokemonDtl
import com.example.pokedex.di.components.PokeDexComponent
import com.example.pokedex.repository.PokeDexRepo
import com.example.pokedex.utils.CustomResponse
import javax.inject.Inject

class PokemonDtlViewModel() : ViewModel(),PokeDexComponent.Injectable{

    var pokemonDtl: PokemonDtl? = null

    lateinit var pokemonDtlLiveData : LiveData<CustomResponse<PokemonDtl>>

     override fun inject(pokeDexComponent: PokeDexComponent) {
        pokeDexComponent.inject(this)
    }

    @Inject
    lateinit var repo: PokeDexRepo

     fun getPokemonDetailById(pokeId: String){

       //  if (pokemonDtl == null) {

         if (!pokemonDtl?.id().equals(pokeId)){

             pokemonDtlLiveData = repo.fetchPokemonById(pokeId)

         }

       //  }

    }





}