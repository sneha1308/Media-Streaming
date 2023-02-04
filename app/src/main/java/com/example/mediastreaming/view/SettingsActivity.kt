package com.example.mediastreaming.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mediastreaming.R

class SettingsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = resources.getString(R.string.toolbar_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}