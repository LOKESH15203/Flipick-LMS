// ##########################
// ##########################
// PLAYER activity
// and
// SEARCH activity
// is to be implemented.
// ##########################
// ##########################

package com.example.flipicklms

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.example.flipicklms.Adapters.PackageRecyclerAdapter
import com.example.flipicklms.Resources.Api
import com.example.flipicklms.Resources.PackageCourseInformation
import com.example.flipicklms.Resources.SharedPrefManager
import com.example.flipicklms.Resources.VolleySingleton
import org.json.JSONException
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {
    var instanceShared: SharedPrefManager? = null
    var courseID: String = ""
    var studentID: String = ""
    var videoUrl: String = ""
    var adUrl: String = ""

    private var simpleExoPlayer_: ExoPlayer? = null
    private lateinit var playerView_: PlayerView
    lateinit var mainImage:ImageView
    lateinit var profileImage: ImageView
    lateinit var progressBar:ProgressBar
    lateinit var textTitle: TextView
    lateinit var btnPlay: AppCompatButton
    lateinit var buttonResume: AppCompatButton
    lateinit var textEpisode:TextView
    lateinit var textDuration: TextView
    lateinit var imageU: ImageView
    lateinit var imageHD: ImageView
    lateinit var recycler_details: RecyclerView
    lateinit var textOverview: TextView
    lateinit var textCastandCrew: TextView
    lateinit var imgSearch: ImageView

    val packageList: ArrayList<PackageCourseInformation> = ArrayList<PackageCourseInformation>()

    private lateinit var packageRecyclerAdapter: PackageRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        instanceShared =
            SharedPrefManager(applicationContext)


        profileImage = findViewById(R.id.profile)
        playerView_ = findViewById(R.id.player)
        mainImage=findViewById(R.id.mainImage)
        textTitle = findViewById(R.id.textTitle)
        btnPlay = findViewById(R.id.buttonPlay)
        buttonResume=findViewById(R.id.buttonResume)
        textDuration = findViewById(R.id.textDuration)
        imageU = findViewById(R.id.imageU)
        imageHD = findViewById(R.id.imageHD)
        textEpisode=findViewById(R.id.textEpisode)
        recycler_details = findViewById(R.id.recycler_details)
        textOverview = findViewById(R.id.textOverview)
        progressBar=findViewById(R.id.descriptionProgress)
        textCastandCrew = findViewById(R.id.textCastandCrew)
        studentID = instanceShared!!.mobid.toString()
        imgSearch=findViewById(R.id.imgSearch)
        courseID = intent.getStringExtra("course_id").toString()
        btnPlay.setOnClickListener {
//            val intent = Intent(applicationContext, PlayerActivity::class.java)
            intent.putExtra("videoUrl", videoUrl)
            intent.putExtra("title", textTitle.text)
            intent.putExtra("adUrl", adUrl)
            intent.putExtra("time",0)

            Log.d("zzz","videoUrl "+videoUrl)

            startActivity(intent)
        }
        buttonResume.setOnClickListener {
//            val intent = Intent(applicationContext, PlayerActivity::class.java)
            intent.putExtra("videoUrl", videoUrl)
            intent.putExtra("title", textTitle.text)
            intent.putExtra("adUrl", adUrl)
            intent.putExtra("time","")
            Log.d("zzz","videoUrl "+videoUrl)

            startActivity(intent)
        }

        imgSearch.setOnClickListener {
//            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }
        profileImage.setOnClickListener {
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }
        val gridLayoutManager = GridLayoutManager(applicationContext, 1)
        recycler_details.layoutManager = gridLayoutManager
        val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recycler_details.addItemDecoration(decoration)
        packageRecyclerAdapter =
            PackageRecyclerAdapter(
                this, packageList
            )
        recycler_details.setHasFixedSize(true);
        recycler_details.adapter = packageRecyclerAdapter
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    override fun onPause() {
        if (simpleExoPlayer_ != null) {
            simpleExoPlayer_!!.release()
        }
        super.onPause()
    }

    fun loadData() {
        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.POST, Api.baseUrl +
            "StudentCourseHBI/GetInstituteCourseListByStudent2",
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    Log.d("zzz", "Response: fetured  " + response)
                    val jsonArray = jsonObject.getJSONArray("StudentCourseList")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObjectItems = jsonArray.getJSONObject(i)
                        textTitle.setText(jsonObjectItems.getString("CourseName"))
                        Log.d("zzz","CourseHours"+jsonObjectItems.getString("CourseHours"))
                        if (jsonObjectItems.getString("CourseHours").length>3) {
                            val result: String =
                                jsonObjectItems.getString("CourseHours").substring(3)
                            textDuration.setText(result)
                        }
                        else {
                            textDuration.setText("00:00")
                        }
                        videoUrl = jsonObjectItems.getString("BookUrl")
                        adUrl=jsonObjectItems.getString("AdUrl")
                        Log.d("zzz","VideoUrlfromApi  "+jsonObjectItems.getString("BookUrl"))
                        if (jsonObjectItems.getString("AgeGuidance") != "") {
                            imageU.visibility = View.VISIBLE
                        } else {
                            imageU.visibility = View.VISIBLE
                        }
                        Log.d("zzz","IsPackage:   "+ jsonObjectItems.getString("IsPackage"))
                        if (jsonObjectItems.getString("IsPackage").equals("1")) {
                            recycler_details.visibility=View.VISIBLE
                            btnPlay.visibility=View.INVISIBLE
                            textEpisode.visibility=View.VISIBLE
                            buttonResume.visibility=View.INVISIBLE
                            loadPackageData(studentID, jsonObjectItems.getString("CourseId"))
                        }
                        else{
                            loadResume(studentID, jsonObjectItems.getString("CourseId"))
                        }

                        var someValue = jsonObjectItems.getString("CourseDescription")
                        while (someValue.contains("&lt;")) {
                            someValue = someValue.replace("&lt;", "<")
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            textOverview.setText(
                                Html.fromHtml(
                                    someValue,
                                    Html.FROM_HTML_MODE_COMPACT
                                )
                            );
                        } else {
                            textOverview.setText(Html.fromHtml("<b>Overview: \n </b>"+someValue));
                        }
                        if (jsonObjectItems.getString("VideoQuality").equals("hd", true)) {
                            imageHD.visibility = View.VISIBLE
                        } else {
                            imageHD.visibility = View.GONE
                        }
                        Glide.with(applicationContext)
                            .load(jsonObjectItems.getString("CourseThumbnail"))
                            .into(mainImage)
                        val jsonObjectContent = jsonObjectItems.getJSONObject("ContentTypes")
                        val jsonArrayVideos = jsonObjectContent.getJSONArray("Videos")
                        for (j in 0 until jsonArrayVideos.length()) {
                            val jsonObject1 = jsonArrayVideos.getJSONObject(j)
//                            homeModel.videoId = jsonObject1.getString("VideoId")
//                            homeModel.videoName = jsonObject1.getString("VideoName")
//                            homeModel.videoURL = jsonObject1.getString("VideoURL")
                            play_Video(jsonObject1.getString("VideoURL"))
                        }
                    }
                    progressBar.visibility = View.GONE
                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressBar.visibility = View.GONE

                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
                progressBar.visibility = View.GONE
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                return headers
            }

            override fun getParams(): Map<String, String> {
                val hashMap: HashMap<String, String> = HashMap()
                hashMap["StudentId"] = studentID
                hashMap["CourseId"] = courseID
                hashMap["CollegeId"] = "5430"
                hashMap["ArchiveSearch"]="0"
                return hashMap
            }
        }
        stringRequest.setShouldCache(false)
        applicationContext.let {
            VolleySingleton.getInstance(it).addToRequestQueue(stringRequest)
        }
    }

    fun loadPackageData(studentID: String, courseId: String) {
        packageList.clear()
        Log.d("zzz","Student id"+studentID+"courseId"+ courseId)
        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.GET, Api.baseUrl +
            "StudentCourse/GetPackageCourseListByStudent?id="+studentID+"&CourseId="+courseId,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    Log.d("zzz", "DetailsFragmentResponse:   " + response)
                    val jsonArray = jsonObject.getJSONArray("StudentCourseList")
                    Log.d("zzz", "ArrayLength:   " + jsonArray.length())
                    for (i in 0 until jsonArray.length()) {
                        val packageModel =
                            PackageCourseInformation()
                        val jsonObjectItems = jsonArray.getJSONObject(i)
                        packageModel.courseId=jsonObjectItems.getString("CourseId")
//                        homeModel.academies = jsonObjectItems.getString("Academies")
//                        packageModel.adUrl = jsonObjectItems.getString("AdUrl")
                        packageModel.ageGuidance = ""
//                        homeModel.audience = jsonObjectItems.getString("Audience")
                        packageModel.bookUrl = jsonObjectItems.getString("BookUrl")
//                        homeModel.contentTypes = jsonObjectItems.getString("ContentTypes")
                        packageModel.corusePackageAlias =
                            jsonObjectItems.getString("CorusePackageAlias")
                        packageModel.semesterId = jsonObjectItems.getString("SemesterId")
                        packageModel.masterCourseId =
                            jsonObjectItems.getString("MasterCourseId")
                        packageModel.semesterName =
                            jsonObjectItems.getString("SemesterName")
//                        homeModel.videoQuality =
//                            jsonObjectItems.getString("VideoQuality")
                        packageModel.courseDescription =
                            jsonObjectItems.getString("CourseDescription")
                        packageModel.courseHours = jsonObjectItems.getString("CourseHours")
                        packageModel.courseName = jsonObjectItems.getString("CourseName")
                        packageModel.courseThumbnail =
                            jsonObjectItems.getString("CourseThumbnail")
                        val jsonObjectContent = jsonObjectItems.getJSONObject("ContentTypes")
                        val jsonArrayVideos = jsonObjectContent.getJSONArray("Videos")
                        for (j in 0 until jsonArrayVideos.length()) {
                            val jsonObject1 = jsonArrayVideos.getJSONObject(j)
                            packageModel.videoId = jsonObject1.getString("VideoId")
                            packageModel.videoName = jsonObject1.getString("VideoName")
                            packageModel.videoURL = jsonObject1.getString("VideoURL")
                        }
                        packageList.add(packageModel)


                    }
                    progressBar.visibility = View.GONE
                    packageRecyclerAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressBar.visibility = View.GONE
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }) {
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

    fun loadResume(studentID: String, courseId: String) {
        Log.d("zzz","Student id"+studentID+"courseId"+ courseId)
        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.GET,Api.baseUrl +
            "LRS/getDetails18?mbox="+instanceShared!!.phone+"&verb=attempted&type=course&isShort=1&lrs=5a39a5a8580a00ad7f222356&CourseId="+courseId,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    Log.d("zzz", "DetailsFragmentResponse:   " + response)
                    val jsonArray = jsonObject.getJSONArray("StudentCourseList")
                    Log.d("zzz", "ArrayLength:   " + jsonArray.length())
                    for (i in 0 until jsonArray.length()) {
                        val packageModel =
                            PackageCourseInformation()
                        val jsonObjectItems = jsonArray.getJSONObject(i)
                        packageModel.courseId=jsonObjectItems.getString("CourseId")
//                        homeModel.academies = jsonObjectItems.getString("Academies")
//                        packageModel.adUrl = jsonObjectItems.getString("AdUrl")
                        packageModel.ageGuidance = ""
//                        homeModel.audience = jsonObjectItems.getString("Audience")
                        packageModel.bookUrl = jsonObjectItems.getString("BookUrl")
//                        homeModel.contentTypes = jsonObjectItems.getString("ContentTypes")
                        packageModel.corusePackageAlias =
                            jsonObjectItems.getString("CorusePackageAlias")
                        packageModel.semesterId = jsonObjectItems.getString("SemesterId")
                        packageModel.masterCourseId =
                            jsonObjectItems.getString("MasterCourseId")
                        packageModel.semesterName =
                            jsonObjectItems.getString("SemesterName")
//                        homeModel.videoQuality =
//                            jsonObjectItems.getString("VideoQuality")
                        packageModel.courseDescription =
                            jsonObjectItems.getString("CourseDescription")
                        packageModel.courseHours = jsonObjectItems.getString("CourseHours")
                        packageModel.courseName = jsonObjectItems.getString("CourseName")
                        packageModel.courseThumbnail =
                            jsonObjectItems.getString("CourseThumbnail")
                        val jsonObjectContent = jsonObjectItems.getJSONObject("ContentTypes")
                        val jsonArrayVideos = jsonObjectContent.getJSONArray("Videos")
                        for (j in 0 until jsonArrayVideos.length()) {
                            val jsonObject1 = jsonArrayVideos.getJSONObject(j)
                            packageModel.videoId = jsonObject1.getString("VideoId")
                            packageModel.videoName = jsonObject1.getString("VideoName")
                            packageModel.videoURL = jsonObject1.getString("VideoURL")
                        }
                        packageList.add(packageModel)
                    }
                    progressBar.visibility = View.GONE
                    packageRecyclerAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressBar.visibility = View.GONE
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }) {
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


    fun play_Video(url: String) {

        Log.d("zzz", "VideoUrl :   " + url)
        if (simpleExoPlayer_ != null) {
            simpleExoPlayer_!!.release()
        }
        simpleExoPlayer_ = ExoPlayer.Builder(this).build()
        playerView_.player = simpleExoPlayer_
        playerView_.useController = false
//        playerView_.subtitleView!!.setBottomPaddingFraction(0.17f)// #########################################################

        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        if (url.contains(".m3u8")) {
            Log.d("zzz", "VideoUrl" + url)
            val mediaItem = MediaItem.fromUri(url)
//            val mediaSource =                                    // #########################################################
//                HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
//            simpleExoPlayer_?.setMediaSource(mediaSource)
        } else {
            Log.d("zzz", "VideoUrl++++ mp4" + url)
            val mediaItem = MediaItem.fromUri(url)
            playerView_.player = simpleExoPlayer_
            simpleExoPlayer_!!.setMediaItem(mediaItem)
        }
        simpleExoPlayer_?.seekTo(0)
        simpleExoPlayer_?.prepare()
        simpleExoPlayer_?.playWhenReady = true
        simpleExoPlayer_!!.addListener(object : Player.Listener {
            fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {
            }

//            override fun onLoadingChanged(isLoading: Boolean) {
//            }
//
//            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                when (playbackState) {
//                    Player.STATE_READY -> {
//                    mainImage.visibility=View.INVISIBLE
//                    }
//                    Player.STATE_BUFFERING -> {
//                        progressBar.visibility = View.VISIBLE
//
//                    }
//                    Player.STATE_ENDED -> {
////                        if (fullScreen)
////                        {
////                        } else {
//                        mainImage.visibility=View.VISIBLE
//                        playerView_.visibility=View.INVISIBLE
//
////                        }
//                    }
//                }
//            }

            override fun onRepeatModeChanged(repeatMode: Int) {
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            }

            override fun onPlayerError(error: PlaybackException) {
                if (simpleExoPlayer_ != null) {
                    simpleExoPlayer_!!.release()
                }
            }

//            override fun onPositionDiscontinuity(reason: Int) {
//            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
            }

            fun onSeekProcessed() {
            }
        })
    }



}