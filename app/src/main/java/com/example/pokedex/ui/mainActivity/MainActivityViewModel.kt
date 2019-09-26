package com.example.pokedex.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.di.components.PokeDexComponent
import com.example.pokedex.repository.PokeDexRepo
import com.example.pokedex.utils.PokemonState
import javax.inject.Inject

class MainActivityViewModel() : ViewModel(), PokeDexComponent.Injectable{

   lateinit var pkmnListLiveData:  LiveData<PokemonState>


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