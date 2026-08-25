package com.mindtoscreen.cappupos.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.mindtoscreen.cappupos.data.dao.*;
import com.mindtoscreen.cappupos.data.entities.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/mindtoscreen/cappupos/data/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "orderDao", "Lcom/mindtoscreen/cappupos/data/dao/OrderDao;", "orderDetailDao", "Lcom/mindtoscreen/cappupos/data/dao/OrderDetailDao;", "productDao", "Lcom/mindtoscreen/cappupos/data/dao/ProductDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.mindtoscreen.cappupos.data.entities.ProductEntity.class, com.mindtoscreen.cappupos.data.entities.CategoryEntity.class, com.mindtoscreen.cappupos.data.entities.OrderEntity.class, com.mindtoscreen.cappupos.data.entities.OrderDetailEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    private static final com.mindtoscreen.cappupos.data.MigrationV1ToV2 MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.mindtoscreen.cappupos.data.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.mindtoscreen.cappupos.data.dao.ProductDao productDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.mindtoscreen.cappupos.data.dao.OrderDao orderDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.mindtoscreen.cappupos.data.dao.OrderDetailDao orderDetailDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/mindtoscreen/cappupos/data/AppDatabase$Companion;", "", "()V", "MIGRATION_1_2", "Lcom/mindtoscreen/cappupos/data/MigrationV1ToV2;", "getMIGRATION_1_2", "()Lcom/mindtoscreen/cappupos/data/MigrationV1ToV2;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mindtoscreen.cappupos.data.MigrationV1ToV2 getMIGRATION_1_2() {
            return null;
        }
    }
}