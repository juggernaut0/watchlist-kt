package watchlist.handlers

import auth.ValidatedToken
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.routing.Route
import multiplatform.ktor.WebApplicationException
import multiplatform.ktor.handleApi
import kotlinx.coroutines.future.await
import kotlinx.serialization.json.Json
import org.jooq.JSONB
import watchlist.api.v1.Watchlist
import watchlist.api.v1.getListByName
import watchlist.api.v1.getMyList
import watchlist.api.v1.saveMyList
import watchlist.db.Database
import watchlist.db.jooq.Tables.WATCHLIST
import java.util.*
import javax.inject.Inject

fun Route.registerRoutes(handler: ApiHandler) {
    authenticate {
        handleApi(getMyList) { handler.getMyList(auth as ValidatedToken) }
        handleApi(saveMyList) { handler.saveMyList(auth as ValidatedToken, it) }
    }
    handleApi(getListByName) { throw WebApplicationException(HttpStatusCode.NotImplemented) }
}

class ApiHandler @Inject constructor(
    private val db: Database,
    private val json: Json
) {
    suspend fun getMyList(auth: ValidatedToken): Watchlist? {
        val userId = auth.userId
        return db.transaction { dsl ->
            dsl.selectFrom(WATCHLIST)
                .where(WATCHLIST.USER_ID.eq(userId))
                .fetchAsync()
                .await()
                .firstOrNull()
                ?.let {
                    readJson(it.version, it.contents)
                }
        }
    }

    suspend fun saveMyList(auth: ValidatedToken, watchlist: Watchlist) {
        val userId = auth.userId
        val (version, jsonb) = writeJson(watchlist)
        db.transaction { dsl ->
            val existing = dsl.select(WATCHLIST.ID)
                .from(WATCHLIST)
                .where(WATCHLIST.USER_ID.eq(userId))
                .forUpdate()
                .fetchAsync()
                .await()
                .firstOrNull()
                ?.value1()

            if (existing == null) {
                dsl.insertInto(WATCHLIST)
                    .set(WATCHLIST.ID, UUID.randomUUID())
                    .set(WATCHLIST.USER_ID, userId)
                    .set(WATCHLIST.VERSION, version)
                    .set(WATCHLIST.CONTENTS, jsonb)
                    .executeAsync()
                    .await()
            } else {
                dsl.update(WATCHLIST)
                    .set(WATCHLIST.VERSION, version)
                    .set(WATCHLIST.CONTENTS, jsonb)
                    .where(WATCHLIST.ID.eq(existing))
                    .executeAsync()
                    .await()
            }
        }
    }

    private fun readJson(version: Int, jsonb: JSONB): Watchlist {
        return when (version) {
            1 -> json.parse(Watchlist.serializer(), jsonb.data())
            else -> throw IllegalStateException("Unrecognized watchlist version: $version")
        }
    }

    private fun writeJson(watchlist: Watchlist): Pair<Int, JSONB> {
        return 1 to JSONB.valueOf(json.stringify(Watchlist.serializer(), watchlist))
    }
}
