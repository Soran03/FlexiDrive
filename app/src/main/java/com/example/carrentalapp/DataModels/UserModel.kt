package com.example.carrentalapp.DataModels

data class UserModel(
    val userId: String = "n/a",
    val fullName: String = "n/a",
    val email: String = "n/a",
    val phoneNumber: String = "n/a",
    val profileImage: String = "n/a",
    val rentalHistory: List<String> = emptyList(),
    val userType: String = "Standard"
)
