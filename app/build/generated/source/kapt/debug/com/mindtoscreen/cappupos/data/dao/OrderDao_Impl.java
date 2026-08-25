package com.mindtoscreen.cappupos.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mindtoscreen.cappupos.data.entities.OrderEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
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
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OrderEntity> __insertionAdapterOfOrderEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePayment;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  private final SharedSQLiteStatement __preparedStmtOfHide;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfHardDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteItemsForOrder;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrderEntity = new EntityInsertionAdapter<OrderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `orders` (`id`,`status`,`statusPo`,`metodeBayar`,`subtotal`,`nominalDiterima`,`kembalian`,`catatan`,`tanggal`,`isHidden`,`isDeleted`,`deletedAt`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrderEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getStatus());
        }
        if (entity.getStatusPo() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getStatusPo());
        }
        if (entity.getMetodeBayar() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getMetodeBayar());
        }
        statement.bindDouble(5, entity.getSubtotal());
        if (entity.getNominalDiterima() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getNominalDiterima());
        }
        if (entity.getKembalian() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getKembalian());
        }
        if (entity.getCatatan() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCatatan());
        }
        statement.bindLong(9, entity.getTanggal());
        final int _tmp = entity.isHidden() ? 1 : 0;
        statement.bindLong(10, _tmp);
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        if (entity.getDeletedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getDeletedAt());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfUpdatePayment = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET status = ?, metodeBayar = ?, nominalDiterima = ?, kembalian = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET status = ?, statusPo = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfHide = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET isHidden = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET isDeleted = 1, deletedAt = ? WHERE id = ? AND status = 'lunas'";
        return _query;
      }
    };
    this.__preparedStmtOfHardDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM orders WHERE id = ? AND status = 'belum_bayar'";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteItemsForOrder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM order_details WHERE orderId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final OrderEntity order, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderEntity.insert(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<OrderEntity> orders,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderEntity.insert(orders);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePayment(final String orderId, final String status, final String metode,
      final double nominal, final double kembalian, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePayment.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        if (metode == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, metode);
        }
        _argIndex = 3;
        _stmt.bindDouble(_argIndex, nominal);
        _argIndex = 4;
        _stmt.bindDouble(_argIndex, kembalian);
        _argIndex = 5;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 6;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
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
          __preparedStmtOfUpdatePayment.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final String orderId, final String status, final String statusPo,
      final long updatedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        if (statusPo == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, statusPo);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 4;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
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
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object hide(final String orderId, final boolean hidden,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHide.acquire();
        int _argIndex = 1;
        final int _tmp = hidden ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
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
          __preparedStmtOfHide.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object softDelete(final String orderId, final long deletedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, deletedAt);
        _argIndex = 2;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
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
  public Object hardDelete(final String orderId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHardDelete.acquire();
        int _argIndex = 1;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
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
  public Object deleteItemsForOrder(final String orderId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteItemsForOrder.acquire();
        int _argIndex = 1;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
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
          __preparedStmtOfDeleteItemsForOrder.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getBelumBayar(final Continuation<? super List<OrderEntity>> $completion) {
    final String _sql = "SELECT * FROM orders WHERE status = 'belum_bayar' AND isDeleted = 0 AND isHidden = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<OrderEntity>>() {
      @Override
      @NonNull
      public List<OrderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStatusPo = CursorUtil.getColumnIndexOrThrow(_cursor, "statusPo");
          final int _cursorIndexOfMetodeBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "metodeBayar");
          final int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "subtotal");
          final int _cursorIndexOfNominalDiterima = CursorUtil.getColumnIndexOrThrow(_cursor, "nominalDiterima");
          final int _cursorIndexOfKembalian = CursorUtil.getColumnIndexOrThrow(_cursor, "kembalian");
          final int _cursorIndexOfCatatan = CursorUtil.getColumnIndexOrThrow(_cursor, "catatan");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpStatusPo;
            if (_cursor.isNull(_cursorIndexOfStatusPo)) {
              _tmpStatusPo = null;
            } else {
              _tmpStatusPo = _cursor.getString(_cursorIndexOfStatusPo);
            }
            final String _tmpMetodeBayar;
            if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
              _tmpMetodeBayar = null;
            } else {
              _tmpMetodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
            }
            final double _tmpSubtotal;
            _tmpSubtotal = _cursor.getDouble(_cursorIndexOfSubtotal);
            final Double _tmpNominalDiterima;
            if (_cursor.isNull(_cursorIndexOfNominalDiterima)) {
              _tmpNominalDiterima = null;
            } else {
              _tmpNominalDiterima = _cursor.getDouble(_cursorIndexOfNominalDiterima);
            }
            final Double _tmpKembalian;
            if (_cursor.isNull(_cursorIndexOfKembalian)) {
              _tmpKembalian = null;
            } else {
              _tmpKembalian = _cursor.getDouble(_cursorIndexOfKembalian);
            }
            final String _tmpCatatan;
            if (_cursor.isNull(_cursorIndexOfCatatan)) {
              _tmpCatatan = null;
            } else {
              _tmpCatatan = _cursor.getString(_cursorIndexOfCatatan);
            }
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final boolean _tmpIsHidden;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsHidden);
            _tmpIsHidden = _tmp != 0;
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
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new OrderEntity(_tmpId,_tmpStatus,_tmpStatusPo,_tmpMetodeBayar,_tmpSubtotal,_tmpNominalDiterima,_tmpKembalian,_tmpCatatan,_tmpTanggal,_tmpIsHidden,_tmpIsDeleted,_tmpDeletedAt,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getAllOrders(final Continuation<? super List<OrderEntity>> $completion) {
    final String _sql = "SELECT * FROM orders WHERE isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<OrderEntity>>() {
      @Override
      @NonNull
      public List<OrderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStatusPo = CursorUtil.getColumnIndexOrThrow(_cursor, "statusPo");
          final int _cursorIndexOfMetodeBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "metodeBayar");
          final int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "subtotal");
          final int _cursorIndexOfNominalDiterima = CursorUtil.getColumnIndexOrThrow(_cursor, "nominalDiterima");
          final int _cursorIndexOfKembalian = CursorUtil.getColumnIndexOrThrow(_cursor, "kembalian");
          final int _cursorIndexOfCatatan = CursorUtil.getColumnIndexOrThrow(_cursor, "catatan");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<OrderEntity> _result = new ArrayList<OrderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpStatusPo;
            if (_cursor.isNull(_cursorIndexOfStatusPo)) {
              _tmpStatusPo = null;
            } else {
              _tmpStatusPo = _cursor.getString(_cursorIndexOfStatusPo);
            }
            final String _tmpMetodeBayar;
            if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
              _tmpMetodeBayar = null;
            } else {
              _tmpMetodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
            }
            final double _tmpSubtotal;
            _tmpSubtotal = _cursor.getDouble(_cursorIndexOfSubtotal);
            final Double _tmpNominalDiterima;
            if (_cursor.isNull(_cursorIndexOfNominalDiterima)) {
              _tmpNominalDiterima = null;
            } else {
              _tmpNominalDiterima = _cursor.getDouble(_cursorIndexOfNominalDiterima);
            }
            final Double _tmpKembalian;
            if (_cursor.isNull(_cursorIndexOfKembalian)) {
              _tmpKembalian = null;
            } else {
              _tmpKembalian = _cursor.getDouble(_cursorIndexOfKembalian);
            }
            final String _tmpCatatan;
            if (_cursor.isNull(_cursorIndexOfCatatan)) {
              _tmpCatatan = null;
            } else {
              _tmpCatatan = _cursor.getString(_cursorIndexOfCatatan);
            }
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final boolean _tmpIsHidden;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsHidden);
            _tmpIsHidden = _tmp != 0;
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
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new OrderEntity(_tmpId,_tmpStatus,_tmpStatusPo,_tmpMetodeBayar,_tmpSubtotal,_tmpNominalDiterima,_tmpKembalian,_tmpCatatan,_tmpTanggal,_tmpIsHidden,_tmpIsDeleted,_tmpDeletedAt,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getById(final String orderId, final Continuation<? super OrderEntity> $completion) {
    final String _sql = "SELECT * FROM orders WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (orderId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, orderId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<OrderEntity>() {
      @Override
      @Nullable
      public OrderEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStatusPo = CursorUtil.getColumnIndexOrThrow(_cursor, "statusPo");
          final int _cursorIndexOfMetodeBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "metodeBayar");
          final int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "subtotal");
          final int _cursorIndexOfNominalDiterima = CursorUtil.getColumnIndexOrThrow(_cursor, "nominalDiterima");
          final int _cursorIndexOfKembalian = CursorUtil.getColumnIndexOrThrow(_cursor, "kembalian");
          final int _cursorIndexOfCatatan = CursorUtil.getColumnIndexOrThrow(_cursor, "catatan");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfIsHidden = CursorUtil.getColumnIndexOrThrow(_cursor, "isHidden");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final OrderEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpStatusPo;
            if (_cursor.isNull(_cursorIndexOfStatusPo)) {
              _tmpStatusPo = null;
            } else {
              _tmpStatusPo = _cursor.getString(_cursorIndexOfStatusPo);
            }
            final String _tmpMetodeBayar;
            if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
              _tmpMetodeBayar = null;
            } else {
              _tmpMetodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
            }
            final double _tmpSubtotal;
            _tmpSubtotal = _cursor.getDouble(_cursorIndexOfSubtotal);
            final Double _tmpNominalDiterima;
            if (_cursor.isNull(_cursorIndexOfNominalDiterima)) {
              _tmpNominalDiterima = null;
            } else {
              _tmpNominalDiterima = _cursor.getDouble(_cursorIndexOfNominalDiterima);
            }
            final Double _tmpKembalian;
            if (_cursor.isNull(_cursorIndexOfKembalian)) {
              _tmpKembalian = null;
            } else {
              _tmpKembalian = _cursor.getDouble(_cursorIndexOfKembalian);
            }
            final String _tmpCatatan;
            if (_cursor.isNull(_cursorIndexOfCatatan)) {
              _tmpCatatan = null;
            } else {
              _tmpCatatan = _cursor.getString(_cursorIndexOfCatatan);
            }
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final boolean _tmpIsHidden;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsHidden);
            _tmpIsHidden = _tmp != 0;
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
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new OrderEntity(_tmpId,_tmpStatus,_tmpStatusPo,_tmpMetodeBayar,_tmpSubtotal,_tmpNominalDiterima,_tmpKembalian,_tmpCatatan,_tmpTanggal,_tmpIsHidden,_tmpIsDeleted,_tmpDeletedAt,_tmpCreatedAt,_tmpUpdatedAt);
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
