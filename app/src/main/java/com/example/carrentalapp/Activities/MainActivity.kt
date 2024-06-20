package com.example.carrentalapp.Activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.carrentalapp.Fragments.HomeFragment
import com.example.carrentalapp.Fragments.NotificationsFragment
import com.example.carrentalapp.Fragments.SearchFragment
import com.example.carrentalapp.Fragments.SettingsFragment
import com.example.carrentalapp.R
import com.example.carrentalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)



            replaceFragment(HomeFragment())

            binding.bottomNavigationView.setOnItemSelectedListener {menuItem ->
                when (menuItem.itemId) {
                    R.id.navHome -> {
                        replaceFragment(HomeFragment())
                        true
                    }
                    R.id.navSearch -> {
                        replaceFragment(SearchFragment())
                        true
                    }
                    R.id.navNotifications -> {
                        replaceFragment(NotificationsFragment())
                        true
                    }
                    R.id.navSettings -> {
                        replaceFragment(SettingsFragment())
                        true
                    }
                    else -> false
                }
            }


        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.FragmentContainer.id, fragment).commit()
    }


}
