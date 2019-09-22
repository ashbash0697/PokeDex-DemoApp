 package com.example.pokedex.ui.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.sample.PokemonsQuery
import com.example.pokedex.PokeDexApplication
import com.example.pokedex.PokeDexViewModelFactory
import com.example.pokedex.R
import com.example.pokedex.ui.PokemonDetail.PokemonDetailActivity
import com.example.pokedex.utils.CustomResponse
import com.example.pokedex.utils.StateEnum
import kotlinx.android.synthetic.main.activity_main.*

 class MainActivity : AppCompatActivity(), PokemonsListAdapter.Interaction {


     private val mainVm by lazy { ViewModelProviders.of(this, PokeDexViewModelFactory(application as PokeDexApplication)).get(
         MainActivityViewModel::class.java) }

     lateinit var pkmnsRecAdapter: PokemonsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecView()
        mainVm.getPokemons().observe(this, Observer<CustomResponse<List<PokemonsQuery.Pokemon>>>{
            when(it.status){
                StateEnum.SUCCESS -> {
                    showRecycler()
                    it.data?.let { it1 -> pkmnsRecAdapter.submitList(it1) }
                }
                StateEnum.FAILED -> {
                    showError(it.message)
                }
                StateEnum.LOADING ->{
                    showLoading()
                }

            }
        })
    }

     private fun showLoading() {
         pokeRecView.visibility = View.GONE
         mainActivityPB.visibility = View.VISIBLE
         mainActivityErrorMsg.visibility = View.GONE
     }

     private fun showError(message: String?) {
         pokeRecView.visibility = View.GONE
         mainActivityPB.visibility = View.GONE
         mainActivityErrorMsg.visibility = View.VISIBLE
         mainActivityErrorMsg.text = message
     }

     private fun showRecycler() {
         pokeRecView.visibility = View.VISIBLE
         mainActivityPB.visibility = View.GONE
         mainActivityErrorMsg.visibility = View.GONE
     }

     private fun initRecView() {
         pokeRecView.apply {
             layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
             pkmnsRecAdapter = PokemonsListAdapter(this@MainActivity)
             adapter = pkmnsRecAdapter
         }
     }

     override fun onItemSelected(position: Int, item: PokemonsQuery.Pokemon) {
         startActivity(Intent(this, PokemonDetailActivity::class.java).putExtra("ID", item.id()))
     }
 }
