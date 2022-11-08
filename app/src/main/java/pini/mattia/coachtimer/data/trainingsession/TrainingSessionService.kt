package pini.mattia.coachtimer.data.trainingsession

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class TrainingSessionService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun uploadSession(trainingSessionDTO: TrainingSessionDTO): Result<SessionUploadStatus> {
        return kotlin.runCatching {
            val apiResponse = httpClient.post(
                "http://empatica-homework.free.beeceptor.com/trainings"
            ) {
                contentType(ContentType.Application.Json)
                setBody(trainingSessionDTO)
            }
            apiResponse.body()
        }
    }
}

@kotlinx.serialization.Serializable
data class SessionUploadStatus(val status: String) {
    fun isSuccessful(): Boolean = status == "training-uploaded"
}
