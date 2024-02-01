package com.example.flipicklms

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LoginAcitvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acitvity)

        val collegeIdEditText = findViewById<EditText>(R.id.college_Id)
        val passwordEditText = findViewById<EditText>(R.id.passText)
        val alreadyHave_acc = findViewById<TextView>(R.id.alreadyHave_acc)
        val sigunp_layout = findViewById<LinearLayout>(R.id.signUp_layout)
        val login_layout = findViewById<LinearLayout>(R.id.login_layout)
        val dont_have_acc = findViewById<TextView>(R.id.dont_have_acc)
        val register_acc = findViewById<TextView>(R.id.register_acc)
        val loginTxt = findViewById<TextView>(R.id.login_acc)

        // Assuming you have a Button for triggering the login process
        val loginButton = findViewById<AppCompatButton>(R.id.login_button)
        loginButton.setOnClickListener {
            val collegeId = collegeIdEditText.text.toString()
            val password = passwordEditText.text.toString()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
        }


        alreadyHave_acc.setOnClickListener {
            sigunp_layout.visibility = View.GONE
            login_layout.visibility = View.VISIBLE
            alreadyHave_acc.visibility = View.GONE
            dont_have_acc.visibility = View.VISIBLE
            register_acc.visibility = View.VISIBLE
            loginTxt.visibility = View.GONE
        }

        loginTxt.setOnClickListener {
            sigunp_layout.visibility = View.GONE
            login_layout.visibility = View.VISIBLE
            alreadyHave_acc.visibility = View.GONE
            dont_have_acc.visibility = View.VISIBLE
            register_acc.visibility = View.VISIBLE
            loginTxt.visibility = View.GONE
        }

        dont_have_acc.setOnClickListener {
            login_layout.visibility = View.GONE
            sigunp_layout.visibility = View.VISIBLE
            alreadyHave_acc.visibility = View.VISIBLE
            dont_have_acc.visibility = View.GONE
            register_acc.visibility = View.GONE
            loginTxt.visibility = View.VISIBLE
        }

        register_acc.setOnClickListener {
            login_layout.visibility = View.GONE
            sigunp_layout.visibility = View.VISIBLE
            alreadyHave_acc.visibility = View.VISIBLE
            dont_have_acc.visibility = View.GONE
            register_acc.visibility = View.GONE
            loginTxt.visibility = View.VISIBLE
        }
    }

}



/*

 */