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

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\b\u0010\r\u001a\u00020\nH\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000e"}, d2 = {"Lcom/mindtoscreen/cappupos/presentation/onboarding/AddProductActivity;", "Landroidx/activity/ComponentActivity;", "()V", "viewModel", "Lcom/mindtoscreen/cappupos/presentation/onboarding/AddProductViewModel;", "getViewModel", "()Lcom/mindtoscreen/cappupos/presentation/onboarding/AddProductViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "setupUI", "app_debug"})
public final class AddProductActivity extends androidx.activity.ComponentActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    
    public AddProductActivity() {
        super(0);
    }
    
    private final com.mindtoscreen.cappupos.presentation.onboarding.AddProductViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupUI() {
    }
}