package com.example.androidtechnicaltest.repository.core

/**
 * A generic class that holds the repository's result value with its loading status.
 * @param <T>
 */
data class Result<out T>(val status: Status, val data: T?, val throwable: Throwable?) {
    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable, data: T?): Result<T> {
            return Result(Status.ERROR, data, error)
        }

        fun <T> loading(data: T?): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}