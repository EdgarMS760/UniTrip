package com.psm.unitrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import android.widget.Spinner
import androidx.core.content.ContextCompat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View = inflater.inflate(R.layout.fragment_search, container, false)

        val filterSpinner = root.findViewById<Spinner>(R.id.filterSpinner)
        val orderSpinner = root.findViewById<Spinner>(R.id.orderSpinner)

        val filterOptions = listOf("Filtrar Por", "Año 2024", "Por Año 2024")
        val orderOptions = listOf("Ordenar Por", "Por Año", "Por ID" , "Por Usuario")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            orderOptions
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        orderSpinner.adapter = adapter

        val adapterF = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            filterOptions
        )

        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        filterSpinner.adapter = adapterF

        return root
    }


}