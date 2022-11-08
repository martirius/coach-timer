package pini.mattia.coachtimer.data.player

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayers(playersDTO: List<PlayerDTO>)

    @Query("SELECT * FROM player")
    suspend fun getPlayers(): List<PlayerDTO>
}
