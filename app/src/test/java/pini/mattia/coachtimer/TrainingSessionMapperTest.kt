package pini.mattia.coachtimer

import org.junit.Test
import pini.mattia.coachtimer.data.player.PictureDTO
import pini.mattia.coachtimer.data.player.PlayerDTO
import pini.mattia.coachtimer.data.player.PlayerDTOMapper
import pini.mattia.coachtimer.data.player.PlayerMapper
import pini.mattia.coachtimer.data.player.PlayerName
import pini.mattia.coachtimer.data.trainingsession.TrainingSessionDTO
import pini.mattia.coachtimer.data.trainingsession.TrainingSessionDTOMapper
import pini.mattia.coachtimer.data.trainingsession.TrainingSessionMapper
import java.util.Date

class TrainingSessionMapperTest {

    @Test
    fun training_session_dto_mapper_succeed() {
        val trainingSession = fakeTrainingSession.first()
        val trainingSessionDTO = TrainingSessionDTOMapper(PlayerDTOMapper()).mapTo(trainingSession)

        assert(trainingSession.sessionDateMillis == trainingSessionDTO.sessionDateMillis)
        assert(trainingSession.totalElapsedTime == trainingSessionDTO.totalElapsedTime)
        assert(trainingSession.lapDistance == trainingSessionDTO.lapDistance)
        assert(trainingSession.laps.map { it.lapTime } == trainingSessionDTO.laps)
        // not useful to taste also player because already test in proper class
    }

    @Test
    fun training_session_mapper_succeed() {
        val trainingSessionDTO = TrainingSessionDTO(
            Date().time,
            5000L,
            5,
            PlayerDTO(
                PlayerName("Mr", "Fake", "Runner"),
                PictureDTO("fake", "fake", "fake")
            ),
            listOf(5000L)
        )
        val trainingSession = TrainingSessionMapper(PlayerMapper()).mapTo(trainingSessionDTO)

        assert(trainingSession.sessionDateMillis == trainingSessionDTO.sessionDateMillis)
        assert(trainingSession.totalElapsedTime == trainingSessionDTO.totalElapsedTime)
        assert(trainingSession.lapDistance == trainingSessionDTO.lapDistance)
        assert(trainingSession.laps.map { it.lapTime } == trainingSessionDTO.laps)
        // not useful to taste also player because already test in proper class
    }
}
