package com.mindtoscreen.cappupos.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migration from version 3 to version 4
 * Changes: create stock_history table for stock change history report (TASK-006).
 * Column names are camelCase to match Room entity field names (no @ColumnInfo),
 * per DECISIONS.md [2026-09-08] migration rule.
 */
class MigrationV3ToV4 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS stock_history (" +
                "id TEXT NOT NULL PRIMARY KEY, " +
                "productId TEXT NOT NULL, " +
                "quantityBefore INTEGER NOT NULL, " +
                "quantityAfter INTEGER NOT NULL, " +
                "reason TEXT NOT NULL, " +
                "timestamp INTEGER NOT NULL, " +
                "FOREIGN KEY(productId) REFERENCES products(id) ON DELETE CASCADE" +
                ")"
        )
        database.execSQL("CREATE INDEX IF NOT EXISTS index_stock_history_productId ON stock_history(productId)")
    }
}
