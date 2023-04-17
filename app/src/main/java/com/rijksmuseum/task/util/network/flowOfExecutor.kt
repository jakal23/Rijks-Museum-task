package com.rijksmuseum.task.util.network

import com.rijksmuseum.task.util.network.exception.InvalidRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun <T : Any> flowOfExecutor(getResponse: suspend () -> T): Flow<Result<T>> {
    return flow {
        emit(request(getResponse))
    }
}

suspend fun <T : Any> request(
    getResponse: suspend () -> T
): Result<T> {
    try {
        val response = getResponse()
        return Result.Success(response)
    } catch (exception: IOException) {
        return Result.Error(exception)
    } catch (exception: HttpException) {
        val errorBody = exception.response()?.errorBody()?.string()

        return Result.Error(com.rijksmuseum.task.util.network.exception.HttpException(
            exception.response()?.run { raw().request.url.toString() } ?: "",
            exception.code(),
            exception.message(),
            errorBody
        ))
    } catch (t: Throwable) {
        return Result.Error(InvalidRequestException(t))
    }
}