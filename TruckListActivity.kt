package com.simats.app

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class TruckListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truck_list)

        val listView = findViewById<ListView>(R.id.truckListView)

        // Replace with real truck data
        val trucks = listOf(
            "Chennai - Pickup Truck - Red - ₹1500",
            "Bengaluru - Small Truck - Blue - ₹1800",
            "Chennai - Box Truck - White - ₹2000"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trucks)
        listView.adapter = adapter
    }
}
