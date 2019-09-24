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
import com.example.pokedex.utils.CustomResponse
import com.example.pokedex.utils.StateEnum

class PokeDeRepoImpl (val apolloClient: ApolloClient): PokeDexRepo{

    var pokemonDtlMutableLiveData : MutableLiveData<CustomResponse<PokemonDtl>> = MutableLiveData()
    var pokemonsMutableLiveData: MutableLiveData<CustomResponse<List<PokemonsQuery.Pokemon>>> = MutableLiveData()

    override fun fetchAllPokemons(): LiveData<CustomResponse<List<PokemonsQuery.Pokemon>>> {

        pokemonsMutableLiveData.postValue(CustomResponse(StateEnum.LOADING, "", null))

        apolloClient.query(PokemonsQuery
            .builder()
            .number(151)
            .build())
            .enqueue(object : ApolloCall.Callback<PokemonsQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    pokemonsMutableLiveData.postValue(CustomResponse(StateEnum.FAILED, e.message, null))
                }

                override fun onResponse(response: Response<PokemonsQuery.Data>) {
                    response.data()?.pokemons()?.let {
                        pokemonsMutableLiveData.postValue(CustomResponse(StateEnum.SUCCESS, "Success", it))
                    }

                }

            })

        return pokemonsMutableLiveData
    }

    override fun fetchPokemonById(pkmnId: String): MutableLiveData<CustomResponse<PokemonDtl>>{

        pokemonDtlMutableLiveData.postValue(CustomResponse(StateEnum.LOADING, "", null))

        apolloClient.query(PokemonDtlQuery
            .builder()
            .pkmnId(pkmnId)
            .build())
            .enqueue(object : ApolloCall.Callback<PokemonDtlQuery.Data>(){
                override fun onFailure(e: ApolloException) {
                    pokemonDtlMutableLiveData.postValue(CustomResponse(StateEnum.FAILED, e.message, null))

                }

                override fun onResponse(response: Response<PokemonDtlQuery.Data>) {
                    response.data()?.pokemon()?.let {
                        pokemonDtlMutableLiveData.postValue(CustomResponse(StateEnum.SUCCESS, "Success", it.fragments().pokemonDtl()))
                    }

                }

            })

        return pokemonDtlMutableLiveData
    }

}