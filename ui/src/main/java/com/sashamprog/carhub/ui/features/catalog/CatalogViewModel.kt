package com.sashamprog.carhub.ui.features.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sashamprog.carhub.domain.model.Car
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CatalogState {
    object Loading : CatalogState()
    data class Loaded(val items: List<Car>) : CatalogState()
    data class Error(val message: String) : CatalogState()
}

class CatalogViewModel : ViewModel() {
    private val _catalogState = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val catalogState: StateFlow<CatalogState> = _catalogState

    init {
        viewModelScope.launch {
            delay(1000)

            val cars = listOf(
                Car(
                    "Toyota",
                    "Camry",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ),
                    2022,
                    15000,
                    "Відмінний стан, повна комплектація."
                ),
                Car(
                    "Honda",
                    "Civic",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ), 2020,
                    30000,
                    "Економний та надійний автомобіль."
                ),
                Car(
                    "Ford",
                    "Mustang",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ), 2021,
                    10000,
                    "Спортивний автомобіль для справжніх цінителів."
                ),
                Car(
                    "BMW",
                    "X5",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ), 2023,
                    5000,
                    "Преміальний кросовер в ідеальному стані."
                ),
                Car(
                    "Mercedes-Benz",
                    "C-Class",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ), 2022,
                    8000,
                    "Комфортний та елегантний седан."
                ),
                Car(
                    "Audi",
                    "A4",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ), 2021,
                    12000,
                    "Спортивний та динамічний автомобіль."
                ),
                Car(
                    "Hyundai",
                    "Tucson",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ), 2023,
                    3000,
                    "Сучасний та практичний кросовер."
                ),
                Car(
                    "Kia",
                    "Sportage",
                    listOf(
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg",
                        "https://static.boredpanda.com/blog/wp-content/uploads/2018/04/5acb63d83493f__700-png.jpg"
                    ), 2022,
                    7000,
                    "Стильний та комфортабельний кросовер."
                )
            )

            _catalogState.value = CatalogState.Loaded(cars)
        }
    }
}