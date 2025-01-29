package com.sashamprog.carhub.domain.model

import java.io.Serializable

data class Car(
    val make: String,
    val model: String,
    val imageUrls: List<String>, // Список URL зображень
    val year: Int,
    val mileage: Int,
    val description: String
) : Serializable