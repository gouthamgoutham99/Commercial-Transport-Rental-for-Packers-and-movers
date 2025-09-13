package com.simats.app

data class BookingData(
    val orderId: String,
    val username: String,
    val phoneNumber: String,
    val truckName: String,
    val truckPrice: String,
    val truckStyle: String,
    val truckType: String,
    val truckColor: String,
    val address: String,
    val startDate: String,
    val endDate: String,
    val pickupTime: String
)

object BookingStorage {
    val bookings = mutableListOf<BookingData>()

    fun addBooking(booking: BookingData) {
        bookings.add(booking)
    }

    fun getAllBookings(): List<BookingData> = bookings
}
