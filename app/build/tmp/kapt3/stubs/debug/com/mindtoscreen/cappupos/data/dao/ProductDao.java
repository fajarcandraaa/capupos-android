package com.mindtoscreen.cappupos.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.mindtoscreen.cappupos.data.entities.ProductEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\u00020\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/mindtoscreen/cappupos/data/dao/ProductDao;", "", "countActiveProducts", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveProducts", "", "Lcom/mindtoscreen/cappupos/data/entities/ProductEntity;", "getAll", "hardDelete", "", "productId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "product", "(Lcom/mindtoscreen/cappupos/data/entities/ProductEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "products", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "softDelete", "timestamp", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface ProductDao {
    
    @androidx.room.Query(value = "SELECT * FROM products")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.mindtoscreen.cappupos.data.entities.ProductEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM products WHERE isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getActiveProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.mindtoscreen.cappupos.data.entities.ProductEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM products WHERE isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countActiveProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.data.entities.ProductEntity product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.mindtoscreen.cappupos.data.entities.ProductEntity> products, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE products SET isDeleted = 1, deletedAt = :timestamp WHERE id = :productId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM products WHERE id = :productId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hardDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}