package com.mindtoscreen.cappupos.domain.repository

import com.mindtoscreen.cappupos.domain.model.Store

interface StoreRepository {
    suspend fun getStore(): Store?
    suspend fun saveStore(store: Store): Result<Unit>
}
