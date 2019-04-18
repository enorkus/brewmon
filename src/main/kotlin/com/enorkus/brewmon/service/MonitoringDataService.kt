package com.enorkus.brewmon.service

import com.enorkus.brewmon.data.*
import com.enorkus.brewmon.repository.*
import com.enorkus.brewmon.request.MonitoringDataRequest
import com.enorkus.brewmon.response.MonitoringUnitResponse
import com.enorkus.brewmon.response.TimestampedFloatDataResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Level
import java.util.logging.Logger
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update


@Service
class MonitoringDataService {

    private val LOGGER = Logger.getLogger(MonitoringDataService::class.java.name)

    @Autowired
    lateinit var monitoringUnitRepository: MonitoringUnitRepository

    @Autowired
    lateinit var angleRepository: AngleRepository

    @Autowired
    lateinit var temperatureRepository: TemperatureRepository

    @Autowired
    lateinit var batteryRepository: BatteryRepository

    @Autowired
    lateinit var gravityRepository: GravityRepository

    @Autowired
    lateinit var rssiRepository: RSSIRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    fun insertData(request: MonitoringDataRequest) {
        val currentTime = System.currentTimeMillis()

        insertOrUpdateMonitoringUnit(request)
        try {
            angleRepository.insert(Angle(currentTime, request.name, request.angle))
        } catch (e: Exception) {
            LOGGER.log(Level.FINE, "Failed to insert angle: $request", e)
        }
        try {
            temperatureRepository.insert(Temperature(currentTime, request.name, request.temperature))
        } catch (e: Exception) {
            LOGGER.log(Level.FINE, "Failed to insert temperature: $request", e)
        }
        try {
            batteryRepository.insert(Battery(currentTime, request.name, request.battery))
        } catch (e: Exception) {
            LOGGER.log(Level.FINE, "Failed to insert battery: $request", e)
        }
        try {
            gravityRepository.insert(Gravity(currentTime, request.name, request.gravity))
        } catch (e: Exception) {
            LOGGER.log(Level.FINE, "Failed to insert gravity: $request", e)
        }
        try {
            rssiRepository.insert(RSSI(currentTime, request.name, request.RSSI))
        } catch (e: Exception) {
            LOGGER.log(Level.FINE, "Failed to insert RSSI: $request", e)
        }
    }

    private fun insertOrUpdateMonitoringUnit(request: MonitoringDataRequest) {
        val currentTime = System.currentTimeMillis()
        val unit = monitoringUnitRepository.findByName(request.name)
        if (unit == null) {
            monitoringUnitRepository.insert(MonitoringUnit(request.name, currentTime, (request.interval * 1000).toLong(), request.RSSI.toInt(), currentTime, 0.0))
        } else {
            val alcoholByVolume = calculateAlcoholByVolume(unit.name, request.gravity)
            val query = Query(Criteria.where("name").`is`(request.name))
            val update = Update()
                    .set("lastUpdated", currentTime)
                    .set("updateInterval", (request.interval * 1000).toLong())
                    .set("lastRSSI", request.RSSI)
                    .set("alcoholByVolume", alcoholByVolume)
            mongoTemplate.findAndModify(query, update, MonitoringUnit::class.java)
        }
    }

    private fun calculateAlcoholByVolume(unitName: String, gravity: Float): Double {
        val originalGravity = gravityRepository.findFirstByNameOrderByTimestampAsc(unitName).value
        return (originalGravity - gravity) * 131.25
    }

    fun fetchAllMonitoringUnits(): List<MonitoringUnitResponse> {
        val allUnits = monitoringUnitRepository.findAll()
        val monitoringUnitsResponse = mutableListOf<MonitoringUnitResponse>()
        allUnits.forEach { unit ->
            val currentTime = System.currentTimeMillis()
            val isOn = unit.updateInterval - (currentTime - unit.lastUpdated) > 0
            val updateIntervalMins = unit.updateInterval / 60000
            val inFermentationDays = ((unit.lastUpdated - unit.fermentationStart) / (24 * 60 * 60 * 1000)).toInt()
            val abv = calculateMeanABV(unit.name)
            monitoringUnitsResponse.add(MonitoringUnitResponse(unit.name, isOn, unit.lastUpdated, updateIntervalMins, unit.lastRSSI, inFermentationDays, abv))
        }
        return monitoringUnitsResponse;
    }
    
    private fun calculateMeanABV(unitName: String): Double {
        val originalGravity = gravityRepository.findFirstByNameOrderByTimestampAsc(unitName).value
        val originalGravitySpecific = convertPlatoToSpecificGravity(originalGravity)

        val last10Gravities = gravityRepository.findTop10ByNameOrderByTimestampDesc(unitName)
        var sumOfLastABVs = 0.0
        last10Gravities.forEach { sumOfLastABVs += ((originalGravitySpecific - convertPlatoToSpecificGravity(it.value)) * 131.25) }
        return sumOfLastABVs/last10Gravities.size
    }

    fun fetchAngleDataByUnitName(name: String) = mapResponse(angleRepository.findByName(name))

    fun fetchTemperatureDataByUnitName(name: String) = mapResponse(temperatureRepository.findByName(name))

    fun fetchBatteryDataByUnitName(name: String) = mapResponse(batteryRepository.findByName(name))

    fun fetchGravityDataByUnitName(name: String) = mapGravityResponse(gravityRepository.findByName(name))

    fun fetchRSSIDataByUnitName(name: String) = mapResponse(rssiRepository.findByName(name))

    fun mapResponse(timestampedFloatData: List<TimestampedFloatData>): TimestampedFloatDataResponse {
        val timestamps = mutableListOf<Long>()
        val values = mutableListOf<Float>()
        timestampedFloatData.forEach { data ->
            timestamps.add(data.timestamp)
            values.add(data.value)
        }
        return TimestampedFloatDataResponse(timestamps, values)
    }

    fun mapGravityResponse(timestampedFloatData: List<TimestampedFloatData>): TimestampedFloatDataResponse {
        val timestamps = mutableListOf<Long>()
        val values = mutableListOf<Float>()
        timestampedFloatData.forEach { data ->
            timestamps.add(data.timestamp)
            values.add(convertPlatoToSpecificGravity(data.value))
        }
        return TimestampedFloatDataResponse(timestamps, values)
    }

    fun convertPlatoToSpecificGravity(value: Float) = (1 + value / (258.6 - 227.1 * value / 258.2)).toFloat()
}
