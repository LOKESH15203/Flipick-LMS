package com.example.flipicklms

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.flipicklms.Resources.SharedPrefManager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen : AppCompatActivity() {
    private var instanceShared: SharedPrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val scope = CoroutineScope(Dispatchers.Main)
        instanceShared =
            SharedPrefManager(applicationContext)

        scope.launch {
            // Wait for 3 seconds
            delay(3000)

            // Retrieve email from SharedPreferences
            val email = instanceShared?.email?.toString() ?: ""

            // Decide which activity to start based on the presence of email
            val targetActivity = if (email.isNotEmpty()) {
                MainActivity::class.java
            } else {
                LoginAcitvity::class.java
            }

            // Start the appropriate activity and finish the current one
            startActivity(Intent(applicationContext, targetActivity))
            finish()
        }


    }
}