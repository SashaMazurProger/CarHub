package com.sashamprog.carhub.ui.features.create_car

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sashamprog.carhub.domain.model.Car
import com.sashamprog.carhub.domain.repository.CarRepository
import kotlinx.coroutines.launch

class CreateEditCarViewModel(private val carRepository: CarRepository) : ViewModel() {
    fun saveCar(oldCar: Car?, newCar: Car) {
        viewModelScope.launch {
            oldCar?.id?.let { carRepository.updateCar(it, newCar).collect {} }
                ?: carRepository.addCar(newCar).collect {}
        }
    }
}