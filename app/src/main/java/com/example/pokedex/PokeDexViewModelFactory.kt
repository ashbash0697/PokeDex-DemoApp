package com.example.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.di.components.PokeDexComponent

class PokeDexViewModelFactory (val application: PokeDexApplication): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val t:T = super.create(modelClass)
        if (t is PokeDexComponent.Injectable){
            t.inject(application.pokeDexComponent)
        }
        return t
    }
}