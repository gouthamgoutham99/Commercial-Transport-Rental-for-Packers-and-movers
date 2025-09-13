package com.simats.app

object SearchData {
    var startDate: String = ""
    var endDate: String = ""
    var pickupTime: String = ""
    var address: String = ""
    var distanceKm: Double = 0.0
    var pricePerKm: Double = 0.0   // 👈 added for truck rate

    fun setSearchDetails(
        start: String,
        end: String,
        pickup: String,
        addr: String
    ) {
        startDate = start
        endDate = end
        pickupTime = pickup
        address = addr
    }
}
