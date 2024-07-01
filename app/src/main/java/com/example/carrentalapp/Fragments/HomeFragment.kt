package com.example.carrentalapp.Fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.carrentalapp.Adapters.HomeBrandsAdapter
import com.example.carrentalapp.Adapters.HomePopularCarsAdapter
import com.example.carrentalapp.R
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import com.example.carrentalapp.ViewModels.HomeViewModel
import com.example.carrentalapp.ViewModels.HomeViewModelFactory
import com.example.carrentalapp.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import java.util.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var brandAdapter: HomeBrandsAdapter
    private lateinit var popularCarsAdapter: HomePopularCarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbRepo = DatabaseRepository()
        val authRepo = AuthenticationRepository()
        val factory = HomeViewModelFactory(dbRepo, authRepo)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        setupRecyclerView()
        setupDefaultRecentCar()
        observeViewModel()

        viewModel.fetchUserData()
        viewModel.fetchCarBrands()
        viewModel.fetchPopularCars()
    }

    private fun setupRecyclerView() {
        brandAdapter = HomeBrandsAdapter(emptyList())
        binding.homeBrandsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = brandAdapter
        }

        popularCarsAdapter = HomePopularCarsAdapter(emptyList())
        binding.homePopularCarsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = popularCarsAdapter
        }

    }

    private fun setupDefaultRecentCar() {
        binding.sampleHomeRecentCar.homeRecentCarTrueLayout.visibility = View.GONE
        binding.sampleHomeRecentCar.homeRecentCarFalseLayout.visibility = View.VISIBLE
    }


    private fun observeViewModel() {
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            val firstName = user.fullName.split(" ")[0]
            binding.homeWelcomeText.text = "Welcome, ${firstName} \uD83D\uDC4B"
        }

        viewModel.carBrands.observe(viewLifecycleOwner) { brands ->
            brandAdapter.updateBrands(brands)
        }

        viewModel.mostRecentRental.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.sampleHomeRecentCar.homeRecentCarTrueLayout.visibility = View.GONE
                binding.sampleHomeRecentCar.homeRecentCarFalseLayout.visibility = View.VISIBLE
            }
            else {
                binding.sampleHomeRecentCar.homeRecentCarTrueLayout.visibility = View.VISIBLE
                binding.sampleHomeRecentCar.homeRecentCarFalseLayout.visibility = View.GONE

                binding.sampleHomeRecentCar.homeRecentCarBrand.text = it.second.brand
                binding.sampleHomeRecentCar.homeRecentCarModel.text = it.second.model
//                binding.sampleHomeRecentCar.homeRecentCarPrice.text = "£${it.second.rentalPricePerDay}"

                val recentDates = "${formatDateDDMM(it.first.rentalDate)} - ${formatDateDDMM(it.first.expectedReturnDate)}"
//                binding.sampleHomeRecentCar.homeRecentCarDates.text = recentDates

                Picasso.get().load(it.second.image)
                    .placeholder(R.drawable.ic_menu_gallery)
                    .into(binding.sampleHomeRecentCar.homeRecentCarImage)
            }
        }

        viewModel.popularCars.observe(viewLifecycleOwner) { cars ->
            popularCarsAdapter.updateBrands(cars)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            Log.d("HomeError", errorMessage)
        }
    }

    private fun formatDateDDMM(timestamp: Date): String {
        Log.d("HomeError", timestamp.toString())
        val formatter = SimpleDateFormat("dd/MM", Locale.getDefault())
        Log.d("HomeError", formatter.format(timestamp).toString())

        return formatter.format(timestamp)
    }

}