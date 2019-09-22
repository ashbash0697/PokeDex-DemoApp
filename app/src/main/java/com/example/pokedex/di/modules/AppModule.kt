package com.example.pokedex.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (application: Application){
    val application = application

    @Provides
    @Singleton
    fun providesApplication(): Application = application



}