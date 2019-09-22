package com.example.pokedex.ui.PokemonDetail.bottomSheet


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.sample.fragment.PokemonDtl
import com.example.pokedex.PokeDexApplication
import com.example.pokedex.PokeDexViewModelFactory

import com.example.pokedex.R
import com.example.pokedex.ui.PokemonDetail.adapters.AttackListAdapter
import com.example.pokedex.ui.PokemonDetail.PokemonDtlViewModel
import com.example.pokedex.ui.PokemonDetail.adapters.SplAttListAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_attack_bottom_sheet.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AttackBottomSheet : BottomSheetDialogFragment(), AttackListAdapter.Interaction,
    SplAttListAdapter.Interaction {
    override fun onItemSelected(position: Int, item: PokemonDtl.Special) {

    }

    override fun onItemSelected(position: Int, item: PokemonDtl.Fast) {

    }

    private val pokemonDtlViewModel by lazy { ViewModelProviders.of(activity!!, PokeDexViewModelFactory(activity!!.application as PokeDexApplication)).get(
        PokemonDtlViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attack_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fastAttackRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            val attackAdapter = AttackListAdapter(this@AttackBottomSheet)
            adapter = attackAdapter
            val fastAttackList = pokemonDtlViewModel.pokemonDtl?.attacks()?.fast()
            fastAttackList?.let {
                attackAdapter.submitList(it)
            }
        }
        splAttackRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            val attackAdapter = SplAttListAdapter(this@AttackBottomSheet)
            adapter = attackAdapter
            val splAttackList = pokemonDtlViewModel.pokemonDtl?.attacks()?.special()
            splAttackList?.let {
                attackAdapter.submitList(it)
            }


        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: BottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet)
                    as FrameLayout?
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet)?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    companion object{
        val TAG: String = PhysicalBottomSheet::class.java.simpleName
    }


}
