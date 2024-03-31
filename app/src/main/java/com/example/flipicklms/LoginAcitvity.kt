package com.example.flipicklms

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.flipicklms.Resources.Api
import com.example.flipicklms.Resources.SharedPrefManager
import com.example.flipicklms.Resources.VolleySingleton
import org.json.JSONException
import org.json.JSONObject

class LoginAcitvity : AppCompatActivity() {

    lateinit var sharedPref:SharedPreferences
    var userID: String = ""
    var first_lastName: String = ""
    var CollegeId: String = ""
    var StudentId: String = ""
    var LoginName: String = ""
    var UserIdForPrefrence: String = ""
    var EmaiL: String = ""

    lateinit var progressHome: ProgressBar
    lateinit var loginButton: AppCompatButton
    lateinit var etEmailorMobile_login: EditText
    lateinit var login_passText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acitvity)


        etEmailorMobile_login = findViewById(R.id.login_Email_Phone)
        val passwordEditText = findViewById<EditText>(R.id.passText)
        val alreadyHave_acc = findViewById<TextView>(R.id.alreadyHave_acc)
        val sigunp_layout = findViewById<LinearLayout>(R.id.signUp_layout)
        val login_layout = findViewById<LinearLayout>(R.id.login_layout)
        val dont_have_acc = findViewById<TextView>(R.id.dont_have_acc)
        val register_acc = findViewById<TextView>(R.id.register_acc)
        val loginTxt = findViewById<TextView>(R.id.login_acc)
        progressHome = findViewById(R.id.progressHome)
        login_passText = findViewById(R.id.login_passText)



// ################################################################################################################
// ################################################################################################################
// ################################################################################################################
// ################################################################################################################
// ################################################################################################################
// ################################################################################################################



        // Assuming you have a Button for triggering the login process
        loginButton = findViewById(R.id.login_button)
//        loginButton.setOnClickListener {
//            val collegeId = etEmailorMobile_login.text.toString()
//            val password = passwordEditText.text.toString()
//
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//        }


        loginButton.setOnClickListener {

            if (etEmailorMobile_login.text.isEmpty() || login_passText.text.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Fields with * are mandatory",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                load_login(etEmailorMobile_login.text.toString().trim())
                SharedPrefManager.getInstance(getApplicationContext()).saveEmail(etEmailorMobile_login.text.toString().trim());
                etEmailorMobile_login.setText("")
                login_layout.visibility=View.GONE
                loginButton.isClickable=false
                progressHome.visibility = View.VISIBLE
            }
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


    private fun load_login(emailOrPhone: String) {
        progressHome.visibility = View.VISIBLE
        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.POST, Api.baseUrl + "User/SignInUser",
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    Log.d("zzz", "load_login called:  $response")
                    val jsonObjectError = jsonObject.getJSONObject("Error")
                    Log.d("zzz", "Status:   " + jsonObjectError.getString("Status"))
                    if (jsonObjectError.getString("Status") == "1") {
                        Toast.makeText(
                            applicationContext,
                            jsonObjectError.getString("Message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        loginButton.isClickable = true
                        etEmailorMobile_login.setText("")
                    }
                    if (jsonObject.has("MCQUser")) {
                        // {"Error":{"ModelErrors":{},"Status":0,"Message":null,"ErrorCode":null},"MCQUser":{"UserId":"200","Email":"vipulshah181990@gmail.com","Password":"5a5V9hsGJrnzSBZI0m33GQ==","FirstName":"Vipul","LastName":"Wani","Zip":null,"Telephone":null,"CourseId":null,"CourseDetails":null,"CollegeThumbnail":null,"CollegeCourseIds":null,"DeviceId":null,"DeviceType":null,"DeviceOSName":null,"DeviceOSVersion":null,"AppVersion":null,"Year":null,"SessionId":null,"IsSessionActive":false,"ActivationCode":null,"State":null,"StudentType":null,"Info1":null,"Info2":null,"CollegeId":"5430","City":null,"Country":null,"Address":null,"CollegeName":"Flipick","Active":null,"IsWelcome":null,"UserImage":null,"UserImageData":null,"UserImageExtension":null,"UserPapersetId":null,"IPAddress":null,"DomainName":null,"ErrorMessage":null,"Stream":null,"Qualification":null,"StreamOption":null,"IsOffline":null,"AppName":null,"IsPasswordEncrypted":null,"MiddleName":null,"RegistrationNumber":null,"CourseExamToAttach":null,"GuardianAadharNo":null,"StudentBirthDate":null,"ShowCollegeBatchPage":null,"IsCourseAttached":null,"GroupId":"4","StudentLandingPage":null,"OrderNo":null,"ProductSKU":null,"SmallCollegeThumbnail":null,"IsEncryptedData":null,"InstituteName":null,"IsDefaultInstitute":null,"UserType":"Student","LoginName":"vipulshah181990@gmail.com"},"UserOTP":null}
                        // "CollegeId":"5430"
                        // "UserId":"200"
                        val jsonObjectMCQ = jsonObject.getJSONObject("MCQUser")
                        first_lastName =
                            jsonObjectMCQ.getString("FirstName") + jsonObjectMCQ.getString("LastName")
                        CollegeId = jsonObjectMCQ.getString("CollegeId")
                        SharedPrefManager.getInstance(applicationContext).saveCollegeId(CollegeId);
                        StudentId = jsonObjectMCQ.getString("UserId")
                        SharedPrefManager.getInstance(applicationContext).saveStudentID(StudentId)
                        LoginName = jsonObjectMCQ.getString("LoginName")
                        UserIdForPrefrence = jsonObjectMCQ.getString("UserId")
                        EmaiL = jsonObjectMCQ.getString("Email")
                        userID = jsonObjectMCQ.getString("UserId")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        progressHome.visibility = View.GONE
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressHome.visibility = View.GONE

                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressHome.visibility = View.GONE
                }
            },
            Response.ErrorListener {
                if (applicationContext != null) {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                return headers
            }

            override fun getParams(): Map<String, String> {
                val hashMap: HashMap<String, String> = HashMap()
                hashMap["UserType"] = "Student"
                hashMap["Email"] = emailOrPhone
                hashMap["Password"] = ""
                return hashMap
            }
        }

        stringRequest.setShouldCache(false)
        applicationContext.applicationContext.let {
            VolleySingleton.getInstance(it).addToRequestQueue(stringRequest)
            stringRequest.setRetryPolicy(
                DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            )
        }

    }

}