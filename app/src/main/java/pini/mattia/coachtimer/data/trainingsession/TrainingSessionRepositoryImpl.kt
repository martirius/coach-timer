package pini.mattia.coachtimer.data.trainingsession

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import pini.mattia.coachtimer.model.trainingsession.TrainingSessionRepository
import javax.inject.Inject

class TrainingSessionRepositoryImpl @Inject constructor() : TrainingSessionRepository {

    private val _trainingSessions = MutableStateFlow<List<TrainingSession>>(emptyList())

    override fun getTrainingSessions(): Flow<List<TrainingSession>> {
        return _trainingSessions
    }

    override fun addTrainingSession(trainingSession: TrainingSession) {
        _trainingSessions.tryEmit(_trainingSessions.value.toMutableList().apply { add(trainingSession) })
    }
}
