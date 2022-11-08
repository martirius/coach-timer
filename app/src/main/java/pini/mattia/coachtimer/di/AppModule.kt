package pini.mattia.coachtimer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.plus
import pini.mattia.coachtimer.data.TimeRetrieverImpl
import pini.mattia.coachtimer.data.player.PlayerRepositoryImpl
import pini.mattia.coachtimer.data.trainingsession.TrainingSessionRepositoryImpl
import pini.mattia.coachtimer.model.TimeRetriever
import pini.mattia.coachtimer.model.player.PlayersRepository
import pini.mattia.coachtimer.model.trainingsession.TrainingSessionRepository
import javax.inject.Singleton

annotation class AppCoroutineScope

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun bindPlayerRepository(playerRepositoryImpl: PlayerRepositoryImpl): PlayersRepository

    @Binds
    @Singleton
    fun bindTrainingSessionRepository(trainingSessionRepository: TrainingSessionRepositoryImpl): TrainingSessionRepository

    @Binds
    fun bindTimeRetriever(timeRetrieverImpl: TimeRetrieverImpl): TimeRetriever
}
