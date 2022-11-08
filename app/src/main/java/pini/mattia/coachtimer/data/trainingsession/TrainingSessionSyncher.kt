package pini.mattia.coachtimer.data.trainingsession

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Worker that upload a session and set its status to uploaded
 */
@HiltWorker
class TrainingSessionSyncher @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val trainingSessionDao: TrainingSessionDao,
    private val trainingSessionService: TrainingSessionService
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val sessionId = inputData.getLong(SESSION_ID_PARAM, 0L)
        val trainingSessionDTO = trainingSessionDao.getSessionNotUploaded().find {
            it.sessionDateMillis == sessionId
        }
        return if (trainingSessionDTO != null) {
            // upload session to backend
            val uploadStatus = trainingSessionService.uploadSession(trainingSessionDTO).getOrThrow()
            if (uploadStatus.isSuccessful()) {
                // update session on local storage as uploaded
                trainingSessionDao.updateTrainingSession(trainingSessionDTO.copy(isUploaded = true))
                Result.success()
            } else {
                Log.e(
                    TrainingSessionSyncher::class.java.name,
                    "Failed to upload session, upload status: ${uploadStatus.status}"
                )
                Result.failure()
            }
        } else {
            Log.e(TrainingSessionSyncher::class.java.name, "Failed to upload session")
            Result.failure()
        }
    }

    companion object {
        const val SESSION_ID_PARAM = "sessionId"
    }
}
