package com.mindtoscreen.cappupos.presentation.export

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityExportBinding
import com.mindtoscreen.cappupos.domain.usecase.CekReminderBackupUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * Export data ke Excel (FR-13): 3 sheet (Transaksi, Produk, Laporan Ringkas),
 * share/save via Android Sharesheet lewat FileProvider (cache-path "exports").
 */
@AndroidEntryPoint
class ExportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExportBinding
    private val viewModel: ExportViewModel by viewModels()

    @Inject
    lateinit var cekReminderBackupUseCase: CekReminderBackupUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupExportButton()
        observeExport()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupExportButton() {
        binding.btnMulaiExport.setOnClickListener {
            val outputDir = File(cacheDir, "exports").apply { mkdirs() }
            viewModel.export(outputDir)
        }
    }

    private fun observeExport() {
        lifecycleScope.launch {
            viewModel.exportedFile.collect { file ->
                file?.let { shareFile(it) }
            }
        }
        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    android.widget.Toast.makeText(this@ExportActivity, getString(R.string.msg_gagal_export), android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun shareFile(file: File) {
        cekReminderBackupUseCase.markReminderShown()
        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.btn_export_data)))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
