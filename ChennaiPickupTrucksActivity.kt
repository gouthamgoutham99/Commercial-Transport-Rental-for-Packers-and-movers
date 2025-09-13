package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChennaiPickupTrucksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_pickup_trucks_chennai)

        // Receive dates and times from TruckSearchActivity
        val startDate = intent.getStringExtra("startDate") ?: ""
        val pickupTime = intent.getStringExtra("pickupTime") ?: ""
        val endDate = intent.getStringExtra("endDate") ?: ""
        val dropTime = intent.getStringExtra("dropTime") ?: ""

        // Truck 1 Button
        findViewById<Button>(R.id.bookNow1)?.setOnClickListener {
            val intent = Intent(this@ChennaiPickupTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Mahindra Bolero")
            intent.putExtra("truckPrice", "₹ 17 / Km")
            intent.putExtra("pricePerKm", 17.0)
            intent.putExtra("truckStyle", "Mahindra Bolero Pikup 4×4")
            intent.putExtra("truckType", "Manual")
            intent.putExtra("truckColor", "White")

            intent.putExtra("startDate", startDate)
            intent.putExtra("pickupTime", pickupTime)
            intent.putExtra("endDate", endDate)
            intent.putExtra("dropTime", dropTime)

            startActivity(intent)
        }

        // Truck 2 Button
        findViewById<Button>(R.id.bookNow2)?.setOnClickListener {
            val intent = Intent(this@ChennaiPickupTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Mahindra Imperio")
            intent.putExtra("truckPrice", "₹ 18 / Km")
            intent.putExtra("pricePerKm", 18.0)
            intent.putExtra("truckStyle", "Mahindra Imperio")
            intent.putExtra("truckType", "Manual")
            intent.putExtra("truckColor", "White")

            intent.putExtra("startDate", startDate)
            intent.putExtra("pickupTime", pickupTime)
            intent.putExtra("endDate", endDate)
            intent.putExtra("dropTime", dropTime)

            startActivity(intent)
        }

        // Truck 3 Button
        findViewById<Button>(R.id.bookNow3)?.setOnClickListener {
            val intent = Intent(this@ChennaiPickupTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Tata Yodha Pickup")
            intent.putExtra("truckPrice", "₹ 16 / Km")
            intent.putExtra("pricePerKm", 16.0)
            intent.putExtra("truckStyle", "Tata Yodha Pickup")
            intent.putExtra("truckType", "Manual")
            intent.putExtra("truckColor", "White")

            intent.putExtra("startDate", startDate)
            intent.putExtra("pickupTime", pickupTime)
            intent.putExtra("endDate", endDate)
            intent.putExtra("dropTime", dropTime)

            startActivity(intent)
        }
    }
}
