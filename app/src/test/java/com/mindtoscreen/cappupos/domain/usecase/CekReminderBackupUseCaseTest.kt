package com.mindtoscreen.cappupos.domain.usecase

import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*

/**
 * Unit test untuk CekReminderBackupUseCase.
 * Validasi AC 2 dan AC 3 dari TASK-009.
 */
class CekReminderBackupUseCaseTest {
    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var useCase: CekReminderBackupUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = CekReminderBackupUseCase(sharedPreferences)
    }

    /**
     * AC 2: execute() return false saat lastReminderAt == 0L (first launch)
     */
    @Test
    fun execute_returnsFalse_whenLastReminderIsZero() {
        // Arrange: lastReminderAt = 0L (first launch, belum di-seed)
        `when`(sharedPreferences.getLong(CekReminderBackupUseCase.KEY_LAST_REMINDER, 0L))
            .thenReturn(0L)

        // Act
        val result = useCase.execute()

        // Assert
        assertFalse("execute() must return false saat lastReminderAt == 0L", result)
    }

    /**
     * AC 3: execute() return false bila interval < 7 hari
     */
    @Test
    fun execute_returnsFalse_whenIntervalLessThanSevenDays() {
        // Arrange: lastReminderAt = 6 hari lalu
        val sixDaysAgo = System.currentTimeMillis() - (6L * 24 * 60 * 60 * 1000)
        `when`(sharedPreferences.getLong(CekReminderBackupUseCase.KEY_LAST_REMINDER, 0L))
            .thenReturn(sixDaysAgo)

        // Act
        val result = useCase.execute()

        // Assert
        assertFalse("execute() must return false jika interval < 7 hari", result)
    }

    /**
     * AC 3: execute() return true bila interval >= 7 hari
     */
    @Test
    fun execute_returnsTrue_whenIntervalGreaterThanOrEqualSevenDays() {
        // Arrange: lastReminderAt = 8 hari lalu
        val eightDaysAgo = System.currentTimeMillis() - (8L * 24 * 60 * 60 * 1000)
        `when`(sharedPreferences.getLong(CekReminderBackupUseCase.KEY_LAST_REMINDER, 0L))
            .thenReturn(eightDaysAgo)

        // Act
        val result = useCase.execute()

        // Assert
        assertTrue("execute() must return true jika interval >= 7 hari", result)
    }

    /**
     * AC 3: execute() return true tepat saat interval = 7 hari
     */
    @Test
    fun execute_returnsTrue_whenIntervalExactlySevenDays() {
        // Arrange: lastReminderAt = exactly 7 hari lalu
        val sevenDaysAgo = System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000)
        `when`(sharedPreferences.getLong(CekReminderBackupUseCase.KEY_LAST_REMINDER, 0L))
            .thenReturn(sevenDaysAgo)

        // Act
        val result = useCase.execute()

        // Assert
        assertTrue("execute() must return true saat interval == 7 hari (boundary)", result)
    }

    /**
     * markReminderShown() set lastReminderAt ke current time
     */
    @Test
    fun markReminderShown_savesCurrentTime() {
        // Arrange
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putLong(anyString(), anyLong())).thenReturn(editor)

        // Act
        useCase.markReminderShown()

        // Assert
        verify(editor).putLong(
            eq(CekReminderBackupUseCase.KEY_LAST_REMINDER),
            anyLong()
        )
        verify(editor).apply()
    }
}
