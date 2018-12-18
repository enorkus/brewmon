package com.enorkus.brewmon.response

class MonitoringUnitResponse(
        val name: String
)

class AngleResponse(
        val timestamp: Long,
        val value: String
)

class TemperatureResponse(
        val timestamp: Long,
        val value: String
)

class BatteryResponse(
        val timestamp: Long,
        val value: String
)

class GravityResponse(
        val timestamp: Long,
        val value: String
)

class IntervalResponse(
        val timestamp: Long,
        val value: String
)

class RSSIResponse(
        val timestamp: Long,
        val value: String
)