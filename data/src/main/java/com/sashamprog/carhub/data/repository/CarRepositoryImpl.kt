package com.sashamprog.carhub.data.repository

import com.sashamprog.carhub.domain.model.Car
import com.sashamprog.carhub.domain.repository.CarRepository
import com.sashamprog.carhub.data.source.CarDataSource
import kotlinx.coroutines.flow.Flow

class CarRepositoryImpl(private val carDataSource: CarDataSource) : CarRepository {

    override suspend fun getCars(): Flow<List<Car>> = carDataSource.getCars()

    override suspend fun getCar(carId: String): Flow<Car> = carDataSource.getCar(carId)

    override suspend fun addCar(car: Car): Flow<Car> = carDataSource.addCar(car)

    override suspend fun updateCar(carId: String, car: Car): Flow<Car> =
        carDataSource.updateCar(carId, car)

    override suspend fun deleteCar(carId: String): Flow<Unit> = carDataSource.deleteCar(carId)
}