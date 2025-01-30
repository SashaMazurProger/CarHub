package com.sashamprog.carhub.domain.model

import java.io.Serializable

data class Car(
    val make: String,
    val model: String,
    val imageUrl: String,
    val year: Int,
    val mileage: Int,
    val description: String
) : Serializable {
    val id: String get() = make + model
}