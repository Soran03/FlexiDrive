package com.example.carrentalapp.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.DataModels.ResultModel
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepo: AuthenticationRepository,
    private val dbRepo: DatabaseRepository
) : ViewModel() {

    private val _signUpResult = MutableLiveData<ResultModel<Unit>>()
    val signUpResult: LiveData<ResultModel<Unit>> = _signUpResult

    fun signUpAndSaveUserData(email: String, password: String, fullName: String) {
        viewModelScope.launch {
            val authResult = authRepo.signUp(email, password)
            when (authResult) {
                is ResultModel.Success -> {
                    val uid = authRepo.getCurrentUser()!!.uid
                    val dbResult = dbRepo.saveUserData(uid, fullName, email)
                    when (dbResult) {
                        is ResultModel.Success -> {
                            _signUpResult.value = dbResult
                        }

                        is ResultModel.Error -> {
                            _signUpResult.value = dbResult
                        }
                    }

                }

                is ResultModel.Error -> {
                    _signUpResult.value = authResult
                }
            }
        }
    }

    fun getCurrentUser() = authRepo.getCurrentUser()
}


class SignUpViewModelFactory(
    private val authRepo: AuthenticationRepository,
    private val dbRepo: DatabaseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(authRepo, dbRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}