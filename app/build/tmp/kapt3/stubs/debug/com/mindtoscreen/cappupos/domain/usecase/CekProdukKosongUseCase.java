package com.mindtoscreen.cappupos.domain.usecase;

import javax.inject.Inject;

/**
 * Use case untuk mengecek apakah ada produk di dalam database.
 * FR-14.1: Sistem harus mendeteksi kondisi belum ada produk sama sekali
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/mindtoscreen/cappupos/domain/usecase/CekProdukKosongUseCase;", "", "productRepo", "Lcom/mindtoscreen/cappupos/domain/repository/ProductRepository;", "(Lcom/mindtoscreen/cappupos/domain/repository/ProductRepository;)V", "execute", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CekProdukKosongUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.mindtoscreen.cappupos.domain.repository.ProductRepository productRepo = null;
    
    @javax.inject.Inject()
    public CekProdukKosongUseCase(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.domain.repository.ProductRepository productRepo) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object execute(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}