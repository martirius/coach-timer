package pini.mattia.coachtimer.model.trainingsession

import kotlinx.coroutines.flow.Flow

interface TrainingSessionRepository {
    fun getTrainingSessions(): Flow<List<TrainingSession>>

    suspend fun addTrainingSession(trainingSession: TrainingSession)
}
