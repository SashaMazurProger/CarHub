package com.sashamprog.carhub.data.source

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sashamprog.carhub.domain.model.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalCarDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : CarDataSource {
    private val carKey = "car_data"
    private val gson = Gson()

    init {
        if (!sharedPreferences.contains(carKey)) {
            sharedPreferences.edit().putString(carKey, gson.toJson(testCars)).apply()
        }
    }

    override suspend fun getCars(): Flow<List<Car>> = flow {
        if (!sharedPreferences.contains(carKey)) {
            sharedPreferences.edit().putString(carKey, gson.toJson(testCars)).apply()
        }

        val json = sharedPreferences.getString(carKey, null)
        val type = object : TypeToken<List<Car>>() {}.type
        emit(json?.let { gson.fromJson(it, type) } ?: emptyList())
    }

    override suspend fun getCar(carId: String): Flow<Car> = flow {
        val json = sharedPreferences.getString(carKey, null)
        val type = object : TypeToken<List<Car>>() {}.type
        val cars: List<Car> = json?.let { gson.fromJson(it, type) } ?: emptyList()
        emit(cars.find { it.id == carId }
            ?: Car("", "", "", 0, 0, ""))
    }

    override suspend fun addCar(car: Car): Flow<Car> = flow {
        val json = sharedPreferences.getString(carKey, "[]")
        val type = object : TypeToken<MutableList<Car>>() {}.type
        val cars: MutableList<Car> = gson.fromJson(json, type) ?: mutableListOf()
        cars.add(car)
        sharedPreferences.edit().putString(carKey, gson.toJson(cars)).apply()
        emit(car)
    }

    override suspend fun updateCar(carId: String, car: Car): Flow<Car> = flow {
        val json = sharedPreferences.getString(carKey, "[]")
        val type = object : TypeToken<MutableList<Car>>() {}.type
        val cars: MutableList<Car> = gson.fromJson(json, type) ?: mutableListOf()
        val index = cars.indexOfFirst { it.id == carId }
        if (index != -1) {
            cars[index] = car
            sharedPreferences.edit().putString(carKey, gson.toJson(cars)).apply()
            emit(car)
        }
    }

    override suspend fun deleteCar(carId: String): Flow<Unit> = flow {
        val json = sharedPreferences.getString(carKey, "[]")
        val type = object : TypeToken<MutableList<Car>>() {}.type
        val cars: MutableList<Car> = gson.fromJson(json, type) ?: mutableListOf()
        val index = cars.indexOfFirst { it.id == carId }
        if (index != -1) {
            cars.removeAt(index)
            sharedPreferences.edit().putString(carKey, gson.toJson(cars)).apply()
        }
        emit(Unit)
    }

    companion object {
        private val testCars = listOf(
            Car(
                "Toyota",
                "Camry",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2022,
                15000,
                "Відмінний стан, повна комплектація."
            ),
            Car(
                "Honda",
                "Civic",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2020,
                30000,
                "Економний та надійний автомобіль."
            ),
            Car(
                "Ford",
                "Mustang",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2021,
                10000,
                "Спортивний автомобіль для справжніх цінителів."
            ),
            Car(
                "BMW",
                "X5",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2023,
                5000,
                "Преміальний кросовер в ідеальному стані."
            ),
            Car(
                "Mercedes-Benz",
                "C-Class",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2022,
                8000,
                "Комфортний та елегантний седан."
            ),
            Car(
                "Audi",
                "A4",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2021,
                12000,
                "Спортивний та динамічний автомобіль."
            ),
            Car(
                "Hyundai",
                "Tucson",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2023,
                3000,
                "Сучасний та практичний кросовер."
            ),
            Car(
                "Kia",
                "Sportage",
                "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                2022,
                7000,
                "Стильний та комфортабельний кросовер."
            )
        )
    }
}
