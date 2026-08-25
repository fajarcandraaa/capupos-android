package com.mindtoscreen.cappupos.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.domain.usecase.CekProdukKosongUseCase
import com.mindtoscreen.cappupos.presentation.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    @Inject
    lateinit var cekProdukKosongUseCase: CekProdukKosongUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if product exists (FR-14.1)
        lifecycleScope.launch {
            val isProductEmpty = cekProdukKosongUseCase.execute()
            if (isProductEmpty) {
                // Show empty state with CTA (FR-14.2)
                showEmptyStateDialog()
            } else {
                // Navigate to Home directly
                startActivity(Intent(this@OnboardingActivity, HomeActivity::class.java))
                finish()
            }
        }
    }

    private fun showEmptyStateDialog() {
        val view = layoutInflater.inflate(R.layout.activity_onboarding_empty_state, null)
        
        AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()
            .apply {
                // Set content
                view.findViewById<Button>(R.id.btn_tambah_produk_pertama).setOnClickListener {
                    // Open add product flow
                    startActivity(Intent(this@OnboardingActivity, AddProductActivity::class.java))
                    dismiss()
                }
                show()
            }
    }
}
