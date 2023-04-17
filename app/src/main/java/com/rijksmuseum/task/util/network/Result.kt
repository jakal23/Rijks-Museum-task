package com.rijksmuseum.task.util.network

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    /**
     * Check [Result] is success or not
     * @return [Boolean] true is success, false is otherwise
     */
    fun isSuccess(): Boolean {
        return this is Success
    }

    /**
     * Return data of [Result.Success]
     * @return [T] generic type of success data
     */
    fun toData(): T {
        return (this as Success).data
    }

    /**
     * Check [Result] is error or not
     * @return [Boolean] true is error, false is otherwise
     */
    fun isError(): Boolean {
        return this is Error
    }

    /**
     * Cast to specific type [Result.Error]
     * @return error [Exception]
     */
    fun toException(): Exception {
        return (this as Error).exception
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}