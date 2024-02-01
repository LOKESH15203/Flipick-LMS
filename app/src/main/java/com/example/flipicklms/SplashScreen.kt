package com.example.flipicklms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.mlkit.common.sdkinternal.SharedPrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen : AppCompatActivity() {
    var instanceShared: SharedPrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val scope = CoroutineScope(Dispatchers.Main)

// Launch the coroutine
        scope.launch {
            // Wait for 3 seconds
            delay(3000)

            val intent = Intent(applicationContext, LoginAcitvity::class.java)
            startActivity(intent)
            finish()

            // Check if the `mobid` is null or empty
//            if (instanceShared?.mobid == null || instanceShared?.mobid == "") {
//                // Start the login
//                val intent = Intent(applicationContext, LoginAcitvity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
////                 Start the MainActivity
//                val intent = Intent(applicationContext, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
        }
    }
}