package com.example.flipicklms

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.flipicklms.Adapters.ViewAllAdapter
import com.example.flipicklms.Resources.Data
import com.example.flipicklms.Resources.SharedPrefManager
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {
    private var instanceShared: SharedPrefManager? = null
    var collageID: String = ""
    var studentID: String = ""
    lateinit var imageBack:ImageView
    lateinit var iconSearch:ImageView
    var dataList = arrayListOf<Data>()
    lateinit var editTextSearch:EditText
    lateinit var textSearchresult:TextView
    lateinit var recyclerSearch: RecyclerView
    private lateinit var viewAllAdapter: ViewAllAdapter
    lateinit var progressSearch:ProgressBar
    var showMessage =true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        imageBack=findViewById(R.id.imageBack)
        iconSearch=findViewById(R.id.iconSearch)
        editTextSearch=findViewById(R.id.editTextSearch)
        recyclerSearch=findViewById(R.id.recyclerSearch)
        progressSearch=findViewById(R.id.progressSearch)
        instanceShared = SharedPrefManager(applicationContext)
        textSearchresult=findViewById(R.id.textSearchresult)


        val metrics = DisplayMetrics()
        windowManager
            .defaultDisplay
            .getMetrics(metrics)
        val yInches = metrics.heightPixels / metrics.ydpi
        val xInches = metrics.widthPixels / metrics.xdpi
        val diagonalInches = Math.sqrt((xInches * xInches + yInches * yInches).toDouble())
        if (diagonalInches >= 6.5) {
            // Recycler View Properties
            recyclerSearch.layoutManager = GridLayoutManager(applicationContext,3)
        } else {
            recyclerSearch.layoutManager = GridLayoutManager(applicationContext,2)
        }
        val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerSearch.addItemDecoration(decoration)
        recyclerSearch.setHasFixedSize(true)

        imageBack.setOnClickListener { finish() }


        instanceShared = SharedPrefManager(applicationContext)
//        studentID = instanceShared!!.mobid
//        collageID = instanceShared!!.data


        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                if (s.isNotEmpty()) {
                    var searchText= editTextSearch.text.toString().trim()
                    if (instanceShared?.collegeId==null) {
                    }
                    else{
                        studentID = instanceShared?.studentID.toString()
                        collageID = instanceShared?.collegeId.toString()
                        loadDataforSearchInLogin(searchText,MainActivity.dataModelArrayList)
                    }

                }
                else if (s.isEmpty())
                {
                    dataList.clear()
                    viewAllAdapter =
                        ViewAllAdapter(
                            applicationContext, dataList
                        )
                    recyclerSearch.adapter = viewAllAdapter
                    viewAllAdapter.notifyDataSetChanged()
                    textSearchresult.setText("search Result for")

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })

        iconSearch.setOnClickListener {
          var searchText= editTextSearch.text.toString().trim();

            if (instanceShared?.collegeId==null) {
            }
            else{
                studentID = instanceShared?.studentID.toString()
                collageID = instanceShared?.collegeId.toString()
                loadDataforSearchInLogin(searchText,MainActivity.dataModelArrayList)
            }
        }

    }

    fun loadDataforSearchInLogin(searchText: String?, homeList: ArrayList<Data>, )
    {
        progressSearch.visibility = View.VISIBLE
        dataList.clear()
        Log.d("zzz", "LoginData")
        for (k in 0 until homeList.size) {
            Log.d("zzz","Search Resul"+ searchText)
            if (homeList[k].courseName.contains(searchText.toString(),true))
            {
                showMessage=false
                val showModel = Data()
                showModel.courseName = homeList[k].courseName
                showModel.courseDescription = homeList[k].courseDescription
                showModel.courseThumbnail = homeList[k].courseThumbnail
                showModel.ageGuidance = homeList[k].ageGuidance
                showModel.videoQuality = homeList[k].videoQuality
                showModel.videoURL = homeList[k].videoURL
                showModel.courseId=homeList[k].courseId
                if (homeList[k].courseHours.equals("")) {
                    showModel.courseHours = "00:00:00"
                } else {
                    showModel.courseHours = homeList[k].courseHours
                }
                showModel.courseSequence = homeList[k].courseSequence
                dataList.add(showModel)
                textSearchresult.setText("search Result for "+ searchText)
                viewAllAdapter =
                    ViewAllAdapter(
                        this, dataList
                    )
                recyclerSearch.adapter = viewAllAdapter
                viewAllAdapter.notifyDataSetChanged()
            }
        }
        if (dataList.size<=0){
            Toast.makeText(applicationContext,"We didn't find any matches for $searchText",Toast.LENGTH_SHORT).show()
            dataList.clear()
        }
        progressSearch.visibility = View.GONE

    }

    override fun onBackPressed() {
        super.onBackPressed()
        dataList.clear()
    }

}