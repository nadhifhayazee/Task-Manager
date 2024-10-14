package com.nadhifhayazee.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.nadhifhayazee.data.cache.SharedPrefManager
import com.nadhifhayazee.data.repository.StorageRepositoryImpl
import com.nadhifhayazee.data.repository.TaskRepositoryImpl
import com.nadhifhayazee.data.repository.UserRepositoryImpl
import com.nadhifhayazee.domain.cache.LocalCache
import com.nadhifhayazee.domain.repository.StorageRepository
import com.nadhifhayazee.domain.repository.TaskRepository
import com.nadhifhayazee.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFireStore() : FirebaseFirestore {
        return Firebase.firestore
    }


    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @Singleton
    fun provideSharedPrefManager(
        @ApplicationContext context: Context
    ): LocalCache {
        return SharedPrefManager(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseDb: FirebaseFirestore): UserRepository {
        return UserRepositoryImpl(firebaseDb)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(firebaseDb: FirebaseFirestore): TaskRepository {
        return TaskRepositoryImpl(firebaseDb)
    }

    @Provides
    @Singleton
    fun provideStorageRepository(storage: FirebaseStorage): StorageRepository {
        return StorageRepositoryImpl(storage)
    }
}