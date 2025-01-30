package com.sashamprog.carhub.domain.model

import java.io.Serializable
import java.text.NumberFormat
import java.util.Locale

data class Car(
    val make: String,
    val model: String,
    val imageUrl: String,
    val year: Int,
    val mileage: Int,
    val description: String,
    val price: Double = 1000.0
) : Serializable {
    val id: String get() = make + model
}

public fun formatPrice(price: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(price)
}