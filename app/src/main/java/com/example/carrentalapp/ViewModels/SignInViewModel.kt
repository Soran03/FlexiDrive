package com.example.carrentalapp.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.DataModels.ResultModel
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepo: AuthenticationRepository,
    private val dbRepo: DatabaseRepository
) : ViewModel() {

    private val _signInResult = MutableLiveData<ResultModel<AuthResult>>()
    val signInResult: LiveData<ResultModel<AuthResult>> = _signInResult

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val authResult = authRepo.signIn(email, password)
            when (authResult) {
                is ResultModel.Success -> {
                    _signInResult.value = authResult
                }
                is ResultModel.Error -> {
                    _signInResult.value = authResult
                }
            }
        }
    }
}