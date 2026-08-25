package com.mindtoscreen.cappupos.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.mindtoscreen.cappupos.data.entities.StoreEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/mindtoscreen/cappupos/data/dao/StoreDao;", "", "getStore", "Lcom/mindtoscreen/cappupos/data/entities/StoreEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "store", "(Lcom/mindtoscreen/cappupos/data/entities/StoreEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface StoreDao {
    
    @androidx.room.Query(value = "SELECT * FROM stores LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStore(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.mindtoscreen.cappupos.data.entities.StoreEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.data.entities.StoreEntity store, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}