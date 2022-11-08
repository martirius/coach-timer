package pini.mattia.coachtimer.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pini.mattia.coachtimer.data.db.CoachTimerDatabase
import pini.mattia.coachtimer.data.player.PlayerDao
import pini.mattia.coachtimer.data.trainingsession.TrainingSessionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providePlayerDao(coachTimerDatabase: CoachTimerDatabase): PlayerDao {
        return coachTimerDatabase.playerDao()
    }

    @Provides
    fun provideTrainingSessionDao(coachTimerDatabase: CoachTimerDatabase): TrainingSessionDao {
        return coachTimerDatabase.trainingSessionDao()
    }

    @Provides
    @Singleton
    fun provideCoachTimerDatabase(@ApplicationContext appContext: Context): CoachTimerDatabase {
        return Room.databaseBuilder(
            appContext,
            CoachTimerDatabase::class.java,
            "CoachTimerDatabase"
        ).build()
    }
}
