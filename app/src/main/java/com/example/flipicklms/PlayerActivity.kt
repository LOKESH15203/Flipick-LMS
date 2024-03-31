package com.example.flipicklms

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.annotation.OptIn
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.source.ads.AdsMediaSource
import com.example.flipicklms.Resources.SharedPrefManager
class PlayerActivity : AppCompatActivity() {
    var instanceShared: SharedPrefManager? = null
    private var simpleExoPlayer_: ExoPlayer? = null
    private lateinit var playerView_: VideoView
//    var adsLoader: ImaAdsLoader? = null
    var fullScreen: Boolean = false
    lateinit var progressBar: ProgressBar
    lateinit var imgfullScreen:ImageView
    lateinit var textPlayerTitle:TextView
    lateinit var imgNormal:ImageView

//    lateinit var playerLayout: RelativeLayout






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        progressBar=findViewById(R.id.progressBar)
        playerView_=findViewById(R.id.player_View)
        imgfullScreen=findViewById(R.id.fullScreen)
        textPlayerTitle=findViewById(R.id.textPlayerTitle)
        imgNormal=findViewById(R.id.img_normal)
//        playerLayout = findViewById(R.id.details_player_layout)







        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

//        if (adsLoader != null) {
//            adsLoader!!.release();
//        }

        instanceShared =
            SharedPrefManager(applicationContext)

        imgfullScreen.setOnClickListener {
//            makePlayerFullScreen_()
            screenOrientation()
        }
        imgNormal.setOnClickListener {
//            makePlayerNormal()
            screenOrientationNormal()
        }

    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun makePlayerNormal() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        fullScreen = false
        imgNormal.visibility = View.GONE
        imgfullScreen.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed({
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }, 4000)
    }

    fun screenOrientationNormal() {
        val orientation = this.resources.configuration.orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        textPlayerTitle.setVisibility(View.VISIBLE)
        imgfullScreen.visibility=View.VISIBLE
        imgNormal.visibility=View.GONE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val layout: RelativeLayout = findViewById(R.id.details_player_layout)
//        layoutToolbar.visibility = View.GONE
        val layoutParams = layout.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.height = (300 * Resources.getSystem().displayMetrics.density).toInt()
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        imgfullScreen.setVisibility(View.GONE)
    }


    fun screenOrientation() {
        val orientation = this.resources.configuration.orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        textPlayerTitle.setVisibility(View.GONE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val layout: RelativeLayout = findViewById(R.id.details_player_layout)
//        layoutToolbar.visibility = View.GONE
        val layoutParams = layout.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        imgfullScreen.setVisibility(View.GONE)
        imgNormal.visibility=View.VISIBLE
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            imgfullScreen.visibility = View.VISIBLE
//            imgNormal.visibility = View.GONE
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imgfullScreen.visibility = View.GONE
//            imgNormal.visibility = View.VISIBLE

        }
    }

    override fun onResume() {
        super.onResume()
//        play_Video("")
        textPlayerTitle.setText(intent.getStringExtra("title").toString())
        play_Video_fullScreen(intent.getStringExtra("videoUrl").toString())
    }
    override fun onPause() {
        super.onPause()
        if (simpleExoPlayer_ != null) {
            simpleExoPlayer_!!.release()
        }
    }

    fun play_Video_fullScreen(url: String) {

        Log.d("zzz", "VideoUrl BookUrl:   " + url)
        if (simpleExoPlayer_ != null) {
            simpleExoPlayer_!!.release()
        }
        if (fullScreen == false) {
//            playerThumb.visibility = View.VISIBLE
        }
        simpleExoPlayer_ = ExoPlayer.Builder(this).build()
//        adsLoader!!.setPlayer(simpleExoPlayer_);
//        val dataSpec = DataSpec(Uri.parse(intent.getStringExtra("adUrl")))

        Log.d("zzz","AdUrl  "+intent.getStringExtra("adUrl"))

//        val dataSpec =
//            DataSpec(Uri.parse("https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/vmap_ad_samples&sz=640x480&cust_params=sar%3Da0f2&ciu_szs=300x250&ad_rule=1&gdfp_req=1&output=vmap&unviewed_position_start=1&env=vp&impl=s&correlator="))


//        val imaSdkSettings = ImaSdkFactory.getInstance().createImaSdkSettings()


        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        if (url.contains(".m3u8")) {
            Log.d("zzz", "VideoUrl" + url)
            val mediaItem = MediaItem.fromUri(url)
//            val mediaSource =
//                HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
////            adsLoader = ImaAdsLoader.Builder(this).setImaSdkSettings(imaSdkSettings).build()
////            adsLoader!!.setPlayer(simpleExoPlayer_)
////            val adsMediaSource = AdsMediaSource(
////                mediaSource,
////                dataSpec,
////                0,
////                DefaultMediaSourceFactory(this),
////                adsLoader!!,
////                playerView_
////            )
//            simpleExoPlayer_?.setMediaSource(adsMediaSource)

        } else {
            Log.d("zzz", "VideoUrl++++ mp4" + url)
            val mediaItem = MediaItem.fromUri(url)

//            val mediaSource =
//                ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
//                    .createMediaSource(mediaItem)
//            adsLoader = ImaAdsLoader.Builder(this).setImaSdkSettings(imaSdkSettings).build()
//            adsLoader!!.setPlayer(simpleExoPlayer_)
//            val adsMediaSource = AdsMediaSource(
//                mediaSource,
//                dataSpec,
//                0,
//                DefaultMediaSourceFactory(this),
//                adsLoader!!,
//                playerView_
//            )
//            simpleExoPlayer_?.setMediaSource(adsMediaSource)
//            playerView_.player = simpleExoPlayer_
//            simpleExoPlayer_!!.setMediaItem(mediaItem)
        }
        simpleExoPlayer_?.seekTo(0)
        simpleExoPlayer_?.prepare()
        simpleExoPlayer_?.playWhenReady = true
        simpleExoPlayer_!!.addListener(@UnstableApi object : Player.Listener {
            fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {
            }

//            override fun onLoadingChanged(isLoading: Boolean) {
//            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
//                        playerThumb.visibility = View.GONE
                        progressBar.visibility = View.GONE

                    }
                    Player.STATE_BUFFERING -> {
                        progressBar.visibility = View.VISIBLE

                    }
                    Player.STATE_ENDED -> {

                        finish()
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





}