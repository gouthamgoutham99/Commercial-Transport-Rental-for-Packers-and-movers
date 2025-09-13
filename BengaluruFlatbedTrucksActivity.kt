package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class BengaluruFlatbedTrucksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_flatbed_trucks_bengaluru)

        // Receive dates and times from TruckSearchActivity
        val startDate = intent.getStringExtra("startDate") ?: ""
        val pickupTime = intent.getStringExtra("pickupTime") ?: ""
        val endDate = intent.getStringExtra("endDate") ?: ""
        val dropTime = intent.getStringExtra("dropTime") ?: ""

        // Truck 1 Button
        findViewById<Button>(R.id.bookNow1)?.setOnClickListener {
            val intent = Intent(this@BengaluruFlatbedTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "TATA")
            intent.putExtra("truckPrice", "₹ 70 / Km")
            intent.putExtra("pricePerKm", 70.0)
            intent.putExtra("truckStyle", "Tata Signa 5525.S")
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
            val intent = Intent(this@BengaluruFlatbedTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "BHARAT BENZ")
            intent.putExtra("truckPrice", "₹ 75 / Km")
            intent.putExtra("pricePerKm", 75.0)
            intent.putExtra("truckStyle", "BharatBenz 3723R")
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
