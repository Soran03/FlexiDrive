package com.example.carrentalapp.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.DataModels.CarBrandModel
import com.example.carrentalapp.DataModels.CarModel
import com.example.carrentalapp.DataModels.RentalModel
import com.example.carrentalapp.DataModels.ResultModel
import com.example.carrentalapp.DataModels.UserModel
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dbRepo: DatabaseRepository,
    private val authRepo: AuthenticationRepository
)
    : ViewModel() {

    private val _userData = MutableLiveData<UserModel>()
    val userData: LiveData<UserModel> get() = _userData

    private val _carBrands = MutableLiveData<List<CarBrandModel>>()
    val carBrands: LiveData<List<CarBrandModel>> get() = _carBrands

    private val _mostRecentRental = MutableLiveData<Pair<RentalModel, CarModel>>()
    val mostRecentRental: LiveData<Pair<RentalModel, CarModel>> get() = _mostRecentRental

    private val _popularCars = MutableLiveData<List<CarModel>>()
    val popularCars: LiveData<List<CarModel>> get() = _popularCars

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchUserData() {
        viewModelScope.launch {
            val userResult = dbRepo.fetchUserData(authRepo.getCurrentUser()!!.uid)
            Log.d("fetchUserData", authRepo.getCurrentUser()!!.uid)
            when (userResult) {
                is ResultModel.Success -> {
                    _userData.value = userResult.data
                    try{
                        fetchMostRecentRental(userResult.data.rentalHistory[0])
                    }
                    catch (_: Exception){}
                }
                is ResultModel.Error -> _error.value += "FetchUserData: ${userResult.exception.message}"
            }

        }
    }

    fun fetchCarBrands() {
        viewModelScope.launch {
            val dbResult = dbRepo.fetchCarBrands()
            when (dbResult) {
                is ResultModel.Success -> _carBrands.value = dbResult.data
                is ResultModel.Error -> _error.value += "FetchCarBrands: ${dbResult.exception.message} \n"
            }
        }
    }

    fun fetchPopularCars() {
        viewModelScope.launch {
            val dbResult = dbRepo.fetchAllCars()
            when (dbResult) {
                is ResultModel.Success -> _popularCars.value = dbResult.data.take(3)
                is ResultModel.Error -> _error.value += "FetchPopularCars: ${dbResult.exception.message} \n"
            }
        }
    }

    private fun fetchMostRecentRental(rentalId: String) {
        viewModelScope.launch {
            val rentalResult = dbRepo.fetchMostRecentRental(rentalId)
            when (rentalResult) {
                is ResultModel.Success -> {
                    val rental = rentalResult.data
                    Log.d("fetchMostRecentRental", rental.toString())
                    val carResult = dbRepo.fetchCarDetails(rental.carId)
                    when (carResult) {
                        is ResultModel.Success -> {
                            val car = carResult.data
                            _mostRecentRental.value = Pair(rental, car)
                        }

                        is ResultModel.Error -> _error.value += "FetchCarDetails: ${carResult.exception.message} \n"
                    }
                }

                is ResultModel.Error -> _error.value += "FetchMostRecentRental: ${rentalResult.exception.message} \n"
            }
        }
    }
}


class HomeViewModelFactory(
    private val dbRepo: DatabaseRepository,
    private val authRepo: AuthenticationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dbRepo, authRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}