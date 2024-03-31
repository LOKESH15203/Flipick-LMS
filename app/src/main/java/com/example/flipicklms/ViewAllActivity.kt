package com.example.flipicklms

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.flipicklms.Adapters.ViewAllAdapter
import com.example.flipicklms.Adapters.ViewAllPagerAdapter
import com.example.flipicklms.Resources.Data
import com.example.flipicklms.Resources.SharedPrefManager
import me.relex.circleindicator.CircleIndicator
import java.util.ArrayList


class ViewAllActivity : AppCompatActivity() {
    lateinit var imgProfile:ImageView
    lateinit var imgSearch:ImageView
    var viewPager: ViewPager? = null
    var indicator: CircleIndicator? = null
    val slider_modelArrayList: ArrayList<Data> = ArrayList<Data>()
    lateinit var recyclerView: RecyclerView
    private lateinit var viewAllAdapter: ViewAllAdapter
    var myCustomPagerAdapter: ViewAllPagerAdapter? = null
    var dataList = arrayListOf<Data>()
    var instanceShared: SharedPrefManager? = null
    lateinit var tvBack:TextView
    lateinit var tvTitle:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        instanceShared =
            SharedPrefManager(applicationContext)
        val includedLayout: View = findViewById(R.id.allDetailsToolBarLayout)
        imgProfile=includedLayout.findViewById(R.id.profile)
        imgSearch=includedLayout.findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recycler_viewAll)
        viewPager = findViewById(R.id.viewAllPager)
        indicator = findViewById(R.id.viewAllindicator)
        tvBack=findViewById(R.id.tvBack)
        tvTitle=findViewById(R.id.tvTitle)
        setData()

        imgSearch.setOnClickListener {
//            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)

        }
    tvBack.setOnClickListener {
        finish()
    }

        val metrics = DisplayMetrics()
        windowManager
            .defaultDisplay
            .getMetrics(metrics)
        val yInches = metrics.heightPixels / metrics.ydpi
        val xInches = metrics.widthPixels / metrics.xdpi
        val diagonalInches = Math.sqrt((xInches * xInches + yInches * yInches).toDouble())
        if (diagonalInches >= 6.5) {
            // Recycler View Properties
            recyclerView.layoutManager = GridLayoutManager(applicationContext,3)
        } else {
            recyclerView.layoutManager = GridLayoutManager(applicationContext,2)

        }

        val decoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
        recyclerView.setHasFixedSize(true)
        imgProfile.setOnClickListener {
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    fun setData()
    {
        dataList.clear()
        for(k in 0 until MainActivity.dataModelArrayList.size)
        {
            if (intent.getStringExtra("type").equals(MainActivity.dataModelArrayList[k].type))
            {
                tvTitle.setText(intent.getStringExtra("type"))
            val showModel = Data()
            showModel.courseName = MainActivity.dataModelArrayList[k].courseName
            showModel.courseDescription = MainActivity.dataModelArrayList[k].courseDescription
            showModel.courseThumbnail = MainActivity.dataModelArrayList[k].courseThumbnail
            showModel.ageGuidance = MainActivity.dataModelArrayList[k].ageGuidance
            showModel.videoQuality = MainActivity.dataModelArrayList[k].videoQuality
            showModel.videoURL = MainActivity.dataModelArrayList[k].videoURL
            if (MainActivity.dataModelArrayList[k].courseHours.equals(""))
            {
                showModel.courseHours="00:00:00"
            }
            else {
                showModel.courseHours = MainActivity.dataModelArrayList[k].courseHours
            }
            showModel.courseId=MainActivity.dataModelArrayList[k].courseId
            showModel.courseSequence = MainActivity.dataModelArrayList[k].courseSequence

                if (MainActivity.dataModelArrayList[k].featured.toString()=="1")
                {
                    slider_modelArrayList.add(showModel)
                }
                dataList.add(showModel)
                viewAllAdapter =
                    ViewAllAdapter(
                        this, dataList
                    )
                recyclerView.adapter = viewAllAdapter
                viewAllAdapter.notifyDataSetChanged()
            }
        }

        if (slider_modelArrayList.size>0) {
            viewPager!!.visibility=View.GONE
            indicator!!.visibility=View.GONE
            myCustomPagerAdapter =
                ViewAllPagerAdapter(
                    this.applicationContext,
                    slider_modelArrayList
                )
            viewPager!!.adapter = myCustomPagerAdapter
            indicator!!.setViewPager(viewPager)
            myCustomPagerAdapter!!.notifyDataSetChanged()
        }

    }

    override fun onBackPressed() {
        dataList.clear()
        super.onBackPressed()
    }
}

