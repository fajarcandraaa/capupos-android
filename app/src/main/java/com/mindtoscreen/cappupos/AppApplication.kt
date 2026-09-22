package com.mindtoscreen.cappupos

import android.app.Application
import android.content.SharedPreferences
import com.mindtoscreen.cappupos.domain.usecase.CekReminderBackupUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppApplication : Application() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        // Seed lastReminderAt ke current time saat first launch
        val lastReminderAt = sharedPreferences.getLong(CekReminderBackupUseCase.KEY_LAST_REMINDER, 0L)
        if (lastReminderAt == 0L) {
            sharedPreferences.edit()
                .putLong(CekReminderBackupUseCase.KEY_LAST_REMINDER, System.currentTimeMillis())
                .apply()
        }
    }
}