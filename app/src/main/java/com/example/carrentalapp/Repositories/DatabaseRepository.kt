package com.example.carrentalapp.Repositories

import com.example.carrentalapp.DataModels.CarBrandModel
import com.example.carrentalapp.DataModels.CarModel
import com.example.carrentalapp.DataModels.RentalModel
import com.example.carrentalapp.DataModels.ResultModel
import com.example.carrentalapp.DataModels.UserModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.sql.Date
import java.sql.Timestamp

class DatabaseRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun saveUserData(userId: String, fullName: String, email: String): ResultModel<Unit> {
        return try {
            val userData = UserModel(userId, fullName, email)
            db.collection("Users").document(userId).set(userData).await()
            ResultModel.Success(Unit)

        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    suspend fun fetchUserData(userId: String): ResultModel<UserModel> {
        return try {
            val result = db.collection("Users").document(userId).get().await()
            val user = result.toObject(UserModel::class.java)
            if (user != null) {
                ResultModel.Success(user)
            } else {
                ResultModel.Error(Exception("User not found"))
            }
        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    suspend fun fetchCarBrands(): ResultModel<List<CarBrandModel>> {
        return try {
            val result = db.collection("CarBrands").get().await()
            val brands = result.toObjects(CarBrandModel::class.java)
            ResultModel.Success(brands)
        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    suspend fun fetchAllCars(): ResultModel<List<CarModel>> {
        return try {
            val result = db.collection("Cars").get().await()
            val cars = result.toObjects(CarModel::class.java)
            ResultModel.Success(cars)
        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    suspend fun saveCarRental(
        userId: String, carId: String, rentalDate: Date, expectedReturnDate: Date)
    : ResultModel<Unit> {
        return try {
            val rentalId = db.collection("RentalHistory").document().id
            val rentalData = RentalModel(rentalId, userId, carId, rentalDate, expectedReturnDate)

            db.collection("RentalHistory").document(rentalId).set(rentalData).await()
            db.collection("Users").document(userId).update("rentalHistory", FieldValue.arrayUnion(rentalId)).await()

            ResultModel.Success(Unit)

        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    suspend fun fetchMostRecentRental(rentalId: String): ResultModel<RentalModel> {
        return try {
            val dbResult = db.collection("RentalHistory").document(rentalId).get().await()
            val rentalData = dbResult.toObject(RentalModel::class.java)

            if (rentalData != null) {
                ResultModel.Success(rentalData)
            } else {
                ResultModel.Error(Exception("Null data"))
            }

        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }

    suspend fun fetchCarDetails(carId: String): ResultModel<CarModel> {
        return try {
            val dbResult = db.collection("Cars").document(carId).get().await()
            val car = dbResult.toObject(CarModel::class.java)
            if (car != null) {
                ResultModel.Success(car)
            } else {
                ResultModel.Error(Exception("Car not found"))
            }
        } catch (e: Exception) {
            ResultModel.Error(e)
        }
    }
 }
