package com.example.carrentalapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.carrentalapp.DataModels.ResultModel
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import com.example.carrentalapp.ViewModels.SignInViewModel
import com.example.carrentalapp.ViewModels.SignInViewModelFactory
import com.example.carrentalapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            binding = ActivitySignInBinding.inflate(layoutInflater)
            setContentView(binding.root)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            val authRepo = AuthenticationRepository()
            val dbRepo = DatabaseRepository()
            viewModel = ViewModelProvider(this, SignInViewModelFactory(authRepo, dbRepo))
                .get(SignInViewModel::class.java)

            setButtonListeners()
        }
    }

    private fun setButtonListeners() {
        binding.signinBackBtn.setOnClickListener {
            finish()
        }

        binding.signinRegisterBtn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email = binding.signinEmailInputEditText.text.toString().trim()
        val password = binding.signinPasswordInputEditText.text.toString().trim()

        viewModel.signIn(email, password)
        viewModel.signInResult.observe(this) {result ->
            when (result) {
                is ResultModel.Success -> {
                    Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show()
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is ResultModel.Error ->
                    Toast.makeText(this, "Error: ${result.exception.message}", Toast.LENGTH_SHORT).show()

            }
        }

    }
}
