package pini.mattia.coachtimer.data.trainingsession

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pini.mattia.coachtimer.di.AppCoroutineScope
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import pini.mattia.coachtimer.model.trainingsession.TrainingSessionRepository
import javax.inject.Inject

class TrainingSessionRepositoryImpl @Inject constructor(
    private val trainingSessionDao: TrainingSessionDao,
    private val trainingSessionMapper: TrainingSessionMapper,
    private val trainingSessionDTOMapper: TrainingSessionDTOMapper,
    @AppCoroutineScope
    private val coroutineScope: CoroutineScope
) : TrainingSessionRepository {

    override fun getTrainingSessions(): Flow<List<TrainingSession>> {
        return trainingSessionDao.getTrainingSessions()
            .map {
                it.map { trainingSessionDTO -> trainingSessionMapper.mapTo(trainingSessionDTO) }
            }
    }

    override fun addTrainingSession(trainingSession: TrainingSession) {
        coroutineScope.launch(Dispatchers.IO) {
            trainingSessionDao.addTrainingSession(trainingSessionDTOMapper.mapTo(trainingSession))
        }
    }
}
