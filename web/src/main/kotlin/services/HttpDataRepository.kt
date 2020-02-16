package services

import auth.getToken
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import multiplatform.ktor.callApi
import watchlist.api.v1.Watchlist
import watchlist.api.v1.getMyList
import watchlist.api.v1.saveMyList

class HttpDataRepository(private val httpClient: HttpClient) : DataRepository {
    override suspend fun save(watchlist: Watchlist) {
        if (getToken() == null) return
        try {
            httpClient.callApi(saveMyList, Unit, watchlist)
        } catch (e: ClientRequestException) {
            console.error(e)
        }
    }

    override suspend fun load(): Watchlist? {
        if (getToken() == null) return null
        return try {
            httpClient.callApi(getMyList, Unit)
        } catch (e: ClientRequestException) {
            console.error(e)
            null
        }
    }
}