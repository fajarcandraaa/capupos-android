package com.mindtoscreen.cappupos.presentation.profilusaha

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mindtoscreen.cappupos.R
import com.mindtoscreen.cappupos.databinding.ActivityProfilUsahaBinding
import com.mindtoscreen.cappupos.domain.model.Store
import com.mindtoscreen.cappupos.presentation.export.ExportActivity
import com.mindtoscreen.cappupos.presentation.struk.StrukActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Form profil usaha. FR-10: ubah nama, logo, kategori, deskripsi, alamat,
 * telepon. Juga berisi akses ke Export (satu entry "Profil/Pengaturan",
 * DECISIONS.md [2026-09-14] poin 7).
 */
@AndroidEntryPoint
class ProfilUsahaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilUsahaBinding
    private val viewModel: ProfilUsahaViewModel by viewModels()
    private var selectedLogoUri: Uri? = null
    private var currentLogo: String? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedLogoUri = it
            binding.ivLogo.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilUsahaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupLogoPicker()
        setupButtons()
        observeStore()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupLogoPicker() {
        binding.ivLogo.setOnClickListener { pickImageLauncher.launch("image/*") }
    }

    private fun observeStore() {
        lifecycleScope.launch {
            viewModel.store.collect { store ->
                store ?: return@collect
                binding.editNama.setText(store.nama)
                binding.editAlamat.setText(store.alamat)
                binding.editTelepon.setText(store.telepon ?: "")
                binding.editKategori.setText(store.kategori ?: "")
                binding.editDeskripsi.setText(store.deskripsi ?: "")
                currentLogo = store.logo
                store.logo?.let { binding.ivLogo.setImageURI(Uri.parse(it)) }
            }
        }
    }

    private fun setupButtons() {
        binding.btnSimpan.setOnClickListener { simpanProfil() }
        binding.btnExport.setOnClickListener {
            startActivity(android.content.Intent(this, ExportActivity::class.java))
        }
        binding.btnStrukTerakhir.setOnClickListener {
            val orderId = viewModel.lastLunasOrderId.value
            if (orderId != null) {
                startActivity(StrukActivity.createIntent(this, orderId))
            } else {
                Toast.makeText(this, getString(R.string.msg_belum_ada_struk), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun simpanProfil() {
        val nama = binding.editNama.text.toString().trim()
        val alamat = binding.editAlamat.text.toString().trim()

        if (nama.isEmpty()) {
            binding.editNama.error = getString(R.string.error_nama_usaha_wajib)
            return
        }
        if (alamat.isEmpty()) {
            binding.editAlamat.error = getString(R.string.error_alamat_usaha_wajib)
            return
        }

        val store = Store(
            nama = nama,
            alamat = alamat,
            logo = selectedLogoUri?.toString() ?: currentLogo,
            kategori = binding.editKategori.text.toString().trim().ifEmpty { null },
            deskripsi = binding.editDeskripsi.text.toString().trim().ifEmpty { null },
            telepon = binding.editTelepon.text.toString().trim().ifEmpty { null }
        )

        lifecycleScope.launch {
            viewModel.simpan(store).onSuccess {
                Toast.makeText(this@ProfilUsahaActivity, getString(R.string.msg_profil_tersimpan), Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(this@ProfilUsahaActivity, getString(R.string.msg_gagal_simpan_profil), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
