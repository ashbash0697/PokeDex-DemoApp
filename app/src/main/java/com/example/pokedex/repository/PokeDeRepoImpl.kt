package com.example.pokedex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.sample.PokemonDtlQuery
import com.apollographql.apollo.sample.PokemonsQuery
import com.apollographql.apollo.sample.fragment.PokemonDtl
import com.example.pokedex.utils.PokemonState

class PokeDeRepoImpl (val apolloClient: ApolloClient): PokeDexRepo{

    //PokemonState.DataState<PokemonDtl>()
    var pokemonDtlMutableLiveData : MutableLiveData<PokemonState> = MutableLiveData()

    //PokemonState.DataState<List<PokemonsQuery.Pokemon>>>()
    var pokemonsMutableLiveData: MutableLiveData<PokemonState> = MutableLiveData()


    override fun fetchAllPokemons(): LiveData<PokemonState> {

        pokemonsMutableLiveData.postValue(PokemonState.LoadingState)

        apolloClient.query(PokemonsQuery
            .builder()
            .number(151)
            .build())
            .enqueue(object : ApolloCall.Callback<PokemonsQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    pokemonsMutableLiveData.postValue(PokemonState.ErrorState(e.message))
                }

                override fun onResponse(response: Response<PokemonsQuery.Data>) {
                    response.data()?.pokemons()?.let {
                        pokemonsMutableLiveData.postValue(PokemonState.DataState<List<PokemonsQuery.Pokemon>>(it))
                    }

                }

            })

        return pokemonsMutableLiveData
    }

    override fun fetchPokemonById(pkmnId: String): LiveData<PokemonState>{

        pokemonDtlMutableLiveData.postValue(PokemonState.LoadingState)

        apolloClient.query(PokemonDtlQuery
            .builder()
            .pkmnId(pkmnId)
            .build())
            .enqueue(object : ApolloCall.Callback<PokemonDtlQuery.Data>(){
                override fun onFailure(e: ApolloException) {
                    pokemonDtlMutableLiveData.postValue(PokemonState.ErrorState(e.message))

                }

                override fun onResponse(response: Response<PokemonDtlQuery.Data>) {
                    response.data()?.pokemon()?.let {
                        pokemonDtlMutableLiveData.postValue(PokemonState.DataState<PokemonDtl>(it.fragments().pokemonDtl()))
                    }

                }

            })

        return pokemonDtlMutableLiveData
    }

}