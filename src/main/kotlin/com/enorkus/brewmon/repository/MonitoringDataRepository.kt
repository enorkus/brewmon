package com.enorkus.brewmon.repository

import com.enorkus.brewmon.data.*
import org.springframework.data.mongodb.repository.MongoRepository

interface MonitoringUnitRepository: MongoRepository<MonitoringUnit, Long> {
    fun findByName(name: String): MonitoringUnit
}

interface AngleRepository: MongoRepository<Angle, Long> {
    fun findByName(name: String): List<Angle>
}

interface TemperatureRepository: MongoRepository<Temperature, Long> {
    fun findByName(name: String): List<Temperature>
}

interface BatteryRepository: MongoRepository<Battery, Long> {
    fun findByName(name: String): List<Battery>
}

interface GravityRepository: MongoRepository<Gravity, Long> {
    fun findByName(name: String): List<Gravity>
}

interface IntervalRepository: MongoRepository<Interval, Long> {
    fun findFirstByOrderByTimestampDesc(name: String): Interval
}

interface RSSIRepository: MongoRepository<RSSI, Long> {
    fun findByName(name: String): List<RSSI>
}