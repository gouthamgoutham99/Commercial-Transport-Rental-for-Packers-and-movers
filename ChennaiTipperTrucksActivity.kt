package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChennaiTipperTrucksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_tipper_trucks_chennai)

        // Receive dates and times from TruckSearchActivity
        val startDate = intent.getStringExtra("startDate") ?: ""
        val pickupTime = intent.getStringExtra("pickupTime") ?: ""
        val endDate = intent.getStringExtra("endDate") ?: ""
        val dropTime = intent.getStringExtra("dropTime") ?: ""

        // Truck 1 Button
        findViewById<Button>(R.id.bookNow1)?.setOnClickListener {
            val intent = Intent(this@ChennaiTipperTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "TATA Tipper")
            intent.putExtra("truckPrice", "₹ 30 / Km")
            intent.putExtra("pricePerKm", 30.0)
            intent.putExtra("truckStyle", "Tata BS6 Tipper")
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
            val intent = Intent(this@ChennaiTipperTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "BHARAT BENZ")
            intent.putExtra("truckPrice", "₹ 40 / Km")
            intent.putExtra("pricePerKm", 40.0)
            intent.putExtra("truckStyle", "BHARAT BENZ DE210BS4")
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
            val intent = Intent(this@ChennaiTipperTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "ASHOK LEYLAND")
            intent.putExtra("truckPrice", "₹ 40 / Km")
            intent.putExtra("pricePerKm", 40.0)
            intent.putExtra("truckStyle", "Ashok leyland Tipper 3118")
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
