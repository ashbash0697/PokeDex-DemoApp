package com.example.pokedex.di.modules

import com.apollographql.apollo.ApolloClient
import com.example.pokedex.repository.PokeDeRepoImpl
import com.example.pokedex.repository.PokeDexRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    fun providePokeDexRepo(apolloClient: ApolloClient): PokeDexRepo{
        return PokeDeRepoImpl(apolloClient)
    }

}