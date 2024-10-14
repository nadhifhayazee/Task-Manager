package com.nadhifhayazee.domain.usecase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.nadhifhayazee.domain.cache.LocalCache
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.domain.dto.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetUserTasksRealtimeUseCase @Inject constructor(
    private val localCache: LocalCache,
    private val firebaseDb: FirebaseFirestore
) {
    operator fun invoke(
        status: String? = null,
        startTime: Timestamp? = null,
        endTime: Timestamp? = null,
        orderBy: String? = null,
        sortType: String? = "ASC",
        limit: Long? = null
    ): Flow<ResultState<List<ResponseTask>>> {
        return callbackFlow {
            var listenerRegister: ListenerRegistration? = null
            try {
                if (localCache.getUserId() == null) {
                    send(ResultState.Error(Exception("No user ID found")))
                } else {
                    val reference =
                        firebaseDb.collection("users").document(localCache.getUserId()!!)
                            .collection("tasks")
                    var query = reference.whereNotEqualTo("subject", "")


                    if (status != null) {
                        query = query.whereEqualTo("status", status)
                    }
                    if (startTime != null && endTime != null) {
                        query = query.whereGreaterThanOrEqualTo("dateTime", startTime)
                            .whereLessThan("dateTime", endTime)
                    }
                    if (orderBy != null) {
                        query = query.orderBy(
                            orderBy,
                            if (sortType == "ASC") Query.Direction.ASCENDING else Query.Direction.DESCENDING
                        )
                    }
                    if (limit != null) {
                        query = query.limit(limit)
                    }

                    listenerRegister = query.addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            close(error)  // Handle errors
                            return@addSnapshotListener
                        }

                        if (snapshot != null && snapshot.documents.isNotEmpty()) {
                            // Retrieve the array (tags) from the document
                            val tasks = snapshot.documents.map { ResponseTask.convertFromMap(it) }
                            trySend(ResultState.Success(tasks))  // Send the array to the Flow
                        } else {
                            trySend(ResultState.Success(emptyList()))
                        }
                    }

                }
            } catch (e: Exception) {
                trySend(ResultState.Error(e))
            }

            awaitClose { listenerRegister?.remove() }
        }
    }
}