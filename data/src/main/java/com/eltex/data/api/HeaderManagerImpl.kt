package com.eltex.data.api

import com.eltex.domain.HeaderManager
import javax.inject.Inject

class HeaderManagerImpl @Inject constructor() : HeaderManager {
    override suspend fun setToken(authToken: String) {
        OkHttpClientFactory.setApiToken(authToken)
    }

    override suspend fun setUserID(userID: String) {
        OkHttpClientFactory.setApiUserId(userID)
    }
}