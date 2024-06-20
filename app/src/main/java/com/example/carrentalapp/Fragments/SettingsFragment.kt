package com.example.carrentalapp.Fragments

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carrentalapp.Activities.LandingActivity
import com.example.carrentalapp.R
import com.example.carrentalapp.Repositories.AuthenticationRepository
import com.example.carrentalapp.ViewModels.SettingsViewModel
import com.example.carrentalapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsSignOutBtn.setOnClickListener{
            AuthenticationRepository().signOut()
            val intent = Intent(requireContext(), LandingActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

    }
}