package com.example.pokedex.di.components

import com.example.pokedex.di.modules.AppModule
import com.example.pokedex.di.modules.NetModule
import com.example.pokedex.di.modules.RepoModule
import com.example.pokedex.ui.PokemonDetail.PokemonDtlViewModel
import com.example.pokedex.ui.mainActivity.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class, RepoModule::class))
interface PokeDexComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)

    fun inject(pokemonDtlViewModel: PokemonDtlViewModel)

    interface Injectable{
        fun inject(pokeDexComponent: PokeDexComponent)
    }

}