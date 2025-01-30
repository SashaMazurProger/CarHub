package com.sashamprog.carhub.data.source

import com.sashamprog.carhub.domain.model.Car
import kotlinx.coroutines.flow.Flow

interface CarDataSource {

    suspend fun getCars(): Flow<List<Car>>

    suspend fun getCar(carId: String): Flow<Car>

    suspend fun addCar(car: Car): Flow<Car>

    suspend fun updateCar(carId: String, car: Car): Flow<Car>

    suspend fun deleteCar(carId: String): Flow<Unit>
}