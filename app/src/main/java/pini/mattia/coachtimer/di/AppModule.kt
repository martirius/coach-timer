package pini.mattia.coachtimer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pini.mattia.coachtimer.data.player.PlayerRepositoryImpl
import pini.mattia.coachtimer.model.player.PlayersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun bindPlayerRepository(playerRepositoryImpl: PlayerRepositoryImpl): PlayersRepository
}
