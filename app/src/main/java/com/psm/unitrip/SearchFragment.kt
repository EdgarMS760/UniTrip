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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.unitrip.adapters.PostItemAdapter
import com.psm.unitrip.providers.PostITemProvider



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


        val postAdapter = PostItemAdapter(PostITemProvider.PostList) { postItem ->
            findNavController().navigate(R.id.action_searchFragment_to_chatsFragment)
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerListSearch)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postAdapter

        return root
    }


}