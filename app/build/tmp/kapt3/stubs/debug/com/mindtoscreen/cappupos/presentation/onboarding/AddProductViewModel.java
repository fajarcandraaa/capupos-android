package com.mindtoscreen.cappupos.presentation.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModel;
import com.mindtoscreen.cappupos.R;
import com.mindtoscreen.cappupos.domain.model.Product;
import com.mindtoscreen.cappupos.domain.usecase.SimpanProdukUseCase;
import com.mindtoscreen.cappupos.presentation.HomeActivity;
import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\f"}, d2 = {"Lcom/mindtoscreen/cappupos/presentation/onboarding/AddProductViewModel;", "Landroidx/lifecycle/ViewModel;", "simpanProdukUseCase", "Lcom/mindtoscreen/cappupos/domain/usecase/SimpanProdukUseCase;", "(Lcom/mindtoscreen/cappupos/domain/usecase/SimpanProdukUseCase;)V", "simpanProduct", "Lkotlin/Result;", "", "product", "Lcom/mindtoscreen/cappupos/domain/model/Product;", "simpanProduct-gIAlu-s", "(Lcom/mindtoscreen/cappupos/domain/model/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddProductViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.mindtoscreen.cappupos.domain.usecase.SimpanProdukUseCase simpanProdukUseCase = null;
    
    @javax.inject.Inject()
    public AddProductViewModel(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.domain.usecase.SimpanProdukUseCase simpanProdukUseCase) {
        super();
    }
}