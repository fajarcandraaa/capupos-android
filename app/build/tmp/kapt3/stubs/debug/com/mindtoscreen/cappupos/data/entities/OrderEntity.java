package com.mindtoscreen.cappupos.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b,\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u0012\u001a\u00020\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\r\u00a2\u0006\u0002\u0010\u0014J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u000fH\u00c6\u0003J\t\u0010+\u001a\u00020\u000fH\u00c6\u0003J\u0010\u0010,\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\t\u0010-\u001a\u00020\rH\u00c6\u0003J\t\u0010.\u001a\u00020\rH\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\bH\u00c6\u0003J\u0010\u00103\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u0010\u00104\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001fJ\u000b\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\rH\u00c6\u0003J\u00a6\u0001\u00107\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\rH\u00c6\u0001\u00a2\u0006\u0002\u00108J\u0013\u00109\u001a\u00020\u000f2\b\u0010:\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010;\u001a\u00020<H\u00d6\u0001J\t\u0010=\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0012\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0011\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001dR\u0015\u0010\n\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u0015\u0010\t\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010 \u001a\u0004\b\"\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0016R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0018R\u0011\u0010\u0013\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0018\u00a8\u0006>"}, d2 = {"Lcom/mindtoscreen/cappupos/data/entities/OrderEntity;", "", "id", "", "status", "statusPo", "metodeBayar", "subtotal", "", "nominalDiterima", "kembalian", "catatan", "tanggal", "", "isHidden", "", "isDeleted", "deletedAt", "createdAt", "updatedAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/Double;Ljava/lang/Double;Ljava/lang/String;JZZLjava/lang/Long;JJ)V", "getCatatan", "()Ljava/lang/String;", "getCreatedAt", "()J", "getDeletedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getId", "()Z", "getKembalian", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getMetodeBayar", "getNominalDiterima", "getStatus", "getStatusPo", "getSubtotal", "()D", "getTanggal", "getUpdatedAt", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/Double;Ljava/lang/Double;Ljava/lang/String;JZZLjava/lang/Long;JJ)Lcom/mindtoscreen/cappupos/data/entities/OrderEntity;", "equals", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "orders")
public final class OrderEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String statusPo = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String metodeBayar = null;
    private final double subtotal = 0.0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double nominalDiterima = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double kembalian = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String catatan = null;
    private final long tanggal = 0L;
    private final boolean isHidden = false;
    private final boolean isDeleted = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long deletedAt = null;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    
    public OrderEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String statusPo, @org.jetbrains.annotations.Nullable()
    java.lang.String metodeBayar, double subtotal, @org.jetbrains.annotations.Nullable()
    java.lang.Double nominalDiterima, @org.jetbrains.annotations.Nullable()
    java.lang.Double kembalian, @org.jetbrains.annotations.Nullable()
    java.lang.String catatan, long tanggal, boolean isHidden, boolean isDeleted, @org.jetbrains.annotations.Nullable()
    java.lang.Long deletedAt, long createdAt, long updatedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStatusPo() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMetodeBayar() {
        return null;
    }
    
    public final double getSubtotal() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getNominalDiterima() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getKembalian() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCatatan() {
        return null;
    }
    
    public final long getTanggal() {
        return 0L;
    }
    
    public final boolean isHidden() {
        return false;
    }
    
    public final boolean isDeleted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getDeletedAt() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    public OrderEntity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mindtoscreen.cappupos.data.entities.OrderEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String statusPo, @org.jetbrains.annotations.Nullable()
    java.lang.String metodeBayar, double subtotal, @org.jetbrains.annotations.Nullable()
    java.lang.Double nominalDiterima, @org.jetbrains.annotations.Nullable()
    java.lang.Double kembalian, @org.jetbrains.annotations.Nullable()
    java.lang.String catatan, long tanggal, boolean isHidden, boolean isDeleted, @org.jetbrains.annotations.Nullable()
    java.lang.Long deletedAt, long createdAt, long updatedAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}