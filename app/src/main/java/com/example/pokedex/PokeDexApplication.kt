package com.example.pokedex

import android.app.Application
import com.example.pokedex.di.components.DaggerPokeDexComponent
import com.example.pokedex.di.components.PokeDexComponent
import com.example.pokedex.di.modules.AppModule

class PokeDexApplication: Application(){

    companion object{
        lateinit var pokeDexApplication: PokeDexApplication
        private set
    }

    val pokeDexComponent : PokeDexComponent by lazy {
        DaggerPokeDexComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        pokeDexApplication = this
    }

}