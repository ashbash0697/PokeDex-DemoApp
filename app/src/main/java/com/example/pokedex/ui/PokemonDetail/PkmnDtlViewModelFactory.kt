package com.example.pokedex.ui.PokemonDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.PokeDexApplication
import com.example.pokedex.di.components.PokeDexComponent

class PkmnDtlViewModelFactory (val application: PokeDexApplication, val id: String?): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val t:T = super.create(modelClass)
        if (t is PokeDexComponent.DtlInjectable){
            t.inject(application.pokeDexComponent, id)
        }
        return t
    }
}