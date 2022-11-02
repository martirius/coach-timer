package pini.mattia.coachtimer.model.trainingsession

import kotlinx.coroutines.flow.Flow

interface TrainingSessionRepository {
    fun getTrainingSession(): Flow<TrainingSession>

    suspend fun addTrainingSession(trainingSession: TrainingSession)

    fun getOngoingSession(): Flow<TrainingSession>

    suspend fun updateOngoingSession(onGoingSession: TrainingSession)
}
