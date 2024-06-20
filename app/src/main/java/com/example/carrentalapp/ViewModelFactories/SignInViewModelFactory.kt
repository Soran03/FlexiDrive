package com.example.carrentalapp.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import com.example.carrentalapp.ViewModels.SignInViewModel

class SignInViewModelFactory(
    private val authRepo: AuthenticationRepository,
    private val dbRepo: DatabaseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(authRepo, dbRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
