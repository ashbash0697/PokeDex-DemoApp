package com.example.pokedex.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.sample.PokemonsQuery
import com.example.pokedex.di.components.PokeDexComponent
import com.example.pokedex.repository.PokeDexRepo
import com.example.pokedex.utils.CustomResponse
import javax.inject.Inject

class MainActivityViewModel() : ViewModel(), PokeDexComponent.Injectable{

   lateinit var pkmnListLiveData:  LiveData<CustomResponse<List<PokemonsQuery.Pokemon>>>


        @Inject
        lateinit var repo: PokeDexRepo


    override fun inject(pokeDexComponent: PokeDexComponent) {
        pokeDexComponent.inject(this)
        getPokemons()
    }





    fun getPokemons()  {
       pkmnListLiveData = repo.fetchAllPokemons()
    }



}