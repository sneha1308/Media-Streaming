package com.example.mediastreaming.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mediastreaming.R
import com.example.mediastreaming.databinding.ActivityMediaBinding
import com.example.mediastreaming.network.RetroInstance
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.activity_media.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MediaActivity : AppCompatActivity(), View.OnClickListener {
    private var player: ExoPlayer? = null
    private var uri1: String = ""
    private var uri2: String = ""
    private var uri3: String = ""

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMediaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        supportActionBar?.hide()
        ivSettings.setOnClickListener(this)
        makeApiCall()
    }

    private fun makeApiCall() {
        val call: Call<MediaStream> =
            RetroInstance.getInstance().create(RetroService::class.java)
                .getMediaUrls(resources.getString(R.string.base_url))
        call.enqueue(object : Callback<MediaStream> {
            override fun onResponse(call: Call<MediaStream>, response: Response<MediaStream>) {
                if (response.body() != null) {
                    uri1 = response.body()!!.manifest.url1
                    uri2 = response.body()!!.manifest.url2
                    uri3 = response.body()!!.manifest.url3

                    setUpViewPager()
                }
            }

            override fun onFailure(call: Call<MediaStream>, t: Throwable) {
                return
            }

        })
    }

    fun setUpViewPager() {
        val adapter = ViewPagerAdapter(uri1, uri2, uri3,this)
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                changeColor()

            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeColor()

            }
        })
    }

    fun changeColor() {
        when (viewPager.currentItem) {
            0 -> {
                ivSilder1.setBackgroundColor(applicationContext.resources.getColor(R.color.purple_200))
                ivSilder2.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                ivSilder3.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
            }
            1 -> {
                ivSilder1.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                ivSilder2.setBackgroundColor(applicationContext.resources.getColor(R.color.purple_200))
                ivSilder3.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
            }
            2 -> {
                ivSilder1.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                ivSilder2.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                ivSilder3.setBackgroundColor(applicationContext.resources.getColor(R.color.purple_200))
            }

        }
    }

    fun setLandScape(holder: ViewPagerAdapter.ViewPagerViewHolder){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
           window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        if (supportActionBar != null)
            supportActionBar?.hide()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivSettings ->{
                startActivity(Intent(this, SettingsActivity::class.java))
            }

        }
    }
}



