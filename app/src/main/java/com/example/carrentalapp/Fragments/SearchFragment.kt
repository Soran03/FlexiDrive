package com.example.carrentalapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrentalapp.Adapters.SearchCarsAdapter
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import com.example.carrentalapp.ViewModels.SearchViewModel
import com.example.carrentalapp.ViewModels.SearchViewModelFactory
import com.example.carrentalapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    private lateinit var searchCarsAdapter: SearchCarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbRepo = DatabaseRepository()
        val authRepo = AuthenticationRepository()
        val factory = SearchViewModelFactory(dbRepo, authRepo)
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchCars()
    }

    private fun setupRecyclerView() {
        searchCarsAdapter = SearchCarsAdapter(emptyList())
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchCarsAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.availableCars.observe(viewLifecycleOwner) { cars ->
            searchCarsAdapter.updateCarList(cars)
            binding.searchFilterCarNumText.text = "${cars.size} Vehicles Available"
        }


    }

}