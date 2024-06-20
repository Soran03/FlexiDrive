package com.example.carrentalapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.carrentalapp.DataModels.ResultModel
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.Repositories.DatabaseRepository
import com.example.carrentalapp.ViewModelFactories.SignUpViewModelFactory
import com.example.carrentalapp.ViewModels.SignUpViewModel
import com.example.carrentalapp.databinding.ActivitySignUpBinding

class SignUpActivity : ComponentActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            binding = ActivitySignUpBinding.inflate(layoutInflater)
            setContentView(binding.root)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


            val authRepo = AuthenticationRepository()
            val dbRepo = DatabaseRepository()
            viewModel = ViewModelProvider(this, SignUpViewModelFactory(authRepo, dbRepo))
                .get(SignUpViewModel::class.java)

            setButtonListeners()
        }
    }

    private fun setButtonListeners() {
        binding.signupBackBtn.setOnClickListener {
            finish()
        }

        binding.signupRegisterBtn.setOnClickListener {
            if (checkEntryFields()) signUp()
        }
    }

    private fun checkEntryFields(): Boolean {
        val fullName = binding.signupFullnameInputEditText.text.toString().trim()
        val email = binding.signupEmailInputEditText.text.toString().trim()
        val password = binding.signupPasswordInputEditText.text.toString().trim()
        val confPassword = binding.signupConfPasswordInputEditText.text.toString().trim()

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun signUp() {
        val email = binding.signupEmailInputEditText.text.toString().trim()
        val password = binding.signupPasswordInputEditText.text.toString().trim()
        val fullName = binding.signupFullnameInputEditText.text.toString().trim()

        viewModel.signUpAndSaveUserData(email, password, fullName)
        viewModel.signUpResult.observe(this) {result ->
            when (result) {
                is ResultModel.Success -> {
                    val intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is ResultModel.Error -> {
                    Toast.makeText(this, result.exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
