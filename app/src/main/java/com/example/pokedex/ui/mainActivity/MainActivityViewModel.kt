package com.example.pokedex.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.sample.PokemonsQuery
import com.example.pokedex.di.components.PokeDexComponent
import com.example.pokedex.repository.PokeDexRepo
import com.example.pokedex.utils.CustomResponse
import javax.inject.Inject

class MainActivityViewModel() : ViewModel(), PokeDexComponent.Injectable{

    override fun inject(pokeDexComponent: PokeDexComponent) {
        pokeDexComponent.inject(this)
    }

    @Inject
    lateinit var repo: PokeDexRepo



    fun getPokemons(): LiveData<CustomResponse<List<PokemonsQuery.Pokemon>>> {
       return repo.fetchAllPokemons()
    }



}