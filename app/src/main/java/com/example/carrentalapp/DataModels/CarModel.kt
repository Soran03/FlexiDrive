package com.example.carrentalapp.DataModels

data class CarModel(
    val carId: String = "n/a",
    val brand: String = "n/a",
    val model: String = "n/a",
    val year: Int = 0,
    val color: String = "n/a",
    val image: String = "n/a",
    val rentalPricePerDay: Double = 0.0,
    val doors: Int = 0,
    val seats: Int = 0,
    val transmission: String = "n/a"
)