package com.eltex.data.api

import com.eltex.domain.HeaderManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderManagerImpl @Inject constructor() : HeaderManager {

    var token: String? = null
        private set
    var id: String? = null
        private set

    override suspend fun setToken(authToken: String) {
        this.token = authToken
        OkHttpClientFactory.setApiToken(authToken)
    }

    override suspend fun setUserID(userID: String) {
        this.id = userID
        OkHttpClientFactory.setApiUserId(userID)
    }
}