package pini.mattia.coachtimer.data.trainingsession

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import pini.mattia.coachtimer.data.player.PlayerDTO

@Entity(tableName = "training_session")
data class TrainingSessionDTO(
    @PrimaryKey
    val sessionDateMillis: Long,
    val totalElapsedTime: Long,
    val lapDistance: Int,
    @Embedded
    val player: PlayerDTO,
    val laps: List<Long>,
    val isUploaded: Boolean = false
)
