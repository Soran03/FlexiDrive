package com.example.carrentalapp.Repositories

import com.example.carrentalapp.DataModels.ResultModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthenticationRepository {
    private val auth = FirebaseAuth.getInstance()

    suspend fun signUp(email: String, password: String): ResultModel<AuthResult> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            ResultModel.Success(result)
        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    suspend fun signIn(email: String, password: String): ResultModel<AuthResult> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            ResultModel.Success(result)
        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser

}