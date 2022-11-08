package pini.mattia.coachtimer.data.trainingsession

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val coroutineScope: CoroutineScope,
    @ApplicationContext
    private val context: Context
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

            // prepare data to add to the Worker request
            val wordData = workDataOf(TrainingSessionSyncher.SESSION_ID_PARAM to trainingSession.sessionDateMillis)

            // create request to upload the session
            val request = OneTimeWorkRequestBuilder<TrainingSessionSyncher>().setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).setInputData(wordData).build()

            // enqueue the request
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}
