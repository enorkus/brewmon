package com.enorkus.brewmon.response

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.HttpStatusCodeException

class MonitoringUnitResponse(
        val name: String
)

class TimestampedFloatDataResponse(
        val timestamps: MutableList<Long>,
        val values: MutableList<Float>
)

@ResponseStatus(HttpStatus.FORBIDDEN)
class HttpStatusForbidden : HttpStatusCodeException(HttpStatus.FORBIDDEN)