package com.enorkus.brewmon.repository

import com.enorkus.brewmon.data.*
import com.enorkus.brewmon.response.*
import org.springframework.data.mongodb.repository.MongoRepository

interface MonitoringUnitRepository: MongoRepository<MonitoringUnit, Long>

interface AngleRepository: MongoRepository<Angle, Long> {
    fun findByName(name: String): List<TimestampedFloatData>
}

interface TemperatureRepository: MongoRepository<Temperature, Long> {
    fun findByName(name: String): List<TimestampedFloatData>
}

interface BatteryRepository: MongoRepository<Battery, Long> {
    fun findByName(name: String): List<TimestampedFloatData>
}

interface GravityRepository: MongoRepository<Gravity, Long> {
    fun findByName(name: String): List<TimestampedFloatData>
}

interface RSSIRepository: MongoRepository<RSSI, Long> {
    fun findByName(name: String): List<TimestampedFloatData>
}

interface IntervalRepository: MongoRepository<Interval, Long> {
    fun findFirstByOrderByTimestampDesc(name: String): TimestampedFloatData
}
