package com.enorkus.brewmon.repository

import com.enorkus.brewmon.data.*
import com.enorkus.brewmon.response.*
import org.springframework.data.mongodb.repository.MongoRepository

interface MonitoringUnitRepository: MongoRepository<MonitoringUnit, Long>

interface AngleRepository: MongoRepository<Angle, Long> {
    fun findByName(name: String): List<AngleResponse>
}

interface TemperatureRepository: MongoRepository<Temperature, Long> {
    fun findByName(name: String): List<TemperatureResponse>
}

interface BatteryRepository: MongoRepository<Battery, Long> {
    fun findByName(name: String): List<BatteryResponse>
}

interface GravityRepository: MongoRepository<Gravity, Long> {
    fun findByName(name: String): List<GravityResponse>
}

interface IntervalRepository: MongoRepository<Interval, Long> {
    fun findFirstByOrderByTimestampDesc(name: String): IntervalResponse
}

interface RSSIRepository: MongoRepository<RSSI, Long> {
    fun findByName(name: String): List<RSSIResponse>
}