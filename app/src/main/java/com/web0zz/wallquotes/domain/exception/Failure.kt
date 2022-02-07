package com.web0zz.wallquotes.domain.exception

sealed class Failure(open val message: String) {
    // In normal situation this sealed class will contain NetworkError, Api error etc.
    data class UnknownError(override val message: String, val exceptionMessage: String?) :
        Failure(message)
}
