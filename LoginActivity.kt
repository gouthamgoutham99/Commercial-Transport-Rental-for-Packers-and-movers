package com.simats.app

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordToggle: ImageView
    private lateinit var loginButton: Button
    private lateinit var signupText: TextView

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordToggle = findViewById(R.id.passwordToggle)
        loginButton = findViewById(R.id.loginButton)
        signupText = findViewById(R.id.signupText)

        // Toggle password visibility
        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordToggle.setImageResource(R.drawable.ic_visibility_off)
            } else {
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordToggle.setImageResource(R.drawable.ic_visibility)
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        // Login logic
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = "http://10.0.2.2:8080/truck_rental/login.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                { response ->
                    try {
                        val cleanResponse = response.trim()
                        Log.d("LOGIN_DEBUG", "Server Response: $cleanResponse")

                        val json = JSONObject(cleanResponse)
                        if (json.getString("status") == "success") {
                            val uname = json.getString("username")
                            val phone = json.getString("phoneno")

                            // Save session
                            UserSession.saveUser(this, uname, phone)

                            val intent = if (uname == "admin") {
                                Intent(this, AdminDashboardActivity::class.java)
                            } else {
                                Intent(this, BookNowActivity::class.java)
                            }
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Login Error: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.e("LOGIN_ERROR", "Raw Response: $response")
                    }
                },
                { error ->
                    Toast.makeText(this, "Login Failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    return mapOf("username" to username, "password" to password)
                }
            }

            Volley.newRequestQueue(this).add(stringRequest)
        }

        signupText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
