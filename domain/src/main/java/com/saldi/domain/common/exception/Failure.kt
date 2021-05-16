package com.saldi.domain.common.exception

/**
 * Created by Sourabh on 10/5/21
 *
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object DataError : Failure()
    object ServerError : Failure()
}
