package com.eltex.data.repository

import com.eltex.data.api.OkHttpClientFactory
import com.eltex.domain.repository.HeaderRepository
import javax.inject.Inject

class HeaderRepositoryImpl @Inject constructor() : HeaderRepository {
    override suspend fun setToken(authToken: String) {
        OkHttpClientFactory.setApiToken(authToken)
    }

    override suspend fun setUserID(userID: String) {
        OkHttpClientFactory.setApiUserId(userID)
    }
}