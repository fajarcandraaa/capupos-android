package com.mindtoscreen.cappupos.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mindtoscreen.cappupos.data.entities.ProductEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProductEntity> __insertionAdapterOfProductEntity;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfHardDelete;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProductEntity = new EntityInsertionAdapter<ProductEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `products` (`id`,`nama`,`foto`,`kategoriId`,`harga`,`deskripsi`,`lacakStok`,`jumlahStok`,`stokMinimal`,`createdAt`,`updatedAt`,`isDeleted`,`deletedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getNama() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNama());
        }
        if (entity.getFoto() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getFoto());
        }
        if (entity.getKategoriId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getKategoriId());
        }
        statement.bindDouble(5, entity.getHarga());
        if (entity.getDeskripsi() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDeskripsi());
        }
        final int _tmp = entity.getLacakStok() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getJumlahStok() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getJumlahStok());
        }
        if (entity.getStokMinimal() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getStokMinimal());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        if (entity.getDeletedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getDeletedAt());
        }
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE products SET isDeleted = 1, deletedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfHardDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM products WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ProductEntity product, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProductEntity.insert(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<ProductEntity> products,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProductEntity.insert(products);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object softDelete(final String productId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        if (productId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, productId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSoftDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object hardDelete(final String productId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHardDelete.acquire();
        int _argIndex = 1;
        if (productId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, productId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfHardDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAll(final Continuation<? super List<ProductEntity>> $completion) {
    final String _sql = "SELECT * FROM products";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfFoto = CursorUtil.getColumnIndexOrThrow(_cursor, "foto");
          final int _cursorIndexOfKategoriId = CursorUtil.getColumnIndexOrThrow(_cursor, "kategoriId");
          final int _cursorIndexOfHarga = CursorUtil.getColumnIndexOrThrow(_cursor, "harga");
          final int _cursorIndexOfDeskripsi = CursorUtil.getColumnIndexOrThrow(_cursor, "deskripsi");
          final int _cursorIndexOfLacakStok = CursorUtil.getColumnIndexOrThrow(_cursor, "lacakStok");
          final int _cursorIndexOfJumlahStok = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlahStok");
          final int _cursorIndexOfStokMinimal = CursorUtil.getColumnIndexOrThrow(_cursor, "stokMinimal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpNama;
            if (_cursor.isNull(_cursorIndexOfNama)) {
              _tmpNama = null;
            } else {
              _tmpNama = _cursor.getString(_cursorIndexOfNama);
            }
            final String _tmpFoto;
            if (_cursor.isNull(_cursorIndexOfFoto)) {
              _tmpFoto = null;
            } else {
              _tmpFoto = _cursor.getString(_cursorIndexOfFoto);
            }
            final String _tmpKategoriId;
            if (_cursor.isNull(_cursorIndexOfKategoriId)) {
              _tmpKategoriId = null;
            } else {
              _tmpKategoriId = _cursor.getString(_cursorIndexOfKategoriId);
            }
            final double _tmpHarga;
            _tmpHarga = _cursor.getDouble(_cursorIndexOfHarga);
            final String _tmpDeskripsi;
            if (_cursor.isNull(_cursorIndexOfDeskripsi)) {
              _tmpDeskripsi = null;
            } else {
              _tmpDeskripsi = _cursor.getString(_cursorIndexOfDeskripsi);
            }
            final boolean _tmpLacakStok;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfLacakStok);
            _tmpLacakStok = _tmp != 0;
            final Integer _tmpJumlahStok;
            if (_cursor.isNull(_cursorIndexOfJumlahStok)) {
              _tmpJumlahStok = null;
            } else {
              _tmpJumlahStok = _cursor.getInt(_cursorIndexOfJumlahStok);
            }
            final Integer _tmpStokMinimal;
            if (_cursor.isNull(_cursorIndexOfStokMinimal)) {
              _tmpStokMinimal = null;
            } else {
              _tmpStokMinimal = _cursor.getInt(_cursorIndexOfStokMinimal);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsDeleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_1 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new ProductEntity(_tmpId,_tmpNama,_tmpFoto,_tmpKategoriId,_tmpHarga,_tmpDeskripsi,_tmpLacakStok,_tmpJumlahStok,_tmpStokMinimal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getActiveProducts(final Continuation<? super List<ProductEntity>> $completion) {
    final String _sql = "SELECT * FROM products WHERE isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfFoto = CursorUtil.getColumnIndexOrThrow(_cursor, "foto");
          final int _cursorIndexOfKategoriId = CursorUtil.getColumnIndexOrThrow(_cursor, "kategoriId");
          final int _cursorIndexOfHarga = CursorUtil.getColumnIndexOrThrow(_cursor, "harga");
          final int _cursorIndexOfDeskripsi = CursorUtil.getColumnIndexOrThrow(_cursor, "deskripsi");
          final int _cursorIndexOfLacakStok = CursorUtil.getColumnIndexOrThrow(_cursor, "lacakStok");
          final int _cursorIndexOfJumlahStok = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlahStok");
          final int _cursorIndexOfStokMinimal = CursorUtil.getColumnIndexOrThrow(_cursor, "stokMinimal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpNama;
            if (_cursor.isNull(_cursorIndexOfNama)) {
              _tmpNama = null;
            } else {
              _tmpNama = _cursor.getString(_cursorIndexOfNama);
            }
            final String _tmpFoto;
            if (_cursor.isNull(_cursorIndexOfFoto)) {
              _tmpFoto = null;
            } else {
              _tmpFoto = _cursor.getString(_cursorIndexOfFoto);
            }
            final String _tmpKategoriId;
            if (_cursor.isNull(_cursorIndexOfKategoriId)) {
              _tmpKategoriId = null;
            } else {
              _tmpKategoriId = _cursor.getString(_cursorIndexOfKategoriId);
            }
            final double _tmpHarga;
            _tmpHarga = _cursor.getDouble(_cursorIndexOfHarga);
            final String _tmpDeskripsi;
            if (_cursor.isNull(_cursorIndexOfDeskripsi)) {
              _tmpDeskripsi = null;
            } else {
              _tmpDeskripsi = _cursor.getString(_cursorIndexOfDeskripsi);
            }
            final boolean _tmpLacakStok;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfLacakStok);
            _tmpLacakStok = _tmp != 0;
            final Integer _tmpJumlahStok;
            if (_cursor.isNull(_cursorIndexOfJumlahStok)) {
              _tmpJumlahStok = null;
            } else {
              _tmpJumlahStok = _cursor.getInt(_cursorIndexOfJumlahStok);
            }
            final Integer _tmpStokMinimal;
            if (_cursor.isNull(_cursorIndexOfStokMinimal)) {
              _tmpStokMinimal = null;
            } else {
              _tmpStokMinimal = _cursor.getInt(_cursorIndexOfStokMinimal);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsDeleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_1 != 0;
            final Long _tmpDeletedAt;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmpDeletedAt = null;
            } else {
              _tmpDeletedAt = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _item = new ProductEntity(_tmpId,_tmpNama,_tmpFoto,_tmpKategoriId,_tmpHarga,_tmpDeskripsi,_tmpLacakStok,_tmpJumlahStok,_tmpStokMinimal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countActiveProducts(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM products WHERE isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
