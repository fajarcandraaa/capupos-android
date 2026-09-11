package com.mindtoscreen.cappupos.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migration from version 2 to version 3
 * Changes: add deskripsi column to order_details table for manual transaction descriptions.
 * Column name is camelCase to match Room entity field name (no @ColumnInfo).
 */
class MigrationV2ToV3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE order_details ADD COLUMN deskripsi TEXT NULL")
    }
}
