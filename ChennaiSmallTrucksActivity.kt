package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChennaiSmallTrucksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_small_trucks_chennai)

        // Receive dates and times from TruckSearchActivity
        val startDate = intent.getStringExtra("startDate") ?: ""
        val pickupTime = intent.getStringExtra("pickupTime") ?: ""
        val endDate = intent.getStringExtra("endDate") ?: ""
        val dropTime = intent.getStringExtra("dropTime") ?: ""

        // Truck 1 Button
        findViewById<Button>(R.id.bookNow1)?.setOnClickListener {
            val intent = Intent(this@ChennaiSmallTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "TATA Intra V30")
            intent.putExtra("truckPrice", "₹ 15 / Km")
            intent.putExtra("pricePerKm", 15.0)
            intent.putExtra("truckStyle", "TATA Intra V30 - The Smart Pickup")
            intent.putExtra("truckType", "Manual")
            intent.putExtra("truckColor", "Blue")

            intent.putExtra("startDate", startDate)
            intent.putExtra("pickupTime", pickupTime)
            intent.putExtra("endDate", endDate)
            intent.putExtra("dropTime", dropTime)

            startActivity(intent)
        }

        // Truck 2 Button
        findViewById<Button>(R.id.bookNow2)?.setOnClickListener {
            val intent = Intent(this@ChennaiSmallTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "TATA Ace")
            intent.putExtra("truckPrice", "₹ 16 / Km")
            intent.putExtra("pricePerKm", 16.0)
            intent.putExtra("truckStyle", "TATA Ace Zip XL Mini truck")
            intent.putExtra("truckType", "Manual")
            intent.putExtra("truckColor", "Cream")

            intent.putExtra("startDate", startDate)
            intent.putExtra("pickupTime", pickupTime)
            intent.putExtra("endDate", endDate)
            intent.putExtra("dropTime", dropTime)

            startActivity(intent)
        }

        // Truck 3 Button
        findViewById<Button>(R.id.bookNow3)?.setOnClickListener {
            val intent = Intent(this@ChennaiSmallTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Maruti Suzuki")
            intent.putExtra("truckPrice", "₹ 14 / Km")
            intent.putExtra("pricePerKm", 14.0)
            intent.putExtra("truckStyle", "Maruti Suzuki Super Carry mini")
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
