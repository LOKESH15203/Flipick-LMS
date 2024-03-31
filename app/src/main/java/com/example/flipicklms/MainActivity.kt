//





package com.example.flipicklms

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.example.flipicklms.Adapters.RecyclerViewWithLogin
import com.example.flipicklms.Adapters.ViewPagerWithLogin
import com.example.flipicklms.Resources.Api
import com.example.flipicklms.Resources.Data
import com.example.flipicklms.Resources.HomeClass
import com.example.flipicklms.Resources.HomeModelWithLogin
import com.example.flipicklms.Resources.SharedPrefManager
import com.example.flipicklms.Resources.VolleySingleton
import me.relex.circleindicator.CircleIndicator
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    private var instanceShared: SharedPrefManager? = null
    lateinit var profileImage: ImageView
//    lateinit var imageSearch:ImageView
    lateinit var search:AppCompatButton
    private var simpleExoPlayer_: ExoPlayer? = null
    private lateinit var playerView_: PlayerView
    var viewPager: ViewPager? = null
    var indicator: CircleIndicator? = null
    var myCustomPagerAdapter: ViewPagerWithLogin? = null
    var slider_modelArrayList: ArrayList<HomeModelWithLogin> = ArrayList<HomeModelWithLogin>()
    lateinit var recyclerView: RecyclerView
    private lateinit var newRecyclerViewAdapter: RecyclerViewWithLogin
    var homeModelArrayList = arrayListOf<HomeModelWithLogin>()
    lateinit var progresssHome: ProgressBar
    private var currentPage = 0
    var collegeID: String = ""
    var studentID: String = ""
    var courseId: String = ""
    var trailerUrl:String=""



    lateinit var tvInfo:TextView
    lateinit var layoutInfo:FrameLayout
//    lateinit var mainImage:ImageView
//    lateinit var pager_title:TextView
//    lateinit var img_play:ImageView
//    lateinit var imgInfo:ImageView



    private var finalHashMapToRender = LinkedHashMap<String, ArrayList<HomeModelWithLogin>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        instanceShared = SharedPrefManager(applicationContext)
        playerView_=findViewById(R.id.playerView)
        studentID = instanceShared?.studentID.toString()
        collegeID = instanceShared?.collegeId.toString()
//        collegeID = SharedPrefManager.getInstance(applicationContext).collegeId
//        collegeID = "5430"
//        studentID = "200"
        profileImage = findViewById(R.id.profile)
        recyclerView = findViewById(R.id.recycler_login)
        progresssHome = findViewById(R.id.progresssHome)
        viewPager = findViewById(R.id.viewPager)
        indicator = findViewById(R.id.indicator_youth)
        viewPager!!.adapter = myCustomPagerAdapter
        tvInfo=findViewById(R.id.textInfo)
        layoutInfo=findViewById(R.id.layoutInfo)
//        imageSearch=findViewById(R.id.imgSearch)
        search = findViewById(R.id.editText)

//        imgInfo.setOnClickListener {
//            val intent = Intent(applicationContext, DescriptionActivity::class.java)
//
////                    intent.putExtra("hd", content.ageGuidance)
////                    intent.putExtra("age", content.ageGuidance)
//            //                    intent.putExtra("hd", content.ageGuidance)
////                    intent.putExtra("age", content.ageGuidance)
//            intent.putExtra("course_id", courseId)
////                intent.putExtra("student_id", sharedPrefManager.getMobid());
//            //                intent.putExtra("student_id", sharedPrefManager.getMobid());
//            startActivity(intent)
//        }

        profileImage.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        search.setOnClickListener {
            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }

//        img_play.setOnClickListener {
////            callApi(courseId)
//        }

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
        recyclerView.setHasFixedSize(true)


        loadDataFromFirstApi()
    }













    companion object{
        var dataModelArrayList = arrayListOf<Data>()
        var studentID: String = ""
    }

    override fun onResume() {
        super.onResume()
        if (trailerUrl!="")
        {
//            play_FeaturedVideo(trailerUrl)
        }
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
                // {"Error":{"ModelErrors":{},"Status":0,"Message":null,"ErrorCode":null},"LRSInfo":{"URL":"https://lms1.flipick.com//cmwebapi/api/LRS/","Id":"5a39a5a8580a00ad7f222356","UserName":"689e1f85f35e2e4fb400f6f4e0ba816352582127","Password":"9bf1e03a79b93619c053ac63da93a6425a879640","LRSURL":"https://lms1.flipick.com/"},"PopularCourseList":[],"TrendingCourseList":[],"RecentlyCourseList":[],"CourseList":[{"CourseId":1157,"CourseName":"Customer Service","MasterCourseId":1157,"BookUrl":"https://cdn3.wowza.com/2/OUxEK0xyVWRyR0dy/NGROWkpO/hls/l96rbpgv/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1157_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"In today's competitive business landscape, exceptional customer service is a cornerstone of success. This comprehensive e-learning course, \"Mastering Customer Service Excellence,\" is designed to equip individuals across various industries with the skills, knowledge, and mindset required to deliver unparalleled customer experiences. Participants will explore the fundamentals of customer service, effective communication techniques, conflict resolution strategies, and the art of building lasting customer relationships.","CourseHours":"00:50:58","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1157.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Customer Service","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5759","SemesterName":"Customer Service and Communication","TotalBookMarksCount":1,"TotalFavoritesCount":0,"TotalLikesCount":1,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/16/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37353","BookName":"Customer Service","eBookURL":"https://cdn3.wowza.com/2/OUxEK0xyVWRyR0dy/NGROWkpO/hls/l96rbpgv/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/16/2023 9:56:33 AM","ModifiedDate":"5/16/2023 9:56:33 AM"}],"Videos":[{"VideoId":"103","VideoName":"Customer service","VideoURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1157/Video/103/103.mp4","PublisherId":"1","PublisherName":"","CreatedDate":"5/16/2023 11:53:46 AM","ModifiedDate":"5/16/2023 11:53:46 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5759","CourseSequence":"1157","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/16/2023 9:56:33 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1158,"CourseName":"Revolutionizing Education with AI","MasterCourseId":1158,"BookUrl":"https://cdn3.wowza.com/2/RVgxc3d6V0FFTHdQ/YjhMT0Vj/hls/wmwbcy7p/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1158_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"<div>In a rapidly evolving world driven by technological advancements, the intersection of education and artificial intelligence (AI) has the potential to reshape traditional learning paradigms and unlock unprecedented opportunities for both educators and learners. This comprehensive e-learning course, Revolutionizing Education with AI, delves into the transformative impact of AI on the field of education.</div><div><br></div><div>This course is designed to equip educators, administrators, policymakers, and anyone interested in the future of education with a deep understanding of how AI technologies are being integrated into various aspects of learning, from classroom instruction to personalized learning experiences.</div>","CourseHours":"00:00:27","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1158.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Revolutionizing Education with AI","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":2,"TotalFavoritesCount":0,"TotalLikesCount":4,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/19/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37354","BookName":"Revolutionizing Education with AI","eBookURL":"https://cdn3.wowza.com/2/RVgxc3d6V0FFTHdQ/YjhMT0Vj/hls/wmwbcy7p/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/19/2023 10:26:11 AM","ModifiedDate":"5/19/2023 10:26:11 AM"}],"Videos":[{"VideoId":"104","VideoName":"Revolutionizing Education with AI","VideoURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1158/Video/104/104.mp4","PublisherId":"1","PublisherName":"","CreatedDate":"5/19/2023 10:27:07 AM","ModifiedDate":"5/19/2023 10:27:07 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5765","CourseSequence":"1158","AdUrl":null,"RibbonName":"","Featured":"1","VideoType":"Horizontal","CreatedDate":"5/19/2023 10:26:11 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1160,"CourseName":"Equity and Venture Capital Option 1","MasterCourseId":1160,"BookUrl":"https://cdn3.wowza.com/2/SmR4VG1HSW5mRWFY/cEhLbjNO/hls/byfn1hzp/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1160_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"<div>In today's dynamic business landscape, understanding the intricacies of equity and venture capital is crucial for entrepreneurs, investors, and professionals seeking to make informed financial decisions and drive innovation. This comprehensive e-learning course, Equity and Venture Capital: Navigating Investment Opportunities, offers a deep dive into the world of equity financing, venture capital, and the strategies required to succeed in this complex ecosystem.</div><div><br></div><div>This course is tailored for aspiring entrepreneurs, investors, financial analysts, and individuals interested in gaining insights into the world of high-risk, high-reward investments.</div>","CourseHours":"00:29:38","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1160.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Equity and Venture Capital Option 1","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":2,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/25/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37356","BookName":"Equity and Venture Capital Option 1","eBookURL":"https://cdn3.wowza.com/2/SmR4VG1HSW5mRWFY/cEhLbjNO/hls/byfn1hzp/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/25/2023 12:29:03 PM","ModifiedDate":"5/25/2023 12:29:03 PM"}],"Videos":[{"VideoId":"105","VideoName":"Equity and Venture Capital Option 1","VideoURL":"https://cdn3.wowza.com/2/THg2UDVuRE54ZUhh/QW9NQ254/hls/8pwc3bpg/playlist.m3u8","PublisherId":"1","PublisherName":"","CreatedDate":"5/25/2023 1:06:09 PM","ModifiedDate":"5/25/2023 1:06:09 PM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5765","CourseSequence":"1160","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/25/2023 12:29:03 PM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1161,"CourseName":"Equity and Venture Capital Option 2","MasterCourseId":1161,"BookUrl":"https://videos.sproutvideo.com/embed/7990d1b41e1ae1c7f0/5f65db764bbf0b09?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1161_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"<div>In today's dynamic business landscape, understanding the intricacies of equity and venture capital is crucial for entrepreneurs, investors, and professionals seeking to make informed financial decisions and drive innovation. This comprehensive e-learning course, Equity and Venture Capital: Navigating Investment Opportunities, offers a deep dive into the world of equity financing, venture capital, and the strategies required to succeed in this complex ecosystem.</div><div><br></div><div>This course is tailored for aspiring entrepreneurs, investors, financial analysts, and individuals interested in gaining insights into the world of high-risk, high-reward investments.</div>","CourseHours":"00:29:38","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1161.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Equity and Venture Capital Option 2","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":1,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/25/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37357","BookName":"Equity and Venture Capital Option 2","eBookURL":"https://videos.sproutvideo.com/embed/7990d1b41e1ae1c7f0/5f65db764bbf0b09?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/25/2023 12:32:02 PM","ModifiedDate":"5/25/2023 12:32:02 PM"}],"Videos":[{"VideoId":"107","VideoName":"Equity and Venture Capital Option 2","VideoURL":"https://cdn3.wowza.com/2/dHk5dXNlL0h4TThU/cGdlQ1Nt/hls/gdyxvgb8/playlist.m3u8","PublisherId":"1","PublisherName":"","CreatedDate":"5/25/2023 1:16:26 PM","ModifiedDate":"5/25/2023 1:16:26 PM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5765","CourseSequence":"1161","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/25/2023 12:32:02 PM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1162,"CourseName":"GERD and Infections PCOS","MasterCourseId":1162,"BookUrl":"https://videos.sproutvideo.com/embed/a790d1b41d11e2ca2e/630f38cf35af603f?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1162_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Understand the causes and symptoms of GERD and infections. Learn about the different treatment options available for GERD and infection. Identify the risk factors associated with PCOS.","CourseHours":"00:39:56","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1162.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"GERD and Infections PCOS","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":0,"TotalFavoritesCount":2,"TotalLikesCount":3,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/25/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37358","BookName":"GERD and Infections PCOS","eBookURL":"https://videos.sproutvideo.com/embed/a790d1b41d11e2ca2e/630f38cf35af603f?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/25/2023 12:40:23 PM","ModifiedDate":"5/25/2023 12:40:23 PM"}],"Videos":[{"VideoId":"106","VideoName":"GERD and Infections PCOS","VideoURL":"https://cdn3.wowza.com/2/azVkYys2SFBqblhY/ZW5nd1V0/hls/bnlvxhdv/playlist.m3u8","PublisherId":"1","PublisherName":"","CreatedDate":"5/25/2023 1:10:23 PM","ModifiedDate":"5/25/2023 1:10:23 PM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5763","CourseSequence":"1162","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/25/2023 12:40:23 PM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1163,"CourseName":"A Look at Netflix's Paid Sharing Experiment","MasterCourseId":1163,"BookUrl":"https://cdn3.wowza.com/2/TWlYSEpsSjltK3RW/Z1BybEdI/hls/hvckf9tk/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1163_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Account sharing has been a long-standing issue for Netflix, with over 100 million households estimated to be sharing accounts. Laws have been enacted to address this problem, but enforcement is rare.","CourseHours":"00:02:53","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1163.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"A Look at Netflix's Paid Sharing Experiment","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/26/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37359","BookName":"A Look at Netflix's Paid Sharing Experiment","eBookURL":"https://cdn3.wowza.com/2/TWlYSEpsSjltK3RW/Z1BybEdI/hls/hvckf9tk/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/26/2023 9:27:16 AM","ModifiedDate":"5/26/2023 9:27:16 AM"}],"Videos":[{"VideoId":"108","VideoName":"A Look at Netflix''s Paid Sharing Experiment","VideoURL":"https://cdn3.wowza.com/2/d05YYWswdkdOVk4y/MkEzbjZW/hls/bmnkkb2v/playlist.m3u8","PublisherId":"1","PublisherName":"","CreatedDate":"5/26/2023 9:39:18 AM","ModifiedDate":"5/26/2023 9:39:18 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5765","CourseSequence":"1163","AdUrl":"https://lms1content.flipick.com/lms1content/VASTFiles/1163.xml","RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/26/2023 9:27:16 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1164,"CourseName":"Coaching and Developing Employees","MasterCourseId":1164,"BookUrl":"https://cdn3.wowza.com/2/bUF5S1llV0UrWTRR/N2JqRjNF/hls/khdpr8x3/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1164_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"This course covers Coaching and Developing Employees. Understand the importance of coaching and developing employees in the workplace. Identify the different coaching and development methods and techniques.","CourseHours":"00:41:46","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1164.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Coaching and Developing Employees","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5764","SemesterName":"Customer Support and Relations","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/26/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37360","BookName":"Coaching and Developing Employees","eBookURL":"https://cdn3.wowza.com/2/bUF5S1llV0UrWTRR/N2JqRjNF/hls/khdpr8x3/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/26/2023 11:35:14 AM","ModifiedDate":"5/26/2023 11:35:14 AM"}],"Videos":[{"VideoId":"109","VideoName":"Coaching and Developing Employees","VideoURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1164/Video/109/109.mp4","PublisherId":"1","PublisherName":"","CreatedDate":"5/26/2023 11:36:54 AM","ModifiedDate":"5/26/2023 11:36:54 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5764","CourseSequence":"1164","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/26/2023 11:35:14 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1165,"CourseName":"Customer Communication Essentials","MasterCourseId":1165,"BookUrl":"https://cdn3.wowza.com/2/Y3BLbThRZjFycXZN/SDRqRWs3/hls/v6jdtg2s/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1165_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Welcome to the Customer Communication Essentials Course! In this course, you will gain invaluable skills to enhance your communication with customers and establish long-lasting relationships. Effective communication is key to engaging with customers and meeting their needs.","CourseHours":"00:22:47","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1165.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Customer Communication Essentials","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5764","SemesterName":"Customer Support and Relations","TotalBookMarksCount":1,"TotalFavoritesCount":0,"TotalLikesCount":4,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/30/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37361","BookName":"Customer Communication Essentials","eBookURL":"https://cdn3.wowza.com/2/Y3BLbThRZjFycXZN/SDRqRWs3/hls/v6jdtg2s/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/30/2023 9:52:46 AM","ModifiedDate":"5/30/2023 9:52:46 AM"}],"Videos":[{"VideoId":"110","VideoName":"Customer Communication Essentials","VideoURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1165/Video/110/110.mp4","PublisherId":"1","PublisherName":"","CreatedDate":"5/30/2023 10:23:57 AM","ModifiedDate":"5/30/2023 10:23:57 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5764","CourseSequence":"1165","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/30/2023 9:52:46 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1166,"CourseName":"COVID-19 What You Need to Know","MasterCourseId":1166,"BookUrl":"https://cdn3.wowza.com/2/OWpmTklsRVJFbkRx/M0YxM21U/hls/hhjsmnvp/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1166_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"COVID-19: What You Need to Know Learn essential information about COVID-19, including prevention, symptoms, and treatment options COVID-19, also known as the coronavirus, has affected millions of people worldwide. In this course, you will learn about the essential information you need to know about COVID-19. ","CourseHours":"00:29:57","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1166.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"COVID-19 What You Need to Know","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":0,"TotalFavoritesCount":1,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/30/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37362","BookName":"COVID-19 What You Need to Know","eBookURL":"https://cdn3.wowza.com/2/OWpmTklsRVJFbkRx/M0YxM21U/hls/hhjsmnvp/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/30/2023 10:04:11 AM","ModifiedDate":"5/30/2023 10:04:11 AM"}],"Videos":[{"VideoId":"111","VideoName":"COVID-19: What You Need to Know","VideoURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1166/Video/111/111.mp4","PublisherId":"1","PublisherName":"","CreatedDate":"5/30/2023 10:29:52 AM","ModifiedDate":"5/30/2023 10:29:52 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5763","CourseSequence":"1166","AdUrl":"https://lms1content.flipick.com/lms1content/VASTFiles/1166.xml","RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/30/2023 10:04:11 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1167,"CourseName":"Driving Your Career","MasterCourseId":1167,"BookUrl":"https://cdn3.wowza.com/2/V1JIalpmbGtid0RW/TDFaMFlm/hls/fp8npg8l/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1167_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Welcome to the \"Driving Your Career\" course. In this course, you will learn essential skills for career success. You will learn how to set goals, manage your time effectively, communicate with others, and develop leadership skills.","CourseHours":"00:24:31","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1167.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Driving Your Career","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"5/30/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37363","BookName":"Driving Your Career","eBookURL":"https://cdn3.wowza.com/2/V1JIalpmbGtid0RW/TDFaMFlm/hls/fp8npg8l/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"5/30/2023 10:10:24 AM","ModifiedDate":"5/30/2023 10:10:24 AM"}],"Videos":[{"VideoId":"112","VideoName":"Driving Your Career","VideoURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1167/Video/112/112.mp4","PublisherId":"1","PublisherName":"","CreatedDate":"5/30/2023 10:37:56 AM","ModifiedDate":"5/30/2023 10:37:56 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"PG","VideoQuality":"HD","SemesterSequence":"5765","CourseSequence":"1167","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"5/30/2023 10:10:24 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1168,"CourseName":"Physician Patient Communication","MasterCourseId":1168,"BookUrl":"https://cdn3.wowza.com/2/N0g1cklCV3FLcytV/ZzdQNjBi/hls/dfsjy8yc/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1168_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"This course is designed to provide healthcare professionals with the knowledge and skills necessary to improve physician-patient communication. Effective communication between physicians and patients is crucial for building trust, promoting patient-centered care, and achieving positive health outcomes. This course will cover various sub-topics related to physician-patient communication, including the effects of poor communication, the definition and importance of communication, barriers to effective communication, and practical tips for improving communication skills.","CourseHours":"00:09:23","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1168.png","CourseType":"URL","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Physician Patient Communication","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"6/3/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37364","BookName":"Physician Patient Communication","eBookURL":"https://cdn3.wowza.com/2/N0g1cklCV3FLcytV/ZzdQNjBi/hls/dfsjy8yc/playlist.m3u8?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"6/3/2023 11:38:45 AM","ModifiedDate":"6/3/2023 11:38:45 AM"}],"Videos":[{"VideoId":"113","VideoName":"Physician Patient Communication","VideoURL":"https://cdn3.wowza.com/2/N0g1cklCV3FLcytV/ZzdQNjBi/hls/dfsjy8yc/playlist.m3u8","PublisherId":"1","PublisherName":"","CreatedDate":"6/3/2023 11:42:08 AM","ModifiedDate":"6/3/2023 11:42:08 AM"}]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"HD","SemesterSequence":"5763","CourseSequence":"1168","AdUrl":"https://lms1content.flipick.com/lms1content/VASTFiles/1168.xml","RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"6/3/2023 11:38:45 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1171,"CourseName":"Medical Terminology Skeletal System (Medicaid)","MasterCourseId":1171,"BookUrl":"https://lms1content.flipick.com/lms1content/Instructors/6682/Assests/Video/88419/index_lms.html?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1171_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Medical professionals and students entering the healthcare field must possess a solid grasp of medical terminology to effectively communicate, diagnose, and treat patients. This specialized e-learning course, Medical Terminology for the Skeletal System (Medicaid), focuses on building a comprehensive understanding of the skeletal system's anatomical structure, function, and associated medical terms. Designed to cater specifically to Medicaid professionals, this course equips participants with the terminology necessary for accurate documentation, communication, and collaboration within the healthcare ecosystem.<br>","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1171.png","CourseType":"AScorm","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Medical Terminology Skeletal System (Medicaid)","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"7/5/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37367","BookName":"Medical Terminology Skeletal System (Medicaid)","eBookURL":"https://lms1content.flipick.com/lms1content/Instructors/6682/Assests/Video/88419/index_lms.html?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"https://lms1content.flipick.com/lms1content//Instructors/6682/Assests/Video/88419.zip?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"7/5/2023 12:57:18 PM","ModifiedDate":"7/5/2023 12:57:18 PM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"HD","SemesterSequence":"5763","CourseSequence":"1171","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"7/5/2023 12:57:18 PM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1182,"CourseName":"Management of Diabetes","MasterCourseId":1182,"BookUrl":"https://lms1content.flipick.com/lms1content//CourseAsset/1182/Book/37378/37378.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1182_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Management of diabetes involves a combination of lifestyle changes, medication, and regular monitoring. It includes maintaining a balanced diet, exercising regularly, monitoring blood glucose levels, taking prescribed medications (such as insulin or oral hypoglycemic agents), and attending regular check-ups with healthcare professionals. It's essential to manage blood sugar levels to prevent complications and improve overall quality of life for individuals with diabetes.<br>","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1182.png","CourseType":"Flipick","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Management of Diabetes","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":1,"TotalFavoritesCount":0,"TotalLikesCount":1,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"8/2/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37378","BookName":"Management of Diabetes","eBookURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1182/Book/37378/37378.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"8/2/2023 6:32:38 AM","ModifiedDate":"8/2/2023 6:32:38 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5763","CourseSequence":"1182","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"8/2/2023 6:32:38 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1196,"CourseName":"Guidelines for Type 2 Diabetes","MasterCourseId":1196,"BookUrl":"https://lms1content.flipick.com/lms1content/Instructors/6682/Assests/pdf/88441.pdf?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1196_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Management of diabetes involves a combination of lifestyle changes, medication, and regular monitoring. It includes maintaining a balanced diet, exercising regularly, monitoring blood glucose levels, taking prescribed medications (such as insulin or oral hypoglycemic agents), and attending regular check-ups with healthcare professionals.","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1196.png","CourseType":"PDF","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Guidelines for Type 2 Diabetes","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"8/4/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"0","NoOfAssessments":"0","NoOfQuestionsPerAssessments":"0","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37392","BookName":"Guidelines for Type 2 Diabetes","eBookURL":"https://lms1content.flipick.com/lms1content/Instructors/6682/Assests/pdf/88441.pdf?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"8/4/2023 6:04:49 AM","ModifiedDate":"8/4/2023 6:04:49 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5763","CourseSequence":"1196","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"8/4/2023 6:04:49 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1227,"CourseName":"Art of War","MasterCourseId":1227,"BookUrl":"https://lms1content.flipick.com/lms1content//CourseAsset/1227/Book/37423/37423.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1227_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Art of War","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1227.png","CourseType":"Flipick","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Art of War","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5764","SemesterName":"Customer Support and Relations","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"9/4/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37423","BookName":"Art of War","eBookURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1227/Book/37423/37423.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"9/4/2023 7:06:51 AM","ModifiedDate":"9/4/2023 7:06:51 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5764","CourseSequence":"1227","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"9/4/2023 7:06:51 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1244,"CourseName":"Aster Test Course 1","MasterCourseId":1244,"BookUrl":"https://lms1content.flipick.com/lms1content//CourseAsset/1244/Book/37440/37440.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1244_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Aster Test Course 1","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1244.png","CourseType":"Flipick","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Aster Test Course 1","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"1/24/2024 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37440","BookName":"Aster Test Course 1","eBookURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1244/Book/37440/37440.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"1/24/2024 7:11:47 AM","ModifiedDate":"1/24/2024 7:11:47 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5765","CourseSequence":"1244","AdUrl":null,"RibbonName":"RecentlyAdded","Featured":"0","VideoType":"Horizontal","CreatedDate":"1/24/2024 7:11:47 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1244,"CourseName":"Aster Test Course 1","MasterCourseId":1244,"BookUrl":"https://lms1content.flipick.com/lms1content//CourseAsset/1244/Book/37440/37440.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1244_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Aster Test Course 1","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1244.png","CourseType":"Flipick","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Aster Test Course 1","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"1/24/2024 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37440","BookName":"Aster Test Course 1","eBookURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1244/Book/37440/37440.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"1/24/2024 7:11:47 AM","ModifiedDate":"1/24/2024 7:11:47 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5765","CourseSequence":"1244","AdUrl":null,"RibbonName":"Trending","Featured":"0","VideoType":"Horizontal","CreatedDate":"1/24/2024 7:11:47 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1245,"CourseName":"Aster Test Course 2","MasterCourseId":1245,"BookUrl":"","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1245_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Aster Test Course 2","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1245.png","CourseType":"Temp","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Aster Test Course 2","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"1/24/2024 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37441","BookName":"Aster Test Course 2","eBookURL":"","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"1/24/2024 7:12:32 AM","ModifiedDate":"1/24/2024 7:12:32 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5765","CourseSequence":"1245","AdUrl":null,"RibbonName":"RecentlyAdded","Featured":"0","VideoType":"Horizontal","CreatedDate":"1/24/2024 7:12:32 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1245,"CourseName":"Aster Test Course 2","MasterCourseId":1245,"BookUrl":"","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1245_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Aster Test Course 2","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1245.png","CourseType":"Temp","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Aster Test Course 2","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5765","SemesterName":"Learning","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"1/24/2024 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37441","BookName":"Aster Test Course 2","eBookURL":"","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"1/24/2024 7:12:32 AM","ModifiedDate":"1/24/2024 7:12:32 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5765","CourseSequence":"1245","AdUrl":null,"RibbonName":"Trending","Featured":"0","VideoType":"Horizontal","CreatedDate":"1/24/2024 7:12:32 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1246,"CourseName":"Entrance Exam","MasterCourseId":1246,"BookUrl":"https://lms1content.flipick.com/lms1content//CourseAsset/1246/Book/37442/37442.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1246_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Entrace Exam Details","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1246.png","CourseType":"Flipick","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Entrance Exam","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"1/24/2024 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37442","BookName":"Entrance Exam","eBookURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1246/Book/37442/37442.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"1/24/2024 9:14:14 AM","ModifiedDate":"1/24/2024 9:14:14 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5763","CourseSequence":"1246","AdUrl":null,"RibbonName":"RecentlyAdded","Featured":"0","VideoType":"Horizontal","CreatedDate":"1/24/2024 9:14:14 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1246,"CourseName":"Entrance Exam","MasterCourseId":1246,"BookUrl":"https://lms1content.flipick.com/lms1content//CourseAsset/1246/Book/37442/37442.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1246_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"Entrace Exam Details","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1246.png","CourseType":"Flipick","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Entrance Exam","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"0","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":0,"TotalFavoritesCount":0,"TotalLikesCount":0,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"1/24/2024 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37442","BookName":"Entrance Exam","eBookURL":"https://lms1content.flipick.com/lms1content//CourseAsset/1246/Book/37442/37442.epub?IsFlipick=w/Y9SfLF5CPdoJw6a0UEbA==","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"1/24/2024 9:14:14 AM","ModifiedDate":"1/24/2024 9:14:14 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5763","CourseSequence":"1246","AdUrl":null,"RibbonName":"Trending","Featured":"0","VideoType":"Horizontal","CreatedDate":"1/24/2024 9:14:14 AM","ExpirationDate":"8/17/2024 5:09:15 AM"},{"CourseId":1183,"CourseName":"Learning Path - Diabetes Control","MasterCourseId":1183,"BookUrl":"","CorusePackageAlias":"Course","CourseAssignedBy":"0","CourseBatchId":"1183_0","CourseCollegeId":null,"CourseCompletedPercentage":null,"CourseDescription":"<div>Management of diabetes involves a combination of lifestyle changes, medication, and regular monitoring. It includes maintaining a balanced diet, exercising regularly, monitoring blood glucose levels, taking prescribed medications (such as insulin or oral hypoglycemic agents), and attending regular check-ups with healthcare professionals. It's essential to manage blood sugar levels to prevent complications and improve overall quality of life for individuals with diabetes.<br></div>","CourseHours":"","CourseThumbnail":"https://lms1content.flipick.com/lms1content/CourseThumbnail/1183.jpg","CourseType":"Temp","CourseTypeAlias":"Continuing Education","Dashboard":"N","DueDateToShow":"08/17/2024","InAppPurchaseName":"Learning Path - Diabetes Control","IsBookMarked":false,"IsChaperWiseTestExists":"0","IsCourseActive":"1","IsCourseEnabled":"Y","IsCoursePurchased":true,"IsFavourite":false,"IsIQCourse":null,"IsLiked":false,"IsPackage":"1","IsPackageCourse":null,"IsPartialPurchase":"N","IsPracticeTestExists":"0","LRSCompletion":"Auto","LTIConsumnerKey":null,"LTISecret":null,"LTIURL":null,"Language":"English","MockTestFormat":"","PreviousTopicCompletion":"N","ProductUrl":null,"SemesterId":"5763","SemesterName":"Health Education","TotalBookMarksCount":2,"TotalFavoritesCount":2,"TotalLikesCount":1,"TotalSlides":0,"Audience":"","CourseRole":"","Theme":"","Topics":"","ResourceType":"","OrganizationType":"","EHR":"","StaffedBeds":"0","Academies":"","ProductTypeId":"","ProductTypeName":null,"CourseReleaseDate":"8/2/2023 12:00:00 AM","CourseMeTaData":{"NoOfModules":"","NoOfAssessments":"","NoOfQuestionsPerAssessments":"","TincanId":""},"ContentTypes":{"eBooks":[{"BookId":"37379","BookName":"Learning Path - Diabetes Control","eBookURL":"","ZipURL":"","PublisherId":"22","PublisherName":"Flipick","CreatedDate":"8/2/2023 6:48:29 AM","ModifiedDate":"8/2/2023 6:48:29 AM"}],"Videos":[]},"LevelId":null,"DisciplineId":null,"UniversityId":null,"ExamId":null,"PreRequisite":"","ShowImage":true,"ShowVideo":false,"Keywords":"","AgeGuidance":"U","VideoQuality":"SD","SemesterSequence":"5763","CourseSequence":"1183","AdUrl":null,"RibbonName":"","Featured":"0","VideoType":"Horizontal","CreatedDate":"8/2/2023 6:48:29 AM","ExpirationDate":"8/17/2024 5:09:15 AM"}],"GridSettingsList":[{"ThumbnailWidth":"800","ThumbnailHeight":"450","ImagePlacement":"Bottom","ShortDescription":"N","Period":"Y","SeekTimer":"N","DropCourse":"N","Likes":"Y","Share":"N","Bookmark":"Y","Favorite":"Y","LikeBookReader":"N","ShareBookReader":"N","BookmarkBookReader":"N","FavoriteBookReader":"N","ShowFilter":"N"}]}
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
//                                    Glide.with(applicationContext).load(jsonObjectItems.getString("CourseThumbnail"))
//                                        .into(mainImage)
//                                    play_FeaturedVideo(jsonObjectItems.getString("BookUrl"))
                                for (j in 0 until jsonArrayVideos.length()) {
                                    val jsonObject1 = jsonArrayVideos.getJSONObject(j)
                                    homeModel.videoId = jsonObject1.getString("VideoId")
                                    homeModel.videoName = jsonObject1.getString("VideoName")
                                    homeModel.videoURL = jsonObject1.getString("VideoURL")

                                    trailerUrl=jsonObject1.getString("VideoURL")
//                                    play_FeaturedVideo(jsonObject1.getString("VideoURL"))

                                }
//                                pager_title.setText(jsonObjectItems.getString("CourseName"))
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
//                hashMap["ArchiveSearch"] = "0"
                return hashMap
            }
        }
        stringRequest.setShouldCache(false)
        this.applicationContext.let {
            VolleySingleton.getInstance(it).addToRequestQueue(stringRequest)
        }
    }

    fun loadDataFromSecondApi(
        sortedHeaderListBoth: ArrayList<String>,
        homeModelBothApi: ArrayList<HomeModelWithLogin>
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
                    val tempHeaderToBeSortedBySemesterSequence = ArrayList<HomeModelWithLogin>()
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

                        val requiredItemCount = 3 - slider_modelArrayList.size
                        for (i in 0 until requiredItemCount) {
                            // Assuming slider_modelArrayList is already populated, repeat the data
                            slider_modelArrayList.add(slider_modelArrayList[i % slider_modelArrayList.size])
                        }

                        slider_modelArrayList =
                            slider_modelArrayList.distinctBy { it.courseId } as ArrayList<HomeModelWithLogin>
                        myCustomPagerAdapter =
                            ViewPagerWithLogin(
                                this.applicationContext,
                                slider_modelArrayList
                            )
                        viewPager!!.adapter = myCustomPagerAdapter
                        indicator!!.setViewPager(viewPager)
                        myCustomPagerAdapter!!.notifyDataSetChanged()
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

    fun sortAndFilterData(
        headerListBothApiNotFilteredButSorted: ArrayList<String>,
        homeListBothApiNotFilteredAndNotSorted: ArrayList<HomeModelWithLogin>
    ) {
        val uniqueFilteredSortedHeaderList =
            headerListBothApiNotFilteredButSorted.filter { it.isNotEmpty() }.distinctBy { it }
        for (j in uniqueFilteredSortedHeaderList.indices) {
            val tempHomeListByRibbon = ArrayList<HomeModelWithLogin>()
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
            val contentArrayListInternal: ArrayList<Data> = ArrayList<Data>()
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


    override fun onBackPressed() {
        if (layoutInfo.visibility==View.VISIBLE)
        {
            layoutInfo.visibility=View.GONE
            tvInfo.text=""
//            tvInfo.requestFocus()

        }
        else {
            super.onBackPressed()
            dataModelArrayList.clear()
            finish()
        }
    }


//    fun play_FeaturedVideo(url: String) {
//
//        Log.d("zzz", "VideoUrl BookUrl:   " + url)
//        if (simpleExoPlayer_ != null) {
//            simpleExoPlayer_!!.release()
//        }
//        simpleExoPlayer_ = ExoPlayer.Builder(this).build()
//        playerView_.player = simpleExoPlayer_
//        playerView_.useController = false
//        playerView_.subtitleView!!.setBottomPaddingFraction(0.17f)
//
//        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
//        if (url.contains(".m3u8")) {
//            Log.d("zzz", "VideoUrl" + url)
//            val mediaItem = MediaItem.fromUri(url)
//            val mediaSource =
//                HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
//            simpleExoPlayer_?.setMediaSource(mediaSource)
//        } else {
//            Log.d("zzz", "VideoUrl++++ mp4" + url)
//            val mediaItem = MediaItem.fromUri(url)
//            playerView_.player = simpleExoPlayer_
//            simpleExoPlayer_!!.setMediaItem(mediaItem)
//        }
//        simpleExoPlayer_?.seekTo(0)
//        simpleExoPlayer_?.prepare()
//        simpleExoPlayer_?.playWhenReady = true
//        simpleExoPlayer_!!.addListener(object : Player.Listener {
//            fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {
//            }
//
//            override fun onLoadingChanged(isLoading: Boolean) {
//            }
//
//            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                when (playbackState) {
//                    Player.STATE_READY -> {
//                        mainImage.visibility=View.INVISIBLE
//                        playerView_.visibility=View.VISIBLE
//                    }
//                    Player.STATE_BUFFERING -> {
//                        progresssHome.visibility = View.VISIBLE
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
//
//            override fun onRepeatModeChanged(repeatMode: Int) {
//            }
//
//            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
//            }
//
//            override fun onPlayerError(error: PlaybackException) {
//                if (simpleExoPlayer_ != null) {
//                    simpleExoPlayer_!!.release()
//                }
//            }
//
//            override fun onPositionDiscontinuity(reason: Int) {
//            }
//
//            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
//            }
//
//            override fun onSeekProcessed() {
//            }
//        })
//    }


//    fun playVideo(courseID:String) {
//        progresssHome.visibility=View.VISIBLE
//        val stringRequest: StringRequest = @SuppressLint("NotifyDataSetChanged")
//        object : StringRequest(
//            Method.POST, Api.baseUrl +
//            "StudentCourseHBI/GetInstituteCourseListByStudent2",
//            Response.Listener { response ->
//                try {
//                    val jsonObject = JSONObject(response)
//                    Log.d("zzz", "Response:Details   " + response)
//                    val jsonArray = jsonObject.getJSONArray("StudentCourseList")
//                    for (i in 0 until jsonArray.length()) {
//                        val jsonObjectItems = jsonArray.getJSONObject(i)
//                        val intent = Intent(applicationContext, PlayerActivity::class.java)
//                        intent.putExtra("videoUrl",jsonObjectItems.getString("BookUrl") )
//                        intent.putExtra("title", jsonObjectItems.getString("CourseName"))
//                        progresssHome.visibility = View.GONE
//                        startActivity(intent)
//
//
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                    progresssHome.visibility = View.GONE
//
//                }
//            },
//            Response.ErrorListener {
//                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
//                    .show()
//                progresssHome.visibility = View.GONE
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                return headers
//            }
//
//            override fun getParams(): Map<String, String> {
//                val hashMap: HashMap<String, String> = HashMap()
//                hashMap["StudentId"] = studentID
//                hashMap["CourseId"] = courseID
//                return hashMap
//            }
//        }
//        stringRequest.setShouldCache(false)
//        applicationContext.let {
//            VolleySingleton.getInstance(it).addToRequestQueue(stringRequest)
//        }
//    }

    private fun setUiPageViewController() {
        val density = resources.displayMetrics.density
        indicator!!.rotation = 0 * density
//        val handler = Handler()
        val Update = Runnable {
            if (currentPage == slider_modelArrayList.size - 1) {
                currentPage = 0
            }
            viewPager!!.setCurrentItem(
                currentPage++,
                true
            )
        }
        simpleExoPlayer_?.seekTo(0)
        simpleExoPlayer_?.prepare()
        simpleExoPlayer_?.playWhenReady = true
        simpleExoPlayer_!!.addListener(@UnstableApi object : Player.Listener {
            fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {
            }

            override fun onLoadingChanged(isLoading: Boolean) {
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
//                        mainImage.visibility=View.INVISIBLE
                    }
                    Player.STATE_BUFFERING -> {
                        progresssHome.visibility = View.VISIBLE

                    }
                    Player.STATE_ENDED -> {
//                        if (fullScreen)
//                        {
//                        } else {
//                        mainImage.visibility=View.VISIBLE
                        playerView_.visibility=View.INVISIBLE

//                        }
                    }
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            }

            override fun onPlayerError(error: PlaybackException) {
                if (simpleExoPlayer_ != null) {
                    simpleExoPlayer_!!.release()
                }
            }

            override fun onPositionDiscontinuity(reason: Int) {
//                duration=((simpleExoPlayer!!.duration/1000).toDouble())
//                position=((simpleExoPlayer!!.contentPosition/1000).toDouble())
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
            }

            fun onSeekProcessed() {
//                duration=((simpleExoPlayer!!.duration/1000).toDouble())
//                position=((simpleExoPlayer!!.contentPosition/1000).toDouble())
            }
        })
    }

    override fun onPause() {
        if (simpleExoPlayer_ != null) {
            simpleExoPlayer_!!.release()
        }
        super.onPause()
    }



//    private fun callApi(courseID: String) {
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST, Api.baseUrl +
//            "StudentCourseHBI/GetInstituteCourseListByStudent2",
//            Response.Listener { response: String ->
//                try {
//                    Log.d("zzz", "getToken response: $response")
//                    val jsonObject1 = JSONObject(response)
//                    val jsonArray = jsonObject1.getJSONArray("StudentCourseList")
//                    for (i in 0 until jsonArray.length()) {
//                        val jsonObjectItems = jsonArray.getJSONObject(i)
//                        val intent = Intent(applicationContext, PlayerActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        intent.putExtra("videoUrl", jsonObjectItems.getString("BookUrl"))
//                        intent.putExtra("title", jsonObjectItems.getString("CourseName"))
//                        intent.putExtra("adUrl", jsonObjectItems.getString("AdUrl"))
//
//                        startActivity(intent)
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            },
//            Response.ErrorListener { volleyError: VolleyError ->
//                Log.e(
//                    "alert",
//                    "error$volleyError"
//                )
//            }) {
//            override fun getParams(): Map<String, String>? {
//                val params: MutableMap<String, String> = java.util.HashMap()
//                params["StudentId"] = studentID
//                params["CourseId"] = courseID
//                params["CollegeId"] = "5430"
//                params["ArchiveSearch"]="0"
//                return params
//            }
//        }
//        val requestQueue = Volley.newRequestQueue(applicationContext)
//        requestQueue.add(stringRequest)
//    }


}