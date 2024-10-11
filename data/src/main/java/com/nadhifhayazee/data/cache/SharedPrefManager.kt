package com.nadhifhayazee.data.cache

import android.content.Context
import android.content.SharedPreferences
import com.nadhifhayazee.domain.cache.LocalCache
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor(
    @ApplicationContext context: Context
) : LocalCache {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "user_prefs"
        private const val USER_ID = "user_id"
    }

    override fun setUserId(userId: String) {
        sharedPreferences.edit().putString(USER_ID, userId).apply()

    }

    override fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID, null)
    }

    override fun clearCache() {
        sharedPreferences.edit().clear().apply()
    }
}