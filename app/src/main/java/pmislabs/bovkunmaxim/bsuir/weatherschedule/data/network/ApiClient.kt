package pmislabs.bovkunmaxim.bsuir.weatherschedule.data.network

import com.ramcosta.composedestinations.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object ApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    var client = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        install(ContentNegotiation) {
            Json {
                explicitNulls = false
                ignoreUnknownKeys = true
                encodeDefaults = true
                prettyPrint = BuildConfig.DEBUG
                coerceInputValues = true
                useArrayPolymorphism = false
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Any)
            accept(ContentType.Application.Json)
        }

    }
}