package com.eltex.data.repository

import com.eltex.data.api.OkHttpClientFactory
import com.eltex.domain.repository.HeaderLocalRepository
import javax.inject.Inject

class HeaderLocalRepositoryImpl @Inject constructor() : HeaderLocalRepository {
    override suspend fun setToken(authToken: String) {
        OkHttpClientFactory.setApiToken(authToken)
    }

    override suspend fun setUserID(userID: String) {
        OkHttpClientFactory.setApiUserId(userID)
    }
}