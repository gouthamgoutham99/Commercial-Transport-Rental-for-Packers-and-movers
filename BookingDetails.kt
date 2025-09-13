package com.simats.app

data class BookingDetails(
    val truckName: String,
    val style: String,
    val type: String,
    val color: String,
    val address: String,
    val startDate: String,
    val endDate: String,
    val pickupTime: String,
    val totalFare: String
)
