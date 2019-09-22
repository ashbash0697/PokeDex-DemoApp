package com.example.pokedex.utils

import com.example.pokedex.repository.PokeDeRepoImpl

data class CustomResponse<T> (var status: StateEnum , var message: String?, var data: T?)