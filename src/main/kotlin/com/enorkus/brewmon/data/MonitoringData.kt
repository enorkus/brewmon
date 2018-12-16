package com.enorkus.brewmon.data

import org.springframework.data.mongodb.core.mapping.Document

@Document
class MonitoringUnit(
        val name: String
)

@Document
class Angle(
        val timestamp: Long,
        val name: String,
        val value: String
)

@Document
class Temperature(
        val timestamp: Long,
        val name: String,
        val value: String
)

@Document
class Battery(
        val timestamp: Long,
        val name: String,
        val value: String
)

@Document
class Gravity(
        val timestamp: Long,
        val name: String,
        val value: String
)

@Document
class Interval(
        val timestamp: Long,
        val name: String,
        val value: String
)

@Document
class RSSI(
        val timestamp: Long,
        val name: String,
        val value: String
)