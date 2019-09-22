package com.example.pokedex.ui.PokemonDetail.bottomSheet


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import com.example.pokedex.PokeDexApplication
import com.example.pokedex.PokeDexViewModelFactory

import com.example.pokedex.R
import com.example.pokedex.ui.PokemonDetail.PokemonDtlViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_physical_bottom_sheet.*


/**
 * A simple [Fragment] subclass.
 *
 */
class PhysicalBottomSheet : BottomSheetDialogFragment() {

    private val pokemonDtlViewModel by lazy { ViewModelProviders.of(activity!!, PokeDexViewModelFactory(activity!!.application as PokeDexApplication)).get(
        PokemonDtlViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_physical_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        maxWeightValue.text = "Max : " + pokemonDtlViewModel.pokemonDtl?.weight()?.maximum()
        minWeightvalue.text = "Min : " +pokemonDtlViewModel.pokemonDtl?.weight()?.minimum()
        maxHeightValue.text = "Max : " + pokemonDtlViewModel.pokemonDtl?.height()?.maximum()
        minHeightValue.text = "Min : " +pokemonDtlViewModel.pokemonDtl?.height()?.minimum()
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
