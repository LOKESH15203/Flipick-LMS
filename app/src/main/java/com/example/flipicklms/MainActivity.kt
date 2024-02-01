// PlayerView is not implemented

package com.example.flipicklms

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.example.flipicklms.Adapters.RecyclerViewWithLogin
import com.example.flipicklms.Resources.Api
import com.example.flipicklms.Resources.Data
import com.example.flipicklms.Resources.HomeModelWithLogin
import com.example.flipicklms.Resources.SharedPrefManager
import com.example.flipicklms.Resources.VolleySingleton
import org.json.JSONException
import org.json.JSONObject
import java.util.LinkedHashMap

class MainActivity : AppCompatActivity() {

    private var instanceShared: SharedPrefManager? = null
    private var finalHashMapToRender = LinkedHashMap<String, java.util.ArrayList<HomeModelWithLogin>>()
    private lateinit var newRecyclerViewAdapter: RecyclerViewWithLogin
    var homeModelArrayList = arrayListOf<HomeModelWithLogin>()

    lateinit var mainImage:ImageView
    lateinit var pager_title: TextView
    lateinit var progresssHome: ProgressBar
    lateinit var recyclerView: RecyclerView
    var trailerUrl:String=""
    var courseId: String = ""
    var collegeID: String = ""


    var slider_modelArrayList: java.util.ArrayList<HomeModelWithLogin> =
        java.util.ArrayList<HomeModelWithLogin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainImage = findViewById(R.id.mainImage)
        pager_title=findViewById(R.id.pager_title)
        progresssHome = findViewById(R.id.progresssHome)
        recyclerView = findViewById(R.id.recycler_login)
        studentID = instanceShared!!.mobid.toString()

    }

    companion object{
        var dataModelArrayList = arrayListOf<Data>()
        var studentID: String = ""
    }


    fun loadDataFromFirstApi() {
        slider_modelArrayList.clear()
        val headerListBoth = ArrayList<String>()
        val homeModelBothApi = ArrayList<HomeModelWithLogin>()
        finalHashMapToRender.clear()
        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.POST,
            Api.baseUrl + "StudentCourseHBI/GetInstituteCourseListRibbonByStudentForApp",
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    Log.d("zzz", "HomeFragment 111:$response")
                    val jsonArray = jsonObject.getJSONArray("CourseList")
                    if (jsonArray.length() > 0) {
                        for (i in 0 until jsonArray.length()) {
                            val homeModel = HomeModelWithLogin()
                            val jsonObjectItems = jsonArray.getJSONObject(i)
                            homeModel.courseId = jsonObjectItems.getString("CourseId")
                            homeModel.courseName = jsonObjectItems.getString("CourseName")
                            homeModel.semesterName = jsonObjectItems.getString("SemesterName")
                            homeModel.semesterSequence = jsonObjectItems.getString("SemesterSequence")
                            homeModel.ribbonName = jsonObjectItems.getString("RibbonName")
                            headerListBoth.add(jsonObjectItems.getString("RibbonName"))
                            homeModel.masterCourseId = jsonObjectItems.getString("MasterCourseId")
                            homeModel.courseDescription = jsonObjectItems.getString("CourseDescription")
                            homeModel.courseHours = jsonObjectItems.getString("CourseHours")
                            homeModel.courseThumbnail = jsonObjectItems.getString("CourseThumbnail")
                            homeModel.courseSequence = jsonObjectItems.getString("CourseSequence")
                            homeModel.courseReleaseDate = jsonObjectItems.getString("CourseReleaseDate")
                            homeModel.showImage = jsonObjectItems.getString("ShowImage")
                            homeModel.showVideo = jsonObjectItems.getString("ShowVideo")
                            homeModel.ageGuidance = jsonObjectItems.getString("AgeGuidance")
                            homeModel.videoQuality = jsonObjectItems.getString("VideoQuality")
                            homeModel.isPackage=jsonObjectItems.getString("IsPackage")
                            Log.d("zzz","package:  "+jsonObjectItems.getString("IsPackage"))
                            homeModel.featured=jsonObjectItems.getString("Featured")
                            val jsonObjectContent =
                                jsonObjectItems.getJSONObject("ContentTypes")
                            val jsonArrayVideos = jsonObjectContent.getJSONArray("Videos")
                            for (j in 0 until jsonArrayVideos.length()) {
                                val jsonObject1 = jsonArrayVideos.getJSONObject(j)
                                homeModel.videoId = jsonObject1.getString("VideoId")
                                homeModel.videoName = jsonObject1.getString("VideoName")
                                homeModel.videoURL = jsonObject1.getString("VideoURL")
                            }
                            homeModelBothApi.add(homeModel)
                            var Featured: String = jsonObjectItems.getString("Featured")
                            if (Featured == "1") {
//                                slider_modelArrayList.add(homeModel)

                                slider_modelArrayList.add(homeModel)
                                Log.d("zzz","Book Url 22:  "+jsonObjectItems.getString("BookUrl"))
                                Glide.with(applicationContext).load(jsonObjectItems.getString("CourseThumbnail"))
                                    .into(mainImage)
//                                    play_FeaturedVideo(jsonObjectItems.getString("BookUrl"))
                                for (j in 0 until jsonArrayVideos.length()) {
                                    val jsonObject1 = jsonArrayVideos.getJSONObject(j)
                                    homeModel.videoId = jsonObject1.getString("VideoId")
                                    homeModel.videoName = jsonObject1.getString("VideoName")
                                    homeModel.videoURL = jsonObject1.getString("VideoURL")

                                    trailerUrl=jsonObject1.getString("VideoURL")
//                                    play_FeaturedVideo(jsonObject1.getString("VideoURL"))

                                }
                                pager_title.setText(jsonObjectItems.getString("CourseName"))
                                courseId=  jsonObjectItems.getString("CourseId");
                                Log.d("zzz","Course Id:  "+jsonObjectItems.getString("CourseId"))
                            }
                            else
                            {
                                for (j in 0 until jsonArrayVideos.length()) {
                                    val jsonObject1 = jsonArrayVideos.getJSONObject(j)
                                    homeModel.videoId = jsonObject1.getString("VideoId")
                                    homeModel.videoName = jsonObject1.getString("VideoName")
                                    homeModel.videoURL = jsonObject1.getString("VideoURL")
                                }
                            }
                        }
                    }
                    loadDataFromSecondApi(headerListBoth, homeModelBothApi)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    progresssHome.visibility = View.GONE
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
                progresssHome.visibility = View.GONE
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return HashMap()
            }

            override fun getParams(): Map<String, String> {
                val hashMap: HashMap<String, String> = HashMap()
                hashMap["StudentId"] = studentID
                hashMap["CollegeId"] = collegeID
                hashMap["ArchiveSearch"] = "0"
                return hashMap
            }
        }
        stringRequest.setShouldCache(false)
        this.applicationContext.let {
            VolleySingleton.getInstance(it).addToRequestQueue(stringRequest)
        }
    }

    fun loadDataFromSecondApi(
        sortedHeaderListBoth: java.util.ArrayList<String>,
        homeModelBothApi: java.util.ArrayList<HomeModelWithLogin>
    ) {
        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.POST,
            Api.baseUrl + "StudentCourseHBI/GetInstituteCourseListByStudentPaging",
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    Log.d("zzz", "HomeFragment:$response")
                    val jsonArray = jsonObject.getJSONArray("StudentCourseList")
                    val tempHeaderToBeSortedBySemesterSequence =
                        java.util.ArrayList<HomeModelWithLogin>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObjectItems = jsonArray.getJSONObject(i)
                        val homeModel = HomeModelWithLogin()
                        homeModel.academies = jsonObjectItems.getString("Academies")
                        homeModel.courseId = jsonObjectItems.getString("CourseId")
                        homeModel.adUrl = jsonObjectItems.getString("AdUrl")
                        homeModel.ageGuidance = jsonObjectItems.getString("AgeGuidance")
                        homeModel.audience = jsonObjectItems.getString("Audience")
                        homeModel.bookUrl = jsonObjectItems.getString("BookUrl")
                        homeModel.contentTypes = jsonObjectItems.getString("ContentTypes")
                        homeModel.corusePackageAlias = jsonObjectItems.getString("CorusePackageAlias")
                        homeModel.semesterId = jsonObjectItems.getString("SemesterId")
                        homeModel.masterCourseId = jsonObjectItems.getString("MasterCourseId")
                        homeModel.semesterName = jsonObjectItems.getString("SemesterName")
                        homeModel.semesterSequence = jsonObjectItems.getString("SemesterSequence")
                        homeModel.ribbonName = jsonObjectItems.getString("SemesterName")
                        homeModel.videoQuality = jsonObjectItems.getString("VideoQuality")
                        homeModel.courseDescription = jsonObjectItems.getString("CourseDescription")
                        homeModel.courseHours = jsonObjectItems.getString("CourseHours")
                        homeModel.courseName = jsonObjectItems.getString("CourseName")
                        homeModel.courseSequence = jsonObjectItems.getString("CourseSequence")
                        homeModel.courseThumbnail = jsonObjectItems.getString("CourseThumbnail")
                        homeModel.isPackage=jsonObjectItems.getString("IsPackage")

                        Log.d("zzz","   package 222:  "+jsonObjectItems.getString("IsPackage"))

                        homeModel.featured=jsonObjectItems.getString("Featured")
                        val jsonObjectContent = jsonObjectItems.getJSONObject("ContentTypes")
                        val jsonArrayVideos = jsonObjectContent.getJSONArray("Videos")
                        for (j in 0 until jsonArrayVideos.length()) {
                            val jsonObject1 = jsonArrayVideos.getJSONObject(j)
                            homeModel.videoId = jsonObject1.getString("VideoId")
                            homeModel.videoName = jsonObject1.getString("VideoName")
                            homeModel.videoURL = jsonObject1.getString("VideoURL")
                        }
                        var Featured: String = jsonObjectItems.getString("Featured")

//                        slider_modelArrayList =
//                            slider_modelArrayList.distinctBy { it.courseId } as ArrayList<HomeModelWithLogin>
//                        myCustomPagerAdapter =
//                            ViewPagerWithLogin(
//                                this.applicationContext,
//                                slider_modelArrayList
//                            )
//                        viewPager!!.adapter = myCustomPagerAdapter
//                        indicator!!.setViewPager(viewPager)
//                        myCustomPagerAdapter!!.notifyDataSetChanged()
                        tempHeaderToBeSortedBySemesterSequence.add(homeModel)
                    }
                    //Sort by semesterSequence add to header
                    tempHeaderToBeSortedBySemesterSequence.sortBy { it.semesterSequence.toInt() }
                    for (homeModel in tempHeaderToBeSortedBySemesterSequence) {
                        homeModelBothApi.add(homeModel)
                        sortedHeaderListBoth.add(homeModel.semesterName)
                    }
                    sortAndFilterData(sortedHeaderListBoth, homeModelBothApi)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                progresssHome.visibility = View.GONE
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return java.util.HashMap()
            }

            override fun getParams(): Map<String, String> {
                val hashMap: java.util.HashMap<String, String> = java.util.HashMap()
                hashMap["StudentId"] = studentID
                hashMap["CollegeId"] = collegeID
                hashMap["ArchiveSearch"] = "0"
                return hashMap
            }
        }
        stringRequest.setShouldCache(false)
        this.applicationContext.let {
            VolleySingleton.getInstance(it).addToRequestQueue(stringRequest)
        }
    }


    fun sortAndFilterData(
        headerListBothApiNotFilteredButSorted: java.util.ArrayList<String>,
        homeListBothApiNotFilteredAndNotSorted: java.util.ArrayList<HomeModelWithLogin>
    ) {
        val uniqueFilteredSortedHeaderList =
            headerListBothApiNotFilteredButSorted.filter { it.isNotEmpty() }.distinctBy { it }
        for (j in uniqueFilteredSortedHeaderList.indices) {
            val tempHomeListByRibbon = java.util.ArrayList<HomeModelWithLogin>()
            for (k in 0 until homeListBothApiNotFilteredAndNotSorted.size) {
                // If header is as ribbonName in item, then add to main list.
                if (uniqueFilteredSortedHeaderList[j] == homeListBothApiNotFilteredAndNotSorted[k].ribbonName) {
                    val showModel = HomeModelWithLogin()
                    showModel.courseName = homeListBothApiNotFilteredAndNotSorted[k].courseName
                    showModel.courseDescription =
                        homeListBothApiNotFilteredAndNotSorted[k].courseDescription
                    showModel.courseThumbnail =
                        homeListBothApiNotFilteredAndNotSorted[k].courseThumbnail
                    showModel.ageGuidance = homeListBothApiNotFilteredAndNotSorted[k].ageGuidance
                    showModel.videoQuality = homeListBothApiNotFilteredAndNotSorted[k].videoQuality
                    showModel.semesterSequence =
                        homeListBothApiNotFilteredAndNotSorted[k].semesterSequence
                    showModel.courseSequence =
                        homeListBothApiNotFilteredAndNotSorted[k].courseSequence
                    showModel.featured =
                        homeListBothApiNotFilteredAndNotSorted[k].featured
                    showModel.videoURL = homeListBothApiNotFilteredAndNotSorted[k].videoURL
                    showModel.courseHours = homeListBothApiNotFilteredAndNotSorted[k].courseHours
                    showModel.courseId = homeListBothApiNotFilteredAndNotSorted[k].courseId
                    showModel.isPackage = homeListBothApiNotFilteredAndNotSorted[k].isPackage

                    tempHomeListByRibbon.add(showModel)
                }
            }
            finalHashMapToRender[uniqueFilteredSortedHeaderList[j]] = tempHomeListByRibbon
        }
        renderRows(finalHashMapToRender)
    }

    private fun renderRows(finalHashMapToRender: java.util.LinkedHashMap<String, java.util.ArrayList<HomeModelWithLogin>>) {
        for ((key, value) in finalHashMapToRender) {
            //Sorting row contents by course sequence for all
            value.sortBy { it.courseSequence.toInt() }
            val showModel1 = HomeModelWithLogin()
            val contentArrayListInternal: java.util.ArrayList<Data> = java.util.ArrayList<Data>()
            showModel1.header = key
            for (k in 0 until value.size) {
                val showModel = Data()
                showModel.type=key
                showModel.courseName = value[k].courseName
                showModel.courseDescription = value[k].courseDescription
                showModel.courseThumbnail = value[k].courseThumbnail
                showModel.ageGuidance = value[k].ageGuidance
                showModel.videoQuality = value[k].videoQuality
                showModel.videoURL = value[k].videoURL
                showModel.featured = value[k].featured
                showModel.isPackage = value[k].isPackage
                if (value[k].courseHours.equals(""))
                {
                    showModel.courseHours="00:00:00"
                }
                else {
                    showModel.courseHours = value[k].courseHours
                }
                showModel.courseId=value[k].courseId
                showModel.courseSequence = value[k].courseSequence
                contentArrayListInternal.add(showModel)
                dataModelArrayList.add(showModel)

            }
            showModel1.contentArrayList = contentArrayListInternal
            homeModelArrayList.add(showModel1)
//            setUiPageViewController()
        }
        newRecyclerViewAdapter =
            RecyclerViewWithLogin(
                this, homeModelArrayList
            )
        recyclerView.adapter = newRecyclerViewAdapter
        newRecyclerViewAdapter.notifyDataSetChanged()
        progresssHome.visibility = View.GONE

    }

}