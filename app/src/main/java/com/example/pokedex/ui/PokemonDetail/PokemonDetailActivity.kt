package com.example.pokedex.ui.PokemonDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apollographql.apollo.sample.fragment.PokemonDtl
import com.bumptech.glide.Glide
import com.example.pokedex.PokeDexApplication
import com.example.pokedex.PokeDexViewModelFactory
import com.example.pokedex.R
import com.example.pokedex.ui.PokemonDetail.bottomSheet.AttackBottomSheet
import com.example.pokedex.ui.PokemonDetail.bottomSheet.EvolutionBottomSheet
import com.example.pokedex.ui.PokemonDetail.bottomSheet.PhysicalBottomSheet
import com.example.pokedex.utils.StateEnum
import kotlinx.android.synthetic.main.activity_pokemon_detail.*

class PokemonDetailActivity : AppCompatActivity() {
    private val pokemonDtlViewModel by lazy { ViewModelProviders.of(this, PokeDexViewModelFactory(application as PokeDexApplication)).get(PokemonDtlViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        val pokeId = intent.getStringExtra("ID")
        pokemonDtlViewModel.getPokemonDetailById(pokeId)
            pokemonDtlViewModel.pokemonDtlLiveData.observe(this, Observer {
            when(it.status){
                StateEnum.SUCCESS->{
                    showData()
                    setupUi(it.data)
                }
                StateEnum.FAILED->{
                    showError(it.message)

                }
                StateEnum.LOADING->{
                    showLoading()
                }
            }
        })
    }

    private fun showLoading() {
        pkmnDtlErrorMsg.visibility = View.GONE
        pkmnDtlPB.visibility = View.VISIBLE
        pkmnDtlLyt.visibility = View.GONE
    }

    private fun showError(message: String?) {
        pkmnDtlErrorMsg.visibility = View.VISIBLE
        pkmnDtlPB.visibility = View.GONE
        pkmnDtlLyt.visibility = View.GONE
        pkmnDtlErrorMsg.text = message
    }

    private fun showData() {
        pkmnDtlErrorMsg.visibility = View.GONE
        pkmnDtlPB.visibility = View.GONE
        pkmnDtlLyt.visibility = View.VISIBLE
    }

    private fun setupUi(pokemonDtl: PokemonDtl?) {

        pokemonDtl?.let {

            pokemonDtlViewModel.pokemonDtl = it

            pkmnTitleName.text = it.name()
            pkmnNumber.text = it.number()
            pkmnMaxCpValue.text = it.maxCP().toString()
            pkmnMaxHpValue.text = it.maxHP().toString()
            pkmnResistValue.text = it.resistant()?.joinToString()
            pkmnTypeValue.text = it.resistant()?.joinToString ()
            pkmnWeaknessValue.text = it.weaknesses()?.joinToString()
            pkmnclassValue.text = it.classification()

            Glide.with(this)
                .load(it.image())
                .into(pkmnImgView)

            pkmnAttackBtn.setOnClickListener {
                val attackBottomSheet = AttackBottomSheet()
                attackBottomSheet.show(supportFragmentManager, AttackBottomSheet.TAG)
            }



            pkmnEvolutionBtn.setOnClickListener {

                if (pokemonDtl.evolutions().isNullOrEmpty()){

                    Toast.makeText(this, "Sorry!, This Pokemon has no further evolutions", Toast.LENGTH_SHORT).show()

                }else{
                    val evolutionBottomSheet = EvolutionBottomSheet()
                    evolutionBottomSheet.show(supportFragmentManager, EvolutionBottomSheet.TAG)
                }
            }

            pkmnPhyBtn.setOnClickListener {
                val physicalBottomSheet = PhysicalBottomSheet()
                physicalBottomSheet.show(supportFragmentManager, PhysicalBottomSheet.TAG)
            }

        }



    }
}
