package com.mindtoscreen.cappupos.presentation;

import androidx.lifecycle.ViewModel;
import com.mindtoscreen.cappupos.domain.model.Product;
import com.mindtoscreen.cappupos.domain.repository.ProductRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0086@\u00a2\u0006\u0002\u0010\rR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2 = {"Lcom/mindtoscreen/cappupos/presentation/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "productRepository", "Lcom/mindtoscreen/cappupos/domain/repository/ProductRepository;", "(Lcom/mindtoscreen/cappupos/domain/repository/ProductRepository;)V", "_products", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/mindtoscreen/cappupos/domain/model/Product;", "products", "Lkotlinx/coroutines/flow/StateFlow;", "getProducts", "()Lkotlinx/coroutines/flow/StateFlow;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mindtoscreen.cappupos.domain.repository.ProductRepository productRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.mindtoscreen.cappupos.domain.model.Product>> _products = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.mindtoscreen.cappupos.domain.model.Product>> products = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.domain.repository.ProductRepository productRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.mindtoscreen.cappupos.domain.model.Product>> getProducts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getProducts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.mindtoscreen.cappupos.domain.model.Product>> $completion) {
        return null;
    }
}