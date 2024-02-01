

// #################################
// #################################
// EDIT PROFILE ACTIVITY - not implemented
// #################################
// #################################

package com.example.flipicklms

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.flipicklms.Resources.Api
import com.example.flipicklms.Resources.SharedPrefManager
import com.example.flipicklms.Resources.VolleySingleton
import org.json.JSONException
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {
    lateinit var txt_edit: TextView
    lateinit var text_Email: TextView
    lateinit var textFirst_Name: TextView
    lateinit var text_lastName: TextView
    lateinit var text_mobile: TextView
    lateinit var btn_exit:ImageView
    lateinit var progressBarProfile: ProgressBar
    var instanceShared: SharedPrefManager? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        text_Email=findViewById(R.id.text_Email)
        textFirst_Name=findViewById(R.id.textFirst_Name)
        text_lastName=findViewById(R.id.text_lastName)
        text_mobile=findViewById(R.id.text_mobile)
        progressBarProfile=findViewById(R.id.progressBarProfile)
        txt_edit=findViewById(R.id.btn_edit)
        instanceShared =
            SharedPrefManager(applicationContext)
        btn_exit=findViewById(R.id.img_logout)

        txt_edit.setOnClickListener{
//            val intent = Intent(applicationContext, EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_exit.setOnClickListener {
//            instanceShared!!.resetPreferences()
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//            finish()
            showLogoutDialog()
        }

        Log.d("zzz", "PhoneNumber :"+instanceShared!!.phone)
    }

    fun showLogoutDialog() {

        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Would you like to logout?")
            .setPositiveButton("Yes") { dialog, which ->
                instanceShared!!.resetPreferences()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No") { dialog, which ->
               dialog.dismiss()
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        loadProfile()
    }
    private fun loadProfile()
    {
        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.GET, Api.baseUrl +"User/GetUserDetailsByMail?EmailId="+instanceShared!!.phone+"&requestFrom=angular",
            Response.Listener { response ->
                try {
                    val jsonObject= JSONObject(response)
                    Log.d("zzz", "ProfileActivity :$response")
                    text_Email.text=jsonObject.getString("Email")
                    textFirst_Name.text=jsonObject.getString("FirstName")
                    text_lastName.text=jsonObject.getString("LastName")
                    text_mobile.text=jsonObject.getString("Telephone")
                    progressBarProfile.visibility= View.GONE
                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressBarProfile.visibility= View.GONE

                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                progressBarProfile.visibility= View.GONE
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                return headers
            }
        }
        stringRequest.setShouldCache(false)
        applicationContext.let {
            VolleySingleton.getInstance(it).addToRequestQueue(stringRequest)
        }
    }
}