package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AvailableTrucksActivity : AppCompatActivity() {

    private lateinit var citySpinner: AutoCompleteTextView
    private lateinit var bodyTypeSpinner: AutoCompleteTextView
    private lateinit var btnViewTrucks: Button

    private val cities = listOf("Chennai", "Bengaluru")
    private val bodyTypes = listOf("Pickup Truck", "Tipper Truck", "Small Truck", "Box Truck", "Flatbed Truck")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_trucks)

        citySpinner = findViewById(R.id.citySpinner)
        bodyTypeSpinner = findViewById(R.id.bodyTypeSpinner)
        btnViewTrucks = findViewById(R.id.btnViewTrucks)

        // Set up adapters
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)
        val bodyTypeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, bodyTypes)

        citySpinner.setAdapter(cityAdapter)
        bodyTypeSpinner.setAdapter(bodyTypeAdapter)

        citySpinner.threshold = 1
        bodyTypeSpinner.threshold = 1

        // Optional: show dropdown on click
        citySpinner.setOnClickListener {
            citySpinner.showDropDown()
        }

        bodyTypeSpinner.setOnClickListener {
            bodyTypeSpinner.showDropDown()
        }

        btnViewTrucks.setOnClickListener {
            val selectedCity = citySpinner.text.toString().trim()
            val selectedBodyType = bodyTypeSpinner.text.toString().trim()

            if (selectedCity.isEmpty() || selectedBodyType.isEmpty()) {
                showError()
                return@setOnClickListener
            }

            when (selectedCity) {
                "Chennai" -> when (selectedBodyType) {
                    "Pickup Truck" -> startActivity(Intent(this, ChennaiPickupTrucksActivity::class.java))
                    "Tipper Truck" -> startActivity(Intent(this, ChennaiTipperTrucksActivity::class.java))
                    "Small Truck" -> startActivity(Intent(this, ChennaiSmallTrucksActivity::class.java))
                    "Box Truck" -> startActivity(Intent(this, ChennaiBoxTrucksActivity::class.java))
                    "Flatbed Truck" -> startActivity(Intent(this, ChennaiFlatbedTrucksActivity::class.java))
                    else -> showError()
                }

                "Bengaluru" -> when (selectedBodyType) {
                    "Pickup Truck" -> startActivity(Intent(this, BengaluruPickupTrucksActivity::class.java))
                    "Tipper Truck" -> startActivity(Intent(this, BengaluruTipperTrucksActivity::class.java))
                    "Small Truck" -> startActivity(Intent(this, BengaluruSmallTrucksActivity::class.java))
                    "Box Truck" -> startActivity(Intent(this, BengaluruBoxTrucksActivity::class.java))
                    "Flatbed Truck" -> startActivity(Intent(this, BengaluruFlatbedTrucksActivity::class.java))
                    else -> showError()
                }

                else -> showError()
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Please select valid city and body type", Toast.LENGTH_SHORT).show()
    }
}
