package com.simats.app

// Truck data model
data class Truck(
    var name: String,
    var style: String,
    var type: String,
    var color: String,
    var price: String,
    var city: String,
    var bodyType: String
)

// Singleton to hold trucks in-memory
object TruckDataManager {
    private val trucks = mutableListOf<Truck>()

    fun addTruck(truck: Truck) {
        trucks.add(truck)
    }

    fun getTrucksByCityAndBodyType(city: String, bodyType: String): List<Truck> {
        return trucks.filter { it.city == city && it.bodyType == bodyType }
    }

    fun getAllTrucks(): List<Truck> = trucks

    fun updateTruck(index: Int, updatedTruck: Truck) {
        if (index in trucks.indices) {
            trucks[index] = updatedTruck
        }
    }

    fun getTruckAt(index: Int): Truck? = trucks.getOrNull(index)

    fun getIndexOf(truck: Truck): Int = trucks.indexOf(truck)
}
