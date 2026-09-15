package com.mindtoscreen.cappupos.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migration from version 1 to version 2
 * Changes: full schema update to match SDD.
 * Column names are camelCase to match Room entity field names (no @ColumnInfo).
 */
class MigrationV1ToV2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create new products table with correct schema
        database.execSQL("""
            CREATE TABLE products_new (
                id TEXT NOT NULL PRIMARY KEY,
                nama TEXT NOT NULL DEFAULT '',
                foto TEXT NULL,
                kategoriId TEXT NULL,
                harga REAL NOT NULL DEFAULT 0.0,
                deskripsi TEXT NULL,
                lacakStok INTEGER NOT NULL DEFAULT 0,
                jumlahStok INTEGER NULL,
                stokMinimal INTEGER NULL,
                createdAt INTEGER NOT NULL DEFAULT 0,
                updatedAt INTEGER NOT NULL DEFAULT 0,
                isDeleted INTEGER NOT NULL DEFAULT 0,
                deletedAt INTEGER NULL
            )
        """)

        // Migrate existing products
        database.execSQL("""
            INSERT INTO products_new (id, nama, harga, createdAt, updatedAt)
            SELECT CAST(id AS TEXT), name, price, 0, 0 FROM products
        """)

        database.execSQL("DROP TABLE products")
        database.execSQL("ALTER TABLE products_new RENAME TO products")

        // Create new categories table
        database.execSQL("""
            CREATE TABLE categories_new (
                id TEXT NOT NULL PRIMARY KEY,
                nama TEXT NOT NULL DEFAULT '',
                urutan INTEGER NOT NULL DEFAULT 0,
                createdAt INTEGER NOT NULL DEFAULT 0,
                updatedAt INTEGER NOT NULL DEFAULT 0
            )
        """)

        database.execSQL("""
            INSERT INTO categories_new (id, nama, urutan, createdAt, updatedAt)
            SELECT CAST(id AS TEXT), name, 0, 0, 0 FROM categories
        """)

        database.execSQL("DROP TABLE categories")
        database.execSQL("ALTER TABLE categories_new RENAME TO categories")

        // Create new orders table
        database.execSQL("""
            CREATE TABLE orders_new (
                id TEXT NOT NULL PRIMARY KEY,
                status TEXT NOT NULL DEFAULT 'belum_bayar',
                statusPo TEXT NULL,
                metodeBayar TEXT NULL,
                subtotal REAL NOT NULL DEFAULT 0.0,
                nominalDiterima REAL NULL,
                kembalian REAL NULL,
                catatan TEXT NULL,
                tanggal INTEGER NOT NULL DEFAULT 0,
                isHidden INTEGER NOT NULL DEFAULT 0,
                isDeleted INTEGER NOT NULL DEFAULT 0,
                deletedAt INTEGER NULL,
                createdAt INTEGER NOT NULL DEFAULT 0,
                updatedAt INTEGER NOT NULL DEFAULT 0
            )
        """)

        database.execSQL("""
            INSERT INTO orders_new (id, status, subtotal, tanggal, createdAt, updatedAt)
            SELECT CAST(id AS TEXT), 'belum_bayar', total, timestamp, 0, 0 FROM orders
        """)

        database.execSQL("DROP TABLE orders")
        database.execSQL("ALTER TABLE orders_new RENAME TO orders")

        // Create new order_details table
        database.execSQL("""
            CREATE TABLE order_details_new (
                id TEXT NOT NULL PRIMARY KEY,
                orderId TEXT NOT NULL,
                productId TEXT NULL,
                quantity INTEGER NOT NULL DEFAULT 0,
                price REAL NOT NULL DEFAULT 0.0,
                FOREIGN KEY (orderId) REFERENCES orders(id) ON DELETE CASCADE,
                FOREIGN KEY (productId) REFERENCES products(id) ON DELETE SET NULL
            )
        """)

        database.execSQL("DROP TABLE order_details")
        database.execSQL("ALTER TABLE order_details_new RENAME TO order_details")
    }
}
