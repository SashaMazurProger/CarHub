package com.sashamprog.carhub.ui.features.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sashamprog.carhub.domain.model.Car
import com.sashamprog.carhub.domain.repository.CarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CatalogState {
    object Loading : CatalogState()
    data class Loaded(val items: List<Car>) : CatalogState()
    data class Error(val message: String) : CatalogState()
}

class CatalogViewModel(private val carRepository: CarRepository) : ViewModel() {
    private val _catalogState = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val catalogState: StateFlow<CatalogState> = _catalogState

    init {
        viewModelScope.launch {
            carRepository.getCars().collect {
                _catalogState.value = CatalogState.Loaded(it)
            }
        }
    }
}