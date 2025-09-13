package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class BengaluruPickupTrucksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_pickup_trucks_bengaluru)

        // Receive dates and times from TruckSearchActivity
        val startDate = intent.getStringExtra("startDate") ?: ""
        val pickupTime = intent.getStringExtra("pickupTime") ?: ""
        val endDate = intent.getStringExtra("endDate") ?: ""
        val dropTime = intent.getStringExtra("dropTime") ?: ""

        // Truck 1 Button
        findViewById<Button>(R.id.bookNow1)?.setOnClickListener {
            val intent = Intent(this@BengaluruPickupTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Isuzu D-Max")
            intent.putExtra("truckPrice", "₹ 19 / Km")
            intent.putExtra("pricePerKm", 19.0)
            intent.putExtra("truckStyle", "Isuzu D-Max Pickup Truck")
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
            val intent = Intent(this@BengaluruPickupTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Toyota Hilux")
            intent.putExtra("truckPrice", "₹ 20 / Km")
            intent.putExtra("pricePerKm", 20.0)
            intent.putExtra("truckStyle", "Toyota Hilux High 4x4 AT")
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
            val intent = Intent(this@BengaluruPickupTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Mahindra Bolero")
            intent.putExtra("truckPrice", "₹ 18 / Km")
            intent.putExtra("pricePerKm", 18.0)
            intent.putExtra("truckStyle", "Mahindra Maxx Pik Up City 3000")
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
