package services

import auth.getToken
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import multiplatform.ktor.KtorApiClient
import watchlist.api.v1.Watchlist
import watchlist.api.v1.getMyList
import watchlist.api.v1.saveMyList

class HttpDataRepository(httpClient: HttpClient) : DataRepository {
    private val apiClient = KtorApiClient(httpClient)

    override suspend fun save(watchlist: Watchlist) {
        if (getToken() == null) return
        try {
            apiClient.callApi(saveMyList, Unit, watchlist)
        } catch (e: ClientRequestException) {
            console.error(e)
        }
    }

    override suspend fun load(): Watchlist? {
        if (getToken() == null) return null
        return try {
            apiClient.callApi(getMyList, Unit)
        } catch (e: ClientRequestException) {
            console.error(e)
            null
        }
    }
}
