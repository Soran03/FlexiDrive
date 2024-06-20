package com.example.carrentalapp.DataModels

import java.util.Date
import java.sql.Timestamp

data class RentalModel(
    val rentalId: String = "n/a",
    val userId: String = "n/a",
    val carId: String = "n/a",
    val rentalDate: Date = Date(946684800),
    val expectedReturnDate: Date = Date(946684800),
    val actualReturnDate: Date = Date(946684800)
)
