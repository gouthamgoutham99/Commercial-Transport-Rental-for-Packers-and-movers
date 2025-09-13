package com.simats.app

data class Booking(
    val orderId: String,
    val username: String,
    val truckName: String,
    val truckStyle: String,
    val truckType: String,
    val truckColor: String,
    val totalFare: String,
    val address: String,
    val startDate: String,
    val endDate: String,
    val pickupTime: String
)
