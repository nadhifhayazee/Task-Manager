package com.nadhifhayazee.domain.usecase

import com.google.firebase.firestore.FirebaseFirestore
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
    operator fun invoke(status: String?): Flow<ResultState<List<ResponseTask>>> {
        return callbackFlow {
            try {
                if (localCache.getUserId() == null) {
                    send(ResultState.Error(Exception("No user ID found")))
                } else {
                    var query: Query
                    var reference =
                        firebaseDb.collection("users").document(localCache.getUserId()!!)
                            .collection("tasks")

                    if (status != null) {
                        query =
                            reference.whereNotEqualTo("subject", "").whereEqualTo("status", status)
                    } else {
                        query = reference.whereNotEqualTo("subject", "")
                    }
                    val listener = query.addSnapshotListener { snapshot, error ->
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

                    awaitClose { listener.remove() }
                }
            } catch (e: Exception) {
                trySend(ResultState.Error(e))
            }

        }
    }
}