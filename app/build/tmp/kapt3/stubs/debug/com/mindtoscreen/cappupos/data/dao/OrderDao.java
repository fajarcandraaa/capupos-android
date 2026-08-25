package com.mindtoscreen.cappupos.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.mindtoscreen.cappupos.data.entities.OrderEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J \u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ>\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010#J0\u0010$\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u00052\u0006\u0010\"\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010&\u00a8\u0006\'"}, d2 = {"Lcom/mindtoscreen/cappupos/data/dao/OrderDao;", "", "deleteItemsForOrder", "", "orderId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllOrders", "", "Lcom/mindtoscreen/cappupos/data/entities/OrderEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBelumBayar", "getById", "hardDelete", "hide", "hidden", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "order", "(Lcom/mindtoscreen/cappupos/data/entities/OrderEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "orders", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "softDelete", "deletedAt", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePayment", "status", "metode", "nominal", "", "kembalian", "updatedAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateStatus", "statusPo", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface OrderDao {
    
    @androidx.room.Query(value = "SELECT * FROM orders WHERE status = \'belum_bayar\' AND isDeleted = 0 AND isHidden = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBelumBayar(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.mindtoscreen.cappupos.data.entities.OrderEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM orders WHERE isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllOrders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.mindtoscreen.cappupos.data.entities.OrderEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.mindtoscreen.cappupos.data.entities.OrderEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.data.entities.OrderEntity order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.mindtoscreen.cappupos.data.entities.OrderEntity> orders, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE orders SET status = :status, metodeBayar = :metode, nominalDiterima = :nominal, kembalian = :kembalian, updatedAt = :updatedAt WHERE id = :orderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePayment(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.lang.String metode, double nominal, double kembalian, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE orders SET status = :status, statusPo = :statusPo, updatedAt = :updatedAt WHERE id = :orderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String statusPo, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE orders SET isHidden = :hidden WHERE id = :orderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hide(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, boolean hidden, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE orders SET isDeleted = 1, deletedAt = :deletedAt WHERE id = :orderId AND status = \'lunas\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, long deletedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM orders WHERE id = :orderId AND status = \'belum_bayar\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hardDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM order_details WHERE orderId = :orderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItemsForOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}