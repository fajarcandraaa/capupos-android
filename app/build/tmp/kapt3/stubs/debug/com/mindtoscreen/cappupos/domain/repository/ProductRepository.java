package com.mindtoscreen.cappupos.domain.repository;

import com.mindtoscreen.cappupos.domain.model.Product;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00a6@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\fH\u00a6@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/mindtoscreen/cappupos/domain/repository/ProductRepository;", "", "deleteProduct", "", "productId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProductCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProducts", "", "Lcom/mindtoscreen/cappupos/domain/model/Product;", "insertProduct", "product", "(Lcom/mindtoscreen/cappupos/domain/model/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ProductRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.mindtoscreen.cappupos.domain.model.Product>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProductCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertProduct(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.domain.model.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteProduct(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}