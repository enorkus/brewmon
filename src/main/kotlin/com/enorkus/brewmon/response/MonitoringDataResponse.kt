package com.enorkus.brewmon.response

class MonitoringUnitResponse(
        val name: String
)

class TimestampedFloatDataResponse(
        val timestamps: MutableList<Long>,
        val values: MutableList<Float>
)