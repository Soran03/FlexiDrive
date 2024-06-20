package com.example.carrentalapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            binding = ActivityLandingBinding.inflate(layoutInflater)
            setContentView(binding.root)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


            val authRepo = AuthenticationRepository()
            if (authRepo.getCurrentUser()!=null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            setButtonListeners()

        }
    }

    private fun setButtonListeners() {
        binding.landingRegisterBtn.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.landingSignInBtn.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

}
