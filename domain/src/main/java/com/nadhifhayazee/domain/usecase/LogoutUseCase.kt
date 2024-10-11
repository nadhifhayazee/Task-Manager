package com.nadhifhayazee.domain.usecase

import com.nadhifhayazee.domain.cache.LocalCache
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val localCache: LocalCache
) {
    operator fun invoke(){
        localCache.clearCache()
    }
}