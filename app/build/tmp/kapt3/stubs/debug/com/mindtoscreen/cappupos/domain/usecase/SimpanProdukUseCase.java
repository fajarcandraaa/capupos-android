package com.mindtoscreen.cappupos.domain.usecase;

import com.mindtoscreen.cappupos.domain.model.Product;
import com.mindtoscreen.cappupos.domain.repository.ProductRepository;
import javax.inject.Inject;

/**
 * Use case untuk menyimpan produk baru.
 * FR-01.1: Sistem harus memungkinkan pengguna menambah produk
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\f"}, d2 = {"Lcom/mindtoscreen/cappupos/domain/usecase/SimpanProdukUseCase;", "", "productRepository", "Lcom/mindtoscreen/cappupos/domain/repository/ProductRepository;", "(Lcom/mindtoscreen/cappupos/domain/repository/ProductRepository;)V", "execute", "Lkotlin/Result;", "", "product", "Lcom/mindtoscreen/cappupos/domain/model/Product;", "execute-gIAlu-s", "(Lcom/mindtoscreen/cappupos/domain/model/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SimpanProdukUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.mindtoscreen.cappupos.domain.repository.ProductRepository productRepository = null;
    
    @javax.inject.Inject()
    public SimpanProdukUseCase(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.domain.repository.ProductRepository productRepository) {
        super();
    }
}