package com.nadhifhayazee.domain.cache

interface LocalCache {
    fun setUserId(userId: String)
    fun getUserId(): String?
    fun clearCache()
}