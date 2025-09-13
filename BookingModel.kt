package com.simats.app

data class BookingModel(
    val truck_type: String,
    val pickup_location: String,
    val drop_location: String,
    val booking_date: String,
    val status: String
)
