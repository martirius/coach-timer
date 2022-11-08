package pini.mattia.coachtimer.data.trainingsession

import pini.mattia.coachtimer.data.player.PlayerDTOMapper
import pini.mattia.coachtimer.data.player.PlayerMapper
import pini.mattia.coachtimer.model.Mapper
import pini.mattia.coachtimer.model.trainingsession.Lap
import pini.mattia.coachtimer.model.trainingsession.TrainingSession
import javax.inject.Inject

/**
 * Map a Training session from DTO do domain model
 */
class TrainingSessionMapper @Inject constructor(
    private val playerMapper: PlayerMapper
) : Mapper<TrainingSession, TrainingSessionDTO> {
    override fun mapTo(objectToMap: TrainingSessionDTO): TrainingSession {
        return TrainingSession(
            objectToMap.sessionDateMillis,
            objectToMap.totalElapsedTime,
            objectToMap.lapDistance,
            playerMapper.mapTo(objectToMap.player),
            objectToMap.laps.map { Lap(it) }
        )
    }
}

class TrainingSessionDTOMapper @Inject constructor(
    private val playerMapper: PlayerDTOMapper
) : Mapper<TrainingSessionDTO, TrainingSession> {
    override fun mapTo(objectToMap: TrainingSession): TrainingSessionDTO {
        return TrainingSessionDTO(
            objectToMap.sessionDateMillis,
            objectToMap.totalElapsedTime,
            objectToMap.lapDistance,
            playerMapper.mapTo(objectToMap.player),
            objectToMap.laps.map { it.lapTime }
        )
    }
}
