package pini.mattia.coachtimer.data.trainingsession

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingSessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrainingSession(trainingSessionDTO: TrainingSessionDTO)

    @Query("SELECT * FROM training_session")
    fun getTrainingSessions(): Flow<List<TrainingSessionDTO>>

    @Query("SELECT * FROM training_session WHERE NOT(isUploaded)")
    suspend fun getSessionNotUploaded(): List<TrainingSessionDTO>
}
