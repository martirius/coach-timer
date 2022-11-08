package pini.mattia.coachtimer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pini.mattia.coachtimer.data.player.PlayerDTO
import pini.mattia.coachtimer.data.player.PlayerDao
import pini.mattia.coachtimer.data.trainingsession.TrainingSessionDTO
import pini.mattia.coachtimer.data.trainingsession.TrainingSessionDao

@Database(entities = [PlayerDTO::class, TrainingSessionDTO::class], version = 1)
@TypeConverters(Converters::class)
abstract class CoachTimerDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun trainingSessionDao(): TrainingSessionDao
}
