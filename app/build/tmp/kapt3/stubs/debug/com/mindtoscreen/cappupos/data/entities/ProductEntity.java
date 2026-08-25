package com.mindtoscreen.cappupos.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b,\b\u0087\b\u0018\u00002\u00020\u0001B\u0093\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u000b\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0014J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0010H\u00c6\u0003J\t\u0010+\u001a\u00020\u0010H\u00c6\u0003J\t\u0010,\u001a\u00020\u000bH\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\bH\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u000bH\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010\"J\u0010\u00105\u001a\u0004\u0018\u00010\rH\u00c6\u0003\u00a2\u0006\u0002\u0010\"J\u009c\u0001\u00106\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u000b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u00c6\u0001\u00a2\u0006\u0002\u00107J\u0013\u00108\u001a\u00020\u000b2\b\u00109\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010:\u001a\u00020\rH\u00d6\u0001J\t\u0010;\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010 R\u0015\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010#\u001a\u0004\b!\u0010\"R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010 R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001bR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\r\u00a2\u0006\n\n\u0002\u0010#\u001a\u0004\b\'\u0010\"R\u0011\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0016\u00a8\u0006<"}, d2 = {"Lcom/mindtoscreen/cappupos/data/entities/ProductEntity;", "", "id", "", "nama", "foto", "kategoriId", "harga", "", "deskripsi", "lacakStok", "", "jumlahStok", "", "stokMinimal", "createdAt", "", "updatedAt", "isDeleted", "deletedAt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;ZLjava/lang/Integer;Ljava/lang/Integer;JJZLjava/lang/Long;)V", "getCreatedAt", "()J", "getDeletedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getDeskripsi", "()Ljava/lang/String;", "getFoto", "getHarga", "()D", "getId", "()Z", "getJumlahStok", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getKategoriId", "getLacakStok", "getNama", "getStokMinimal", "getUpdatedAt", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;ZLjava/lang/Integer;Ljava/lang/Integer;JJZLjava/lang/Long;)Lcom/mindtoscreen/cappupos/data/entities/ProductEntity;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "products")
public final class ProductEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nama = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String foto = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String kategoriId = null;
    private final double harga = 0.0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String deskripsi = null;
    private final boolean lacakStok = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer jumlahStok = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer stokMinimal = null;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    private final boolean isDeleted = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long deletedAt = null;
    
    public ProductEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String nama, @org.jetbrains.annotations.Nullable()
    java.lang.String foto, @org.jetbrains.annotations.Nullable()
    java.lang.String kategoriId, double harga, @org.jetbrains.annotations.Nullable()
    java.lang.String deskripsi, boolean lacakStok, @org.jetbrains.annotations.Nullable()
    java.lang.Integer jumlahStok, @org.jetbrains.annotations.Nullable()
    java.lang.Integer stokMinimal, long createdAt, long updatedAt, boolean isDeleted, @org.jetbrains.annotations.Nullable()
    java.lang.Long deletedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNama() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFoto() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getKategoriId() {
        return null;
    }
    
    public final double getHarga() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDeskripsi() {
        return null;
    }
    
    public final boolean getLacakStok() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getJumlahStok() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getStokMinimal() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    public final boolean isDeleted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getDeletedAt() {
        return null;
    }
    
    public ProductEntity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final long component10() {
        return 0L;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final boolean component12() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component13() {
        return null;
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
    public final java.lang.String component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mindtoscreen.cappupos.data.entities.ProductEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String nama, @org.jetbrains.annotations.Nullable()
    java.lang.String foto, @org.jetbrains.annotations.Nullable()
    java.lang.String kategoriId, double harga, @org.jetbrains.annotations.Nullable()
    java.lang.String deskripsi, boolean lacakStok, @org.jetbrains.annotations.Nullable()
    java.lang.Integer jumlahStok, @org.jetbrains.annotations.Nullable()
    java.lang.Integer stokMinimal, long createdAt, long updatedAt, boolean isDeleted, @org.jetbrains.annotations.Nullable()
    java.lang.Long deletedAt) {
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