package com.mindtoscreen.cappupos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mindtoscreen.cappupos.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}