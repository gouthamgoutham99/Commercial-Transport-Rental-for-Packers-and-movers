package com.simats.app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import kotlin.math.*

class TruckSearchActivity : AppCompatActivity() {

    private lateinit var citySpinner: AutoCompleteTextView
    private lateinit var addressEditText: EditText
    private lateinit var toLocationEditText: EditText
    private lateinit var bodyTypeSpinner: AutoCompleteTextView
    private lateinit var pickupDate: EditText
    private lateinit var pickupTime: EditText
    private lateinit var searchButton: Button

    private lateinit var profileButton: ImageButton
    private lateinit var myBookingsButton: ImageButton
    private lateinit var contactUsButton: ImageButton
    private lateinit var aboutUsButton: ImageButton

    private val cities = listOf("Chennai", "Bengaluru")

    private val truckTypeMap = mapOf(
        "Pickup Truck" to "Pickup Truck (2 Ton Capacity)",
        "Tipper Truck" to "Tipper Truck (2 to 16 Ton Capacity)",
        "Small Truck" to "Small Truck (600 Kg to 3 Ton Capacity)",
        "Box Truck" to "Box Truck (3.5 to 10 Ton Capacity)",
        "Flatbed Truck" to "Flatbed Truck (10 to 50 Ton Capacity)"
    )

    private val GEOAPIFY_API_KEY = "41b2cacad6ba46d6b2c8882548f68182" // replace with your key
    private val TAG = "TruckSearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truck_search)

        // Bind views
        citySpinner = findViewById(R.id.citySpinner)
        addressEditText = findViewById(R.id.addressEditText)
        toLocationEditText = findViewById(R.id.toLocationEditText)
        bodyTypeSpinner = findViewById(R.id.bodyTypeSpinner)
        pickupDate = findViewById(R.id.pickupDate)
        pickupTime = findViewById(R.id.pickupTime)
        searchButton = findViewById(R.id.searchButton)

        profileButton = findViewById(R.id.profileButton)
        myBookingsButton = findViewById(R.id.myBookingsButton)
        contactUsButton = findViewById(R.id.contactUsButton)
        aboutUsButton = findViewById(R.id.aboutUsButton)

        setupDropdowns()
        setupDatePickers()
        setupTimePickers()
        setupSearchButton()
        setupBottomNavigation()
    }

    private fun setupDropdowns() {
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)
        citySpinner.setAdapter(cityAdapter)
        citySpinner.setOnClickListener { citySpinner.showDropDown() }

        val truckAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, truckTypeMap.values.toList())
        bodyTypeSpinner.setAdapter(truckAdapter)
        bodyTypeSpinner.setOnClickListener { bodyTypeSpinner.showDropDown() }
    }

    private fun setupDatePickers() {
        val today = Calendar.getInstance()

        pickupDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val formatted = String.format("%04d-%02d-%02d", year, month + 1, day)
                    pickupDate.setText(formatted)
                },
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = today.timeInMillis
            }.show()
        }
    }

    private fun setupTimePickers() {
        val calendar = Calendar.getInstance()
        pickupTime.setOnClickListener {
            TimePickerDialog(
                this,
                { _, hour, minute -> pickupTime.setText(String.format("%02d:%02d", hour, minute)) },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun setupSearchButton() {
        searchButton.setOnClickListener {
            val city = citySpinner.text.toString().trim()
            val fromAddress = addressEditText.text.toString().trim()
            val toAddress = toLocationEditText.text.toString().trim()
            val bodyTypeLabel = bodyTypeSpinner.text.toString().trim()
            val bodyType = truckTypeMap.filterValues { it == bodyTypeLabel }.keys.firstOrNull() ?: ""
            val pDate = pickupDate.text.toString().trim()
            val pTime = pickupTime.text.toString().trim()

            if (city.isEmpty() || fromAddress.isEmpty() || toAddress.isEmpty() ||
                bodyType.isEmpty() || pDate.isEmpty() || pTime.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d(TAG, "Searching with from=$fromAddress, to=$toAddress, city=$city")

            // First geocode both addresses
            geocodeAddress(fromAddress, city) { fromLat, fromLon ->
                if (fromLat == null || fromLon == null) {
                    Toast.makeText(this, "Failed to geocode pickup address", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Failed to geocode pickup: $fromAddress, city=$city")
                    return@geocodeAddress
                }
                geocodeAddress(toAddress, city) { toLat, toLon ->
                    if (toLat == null || toLon == null) {
                        Toast.makeText(this, "Failed to geocode drop address", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Failed to geocode drop: $toAddress, city=$city")
                        return@geocodeAddress
                    }
                    // Calculate distance (driving). Falls back to Haversine if API has no data.
                    calculateDistance(fromLat, fromLon, toLat, toLon) { distanceKm ->
                        val finalKm = distanceKm ?: haversineKm(fromLat, fromLon, toLat, toLon).also {
                            Log.w(TAG, "Falling back to Haversine distance: $it km")
                        }

                        if (finalKm.isNaN() || finalKm <= 0.0) {
                            Toast.makeText(this, "Failed to calculate distance", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "Distance calculation failed: from=($fromLat,$fromLon) to=($toLat,$toLon)")
                        } else {
                            Log.d(TAG, "Distance calculated = $finalKm km")
                            SearchData.setSearchDetails(pDate, "", pTime, "$fromAddress → $toAddress")
                            SearchData.distanceKm = finalKm

                            when (city) {
                                "Chennai" -> when (bodyType) {
                                    "Pickup Truck" -> startActivity(Intent(this, ChennaiPickupTrucksActivity::class.java))
                                    "Tipper Truck" -> startActivity(Intent(this, ChennaiTipperTrucksActivity::class.java))
                                    "Small Truck" -> startActivity(Intent(this, ChennaiSmallTrucksActivity::class.java))
                                    "Box Truck" -> startActivity(Intent(this, ChennaiBoxTrucksActivity::class.java))
                                    "Flatbed Truck" -> startActivity(Intent(this, ChennaiFlatbedTrucksActivity::class.java))
                                    else -> showInvalid()
                                }
                                "Bengaluru" -> when (bodyType) {
                                    "Pickup Truck" -> startActivity(Intent(this, BengaluruPickupTrucksActivity::class.java))
                                    "Tipper Truck" -> startActivity(Intent(this, BengaluruTipperTrucksActivity::class.java))
                                    "Small Truck" -> startActivity(Intent(this, BengaluruSmallTrucksActivity::class.java))
                                    "Box Truck" -> startActivity(Intent(this, BengaluruBoxTrucksActivity::class.java))
                                    "Flatbed Truck" -> startActivity(Intent(this, BengaluruFlatbedTrucksActivity::class.java))
                                    else -> showInvalid()
                                }
                                else -> Toast.makeText(this, "Please select a valid city", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    /** ✅ Forward geocoding with Geoapify (parses GeoJSON correctly) */
    private fun geocodeAddress(address: String, city: String, callback: (Double?, Double?) -> Unit) {
        try {
            val fullAddress = "$address, $city"
            val encoded = URLEncoder.encode(fullAddress, "UTF-8")
            val url = "https://api.geoapify.com/v1/geocode/search?text=$encoded&apiKey=$GEOAPIFY_API_KEY"

            Log.d(TAG, "Geocoding: $fullAddress -> $url")

            val request = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    try {
                        val features = response.optJSONArray("features")
                        if (features != null && features.length() > 0) {
                            val geometry = features.getJSONObject(0).optJSONObject("geometry")
                            val coords = geometry?.optJSONArray("coordinates")
                            if (coords != null && coords.length() >= 2) {
                                val lon = coords.getDouble(0)
                                val lat = coords.getDouble(1)
                                Log.d(TAG, "Geocoded $fullAddress -> lat=$lat, lon=$lon")
                                callback(lat, lon)
                            } else {
                                Log.e(TAG, "Coordinates missing in geocode response for $fullAddress")
                                callback(null, null)
                            }
                        } else {
                            Log.e(TAG, "No geocode results for $fullAddress")
                            callback(null, null)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Parse error geocode: ${e.message}")
                        callback(null, null)
                    }
                },
                { error ->
                    Log.e(TAG, "Geocode API error for $fullAddress: ${error.message}")
                    callback(null, null)
                }
            )
            Volley.newRequestQueue(this).add(request)
        } catch (e: Exception) {
            Log.e(TAG, "Exception in geocodeAddress: ${e.message}")
            callback(null, null)
        }
    }

    /** ✅ Driving distance via Geoapify Route Matrix (no route polyline). */
    private fun calculateDistance(
        fromLat: Double, fromLon: Double, toLat: Double, toLon: Double,
        callback: (Double?) -> Unit
    ) {
        val url = "https://api.geoapify.com/v1/routematrix?apiKey=$GEOAPIFY_API_KEY"

        val sourceLoc = JSONArray().put(fromLon).put(fromLat)
        val targetLoc = JSONArray().put(toLon).put(toLat)

        val sources = JSONArray().put(JSONObject().put("location", sourceLoc))
        val targets = JSONArray().put(JSONObject().put("location", targetLoc))

        val body = JSONObject()
            .put("mode", "drive")
            .put("type", "short")
            .put("units", "metric")
            .put("sources", sources)
            .put("targets", targets)

        Log.d(TAG, "Requesting distance JSON: $body")

        val request = JsonObjectRequest(Request.Method.POST, url, body,
            { response ->
                try {
                    val matrix = response.optJSONArray("sources_to_targets")
                    if (matrix != null && matrix.length() > 0) {
                        val row0 = matrix.optJSONArray(0)
                        if (row0 != null && row0.length() > 0) {
                            val cell = row0.optJSONObject(0)
                            val meters = cell?.optDouble("distance", Double.NaN) ?: Double.NaN
                            val seconds = cell?.optDouble("time", Double.NaN) ?: Double.NaN
                            Log.d(TAG, "Matrix cell -> distance(m)=$meters, time(s)=$seconds")

                            if (!meters.isNaN() && meters > 0) {
                                callback(meters / 1000.0)
                                return@JsonObjectRequest
                            }
                        }
                    }
                    Log.e(TAG, "No distance data in response: $response")
                    callback(null)
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing distance response: ${e.message}")
                    callback(null)
                }
            },
            { error ->
                Log.e(TAG, "Distance API error: ${error.message}")
                callback(null)
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

    /** 📏 Local fallback: great-circle distance in km (if API returns nothing). */
    private fun haversineKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0088
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    private fun setupBottomNavigation() {
        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        myBookingsButton.setOnClickListener {
            startActivity(Intent(this, UserBookingHistoryActivity::class.java))
        }

        contactUsButton.setOnClickListener {
            startActivity(Intent(this, ContactUsActivity::class.java))
        }

        aboutUsButton.setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
        }
    }

    private fun showInvalid() {
        Toast.makeText(this, "Invalid selection", Toast.LENGTH_SHORT).show()
    }
}
