package com.sashamprog.carhub.data.source

import com.sashamprog.carhub.domain.model.Car
import kotlinx.coroutines.flow.Flow

class RemoteCarDataSourceImpl(private val api: CarHubApi) : CarDataSource {

    override suspend fun getCars(): Flow<List<Car>> = handleResponse(api.getCars())

    override suspend fun getCar(carId: String): Flow<Car> = handleResponse(api.getCar(carId))

    override suspend fun addCar(car: Car): Flow<Car> = handleResponse(api.addCar(car))

    override suspend fun updateCar(carId: String, car: Car): Flow<Car> =
        handleResponse(api.updateCar(carId, car))

    override suspend fun deleteCar(carId: String): Flow<Unit> = handleResponse(api.deleteCar(carId))
}