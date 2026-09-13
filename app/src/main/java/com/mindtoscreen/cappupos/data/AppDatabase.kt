package com.mindtoscreen.cappupos.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mindtoscreen.cappupos.data.dao.*
import com.mindtoscreen.cappupos.data.entities.*

/**
 * Kategori default dengan id eksplisit string lama (bukan UUID) agar produk
 * legacy yang menyimpan kategoriId "makanan"/"minuman"/"penyedap" tetap
 * ter-resolve, dan fresh install tidak kosong. TASK-008.
 */
private val DEFAULT_CATEGORIES = listOf(
    CategoryEntity(id = "makanan", nama = "Makanan", urutan = 0),
    CategoryEntity(id = "minuman", nama = "Minuman", urutan = 1),
    CategoryEntity(id = "penyedap", nama = "Penyedap", urutan = 2)
)

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        OrderEntity::class,
        OrderDetailEntity::class,
        StockHistoryEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao
    abstract fun stockHistoryDao(): StockHistoryDao

    companion object {
        val MIGRATION_1_2 = MigrationV1ToV2()
        val MIGRATION_2_3 = MigrationV2ToV3()
        val MIGRATION_3_4 = MigrationV3ToV4()

        /**
         * Seed default kategori saat DB pertama kali dibuat (onCreate) dan saat
         * dibuka (onOpen) bila tabel kosong. Idempotent: cek kosong dulu, tidak
         * insert ulang tiap start. TASK-008.
         */
        val CALLBACK = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                seedDefaultKategori(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                seedDefaultKategori(db)
            }
        }

        private fun seedDefaultKategori(db: SupportSQLiteDatabase) {
            val cursor = db.query("SELECT COUNT(*) FROM categories")
            val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
            cursor.close()
            if (count > 0) return

            DEFAULT_CATEGORIES.forEach { kategori ->
                db.execSQL(
                    "INSERT INTO categories (id, nama, urutan, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)",
                    arrayOf(
                        kategori.id,
                        kategori.nama,
                        kategori.urutan,
                        0L,
                        0L
                    )
                )
            }
        }
    }
}
