package com.ferumbot.servercms.models.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonInclude(NON_NULL)
data class ServerResponse <T: Any> (

    val timeStamp: LocalDateTime = LocalDateTime.now(),

    val statusCode: Int = 200,

    val status: HttpStatus = HttpStatus.OK,

    val reason: String? = null,

    val message: String? = null,

    val developerMessage: String? = null,

    val data: T? = null,
) {

    companion object {

        fun <T: Any> of(data: T): ServerResponse<T> {
            return ServerResponse(data = data)
        }
    }

}
