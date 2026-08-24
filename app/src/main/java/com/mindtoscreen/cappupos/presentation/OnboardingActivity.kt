package com.mindtoscreen.cappupos.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mindtoscreen.cappupos.R

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Skip onboarding after first use (simplified)
        findViewById<View>(R.id.btn_start).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}