package com.example.carrentalapp.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.DataModels.CarModel
import com.example.carrentalapp.DataModels.ResultModel
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val dbRepo: DatabaseRepository,
    private val authRepo: AuthenticationRepository
) : ViewModel() {

    private val _availableCars = MutableLiveData<List<CarModel>>()
    val availableCars: LiveData<List<CarModel>> get() = _availableCars

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun fetchCars() {
        viewModelScope.launch {
            val dbResult = dbRepo.fetchAllCars()
            when (dbResult) {
                is ResultModel.Success -> _availableCars.value = dbResult.data
                is ResultModel.Error -> _error.value += "FetchPopularCars: ${dbResult.exception.message} \n"
            }
        }
    }

}


class SearchViewModelFactory(
    private val dbRepo: DatabaseRepository,
    private val authRepo: AuthenticationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(dbRepo, authRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}