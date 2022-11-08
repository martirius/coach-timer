package pini.mattia.coachtimer.model.trainingsession

import kotlinx.coroutines.flow.Flow

interface TrainingSessionRepository {
    fun getTrainingSessions(): Flow<List<TrainingSession>>

    fun addTrainingSession(trainingSession: TrainingSession)
}
