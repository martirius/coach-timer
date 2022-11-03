package pini.mattia.coachtimer.data.player

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class PlayerService @Inject constructor(private val httpClient: HttpClient) {
    suspend fun getPlayers(numberOfPlayers: Int = 10): Result<PlayersResponse> {
        return kotlin.runCatching {
            val apiResponse = httpClient.get(
                "https://randomuser.me/api/?seed=empatica&inc=name,picture&gender=male&results=$numberOfPlayers&noinfo"
            )
            apiResponse.body()
        }
    }
}
