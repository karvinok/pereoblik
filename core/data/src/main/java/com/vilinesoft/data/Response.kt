package com.vilinesoft.data

import com.vilinesoft.domain.model.BaseError
import com.vilinesoft.domain.model.Either
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <R> handleResponse(
    requestBlock: () -> R
): Either<BaseError, R> {
    return runCatching { requestBlock.invoke() }
        .fold({
            Either.Success(it)
        }, { e ->
            val baseErrorFailure = when (e) {
                is HttpException -> Either.Failure(
                    when (e.code()) {
                        400, 401, 500 -> BaseError.ServerError(e.code(), e.buildMessage())
                        else -> BaseError.NetworkError(code = e.code(), message = e.buildMessage())
                    }
                )

                is UnknownHostException,
                is ConnectException,
                is SocketTimeoutException -> Either.Failure(
                    BaseError.NetworkError(
                        message = e.localizedMessage
                    )
                )

                else -> Either.Failure(
                    BaseError.Error(
                        e.localizedMessage ?: e.toString()
                    )
                )
            }
            baseErrorFailure
        })
}

fun HttpException.buildMessage(): String {
    return try {
        val body = response()?.errorBody() as ResponseBody
        buildString {
            append(body.string())
            append(" ", code())
            append(" ", localizedMessage)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        e.message ?: ""
    }
}