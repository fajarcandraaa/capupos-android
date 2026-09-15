package com.mindtoscreen.cappupos.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migration from version 5 to version 6
 * Changes: add nullable `namaItem` column to order_details for item name
 * snapshot (TASK-009, SDD §5.2). Column name camelCase to match Room entity
 * field (no @ColumnInfo), per DECISIONS.md [2026-09-08] migration rule.
 * NULL default — existing rows keep null, no data loss.
 */
class MigrationV5ToV6 : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE order_details ADD COLUMN namaItem TEXT NULL"
        )
    }
}
