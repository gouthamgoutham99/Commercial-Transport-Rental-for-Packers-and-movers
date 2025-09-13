package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class BengaluruBoxTrucksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_box_trucks_bengaluru)

        // Receive dates and times from TruckSearchActivity
        val startDate = intent.getStringExtra("startDate") ?: ""
        val pickupTime = intent.getStringExtra("pickupTime") ?: ""
        val endDate = intent.getStringExtra("endDate") ?: ""
        val dropTime = intent.getStringExtra("dropTime") ?: ""

        // Truck 1 Button
        findViewById<Button>(R.id.bookNow1)?.setOnClickListener {
            val intent = Intent(this@BengaluruBoxTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "TATA")
            intent.putExtra("truckPrice", "₹ 50 / Km")
            intent.putExtra("pricePerKm", 50.0)
            intent.putExtra("truckStyle", "TATA LPT 710")
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
            val intent = Intent(this@BengaluruBoxTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Eicher")
            intent.putExtra("truckPrice", "₹ 60 / Km")
            intent.putExtra("pricePerKm", 60.0)
            intent.putExtra("truckStyle", "eicher pro 3008")
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
            val intent = Intent(this@BengaluruBoxTrucksActivity, BookingSummaryActivity::class.java)
            intent.putExtra("truckName", "Ashok Leyland")
            intent.putExtra("truckPrice", "₹ 65 / Km")
            intent.putExtra("pricePerKm", 65.0)
            intent.putExtra("truckStyle", "Ahok Leyland Boss LE and LX truck")
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
