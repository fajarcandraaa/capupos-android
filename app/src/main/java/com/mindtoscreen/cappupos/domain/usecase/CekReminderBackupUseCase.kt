package com.mindtoscreen.cappupos.domain.usecase

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Use case untuk cek reminder backup mingguan. FR-12.
 * Interval 7 hari dari `lastReminderAt` (dismiss terakhir / "Nanti Saja" /
 * export sukses), bukan dari install date. Storage SharedPreferences — bukan
 * entity DB (DECISIONS.md [2026-09-14] poin 5).
 */
class CekReminderBackupUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val KEY_LAST_REMINDER = "last_backup_timestamp"
        const val INTERVAL_MILLIS = 7L * 24 * 60 * 60 * 1000 // 7 hari
    }

    /** True bila sudah lewat 7 hari sejak reminder terakhir (atau belum pernah). */
    fun execute(): Boolean {
        val lastReminderAt = sharedPreferences.getLong(KEY_LAST_REMINDER, 0L)
        if (lastReminderAt == 0L) return true
        return System.currentTimeMillis() - lastReminderAt >= INTERVAL_MILLIS
    }

    /** "Nanti Saja" / export sukses: reset counter ke now. */
    fun markReminderShown() {
        sharedPreferences.edit()
            .putLong(KEY_LAST_REMINDER, System.currentTimeMillis())
            .apply()
    }
}
