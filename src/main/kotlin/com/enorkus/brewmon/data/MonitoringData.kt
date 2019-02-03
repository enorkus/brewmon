package com.enorkus.brewmon.data

import org.springframework.data.mongodb.core.mapping.Document

open class TimestampedFloatData(val timestamp: Long, val value: Float)

@Document
class MonitoringUnit(val name: String, var lastUpdated: Long, var updateInterval: Long, var lastRSSI: Int, var fermentationStart: Long)

@Document
class Angle(timestamp: Long, val name: String?, value: Float): TimestampedFloatData(timestamp, value)

@Document
class Temperature(timestamp: Long, val name: String?, value: Float): TimestampedFloatData(timestamp, value)

@Document
class Battery(timestamp: Long, val name: String?, value: Float): TimestampedFloatData(timestamp, value)

@Document
class Gravity(timestamp: Long, val name: String?, value: Float): TimestampedFloatData(timestamp, value)

@Document
class RSSI(timestamp: Long, val name: String?, value: Float): TimestampedFloatData(timestamp, value)