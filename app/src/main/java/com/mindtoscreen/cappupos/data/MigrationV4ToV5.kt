package com.mindtoscreen.cappupos.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migration from version 4 to version 5
 * Changes: register stores table for Profil Usaha (TASK-007, FR-10).
 * StoreEntity existed since TASK-001 setup but was never wired into the DB,
 * so the stores table does not exist yet. Create it with the full schema
 * (id, nama, alamat, logo, kategori, deskripsi, telepon). Column names are
 * camelCase to match Room entity field names (no @ColumnInfo), per
 * DECISIONS.md [2026-09-08] migration rule.
 */
class MigrationV4ToV5 : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS stores (" +
                "id INTEGER NOT NULL PRIMARY KEY, " +
                "nama TEXT NOT NULL, " +
                "alamat TEXT NOT NULL, " +
                "logo TEXT NULL, " +
                "kategori TEXT NULL, " +
                "deskripsi TEXT NULL, " +
                "telepon TEXT NULL" +
                ")"
        )
    }
}
