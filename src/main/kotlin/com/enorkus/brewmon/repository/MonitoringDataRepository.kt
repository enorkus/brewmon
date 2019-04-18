package com.enorkus.brewmon.repository

import com.enorkus.brewmon.data.*
import org.springframework.data.mongodb.repository.MongoRepository

interface MonitoringUnitRepository: MongoRepository<MonitoringUnit, Long> {
    fun findByName(name: String) : MonitoringUnit?
}

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
    fun findFirstByNameOrderByTimestampAsc(name: String): TimestampedFloatData
    fun findTop10ByNameOrderByTimestampDesc(name: String): List<TimestampedFloatData>
}

interface RSSIRepository: MongoRepository<RSSI, Long> {
    fun findByName(name: String): List<TimestampedFloatData>
}
