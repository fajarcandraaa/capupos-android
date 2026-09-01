package com.mindtoscreen.cappupos.presentation.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AlertDialog;
import com.mindtoscreen.cappupos.R;
import com.mindtoscreen.cappupos.domain.usecase.CekProdukKosongUseCase;
import com.mindtoscreen.cappupos.presentation.HomeActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\b\u0010\r\u001a\u00020\nH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000e"}, d2 = {"Lcom/mindtoscreen/cappupos/presentation/onboarding/OnboardingActivity;", "Landroidx/activity/ComponentActivity;", "()V", "cekProdukKosongUseCase", "Lcom/mindtoscreen/cappupos/domain/usecase/CekProdukKosongUseCase;", "getCekProdukKosongUseCase", "()Lcom/mindtoscreen/cappupos/domain/usecase/CekProdukKosongUseCase;", "setCekProdukKosongUseCase", "(Lcom/mindtoscreen/cappupos/domain/usecase/CekProdukKosongUseCase;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "showEmptyStateDialog", "app_debug"})
public final class OnboardingActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public com.mindtoscreen.cappupos.domain.usecase.CekProdukKosongUseCase cekProdukKosongUseCase;
    
    public OnboardingActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.mindtoscreen.cappupos.domain.usecase.CekProdukKosongUseCase getCekProdukKosongUseCase() {
        return null;
    }
    
    public final void setCekProdukKosongUseCase(@org.jetbrains.annotations.NotNull()
    com.mindtoscreen.cappupos.domain.usecase.CekProdukKosongUseCase p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void showEmptyStateDialog() {
    }
}