package com.enorkus.brewmon.service

import com.enorkus.brewmon.data.*
import com.enorkus.brewmon.repository.*
import com.enorkus.brewmon.request.MonitoringDataRequest
import com.enorkus.brewmon.response.TimestampedFloatDataResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Level
import java.util.logging.Logger

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
    lateinit var intervalRepository: IntervalRepository

    @Autowired
    lateinit var rssiRepository: RSSIRepository

    fun insertData(request: MonitoringDataRequest) {
        val allUnits = monitoringUnitRepository.findAll();
        var unitExists = false
        allUnits.forEach {
            if (it.name.equals(request.name)) unitExists = true
        }

        try { if (!unitExists) monitoringUnitRepository.insert(MonitoringUnit(request.name)) } catch (e: Exception){ LOGGER.log(Level.FINE, "Failed to insert unit: $request", e) }

        val currentTime = System.currentTimeMillis()

        try { angleRepository.insert(Angle(currentTime, request.name, request.angle)) } catch(e: Exception){ LOGGER.log(Level.FINE, "Failed to insert angle: $request", e) }
        try { temperatureRepository.insert(Temperature(currentTime, request.name, request.temperature)) } catch(e: Exception){ LOGGER.log(Level.FINE, "Failed to insert temperature: $request", e) }
        try { batteryRepository.insert(Battery(currentTime, request.name, request.battery)) } catch(e: Exception){ LOGGER.log(Level.FINE, "Failed to insert battery: $request", e) }
        try { gravityRepository.insert(Gravity(currentTime, request.name, request.gravity)) } catch(e: Exception){ LOGGER.log(Level.FINE, "Failed to insert gravity: $request", e) }
        try {
            if(request.interval != intervalRepository.findFirstByNameOrderByTimestampDesc(request.name)?.value) {
                try{ intervalRepository.insert(Interval(currentTime, request.name, request.interval)) } catch(e: Exception){ LOGGER.log(Level.FINE, "Failed to insert interval: $request", e) }
            }
        } catch (e: Exception) {
            LOGGER.log(Level.FINE, "Failed to find interval: $request", e)
        }


        try{ rssiRepository.insert(RSSI(currentTime, request.name, request.RSSI)) }catch(e: Exception){ LOGGER.log(Level.FINE, "Failed to insert RSSI: $request", e) }
    }

    fun fetchAllMonitoringUnits(): List<MonitoringUnit> = monitoringUnitRepository.findAll()

    fun fetchAngleDataByUnitName(name: String) = mapResponse(angleRepository.findByName(name))

    fun fetchTemperatureDataByUnitName(name: String) = mapResponse(temperatureRepository.findByName(name))

    fun fetchBatteryDataByUnitName(name: String) = mapResponse(batteryRepository.findByName(name))

    fun fetchGravityDataByUnitName(name: String) = mapGravityResponse(gravityRepository.findByName(name))

    fun fetchRSSIDataByUnitName(name: String) = mapResponse(rssiRepository.findByName(name))

    fun fetchLatestIntervalDataByUnitName(name: String) = intervalRepository.findFirstByNameOrderByTimestampDesc(name)

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

    fun convertPlatoToSpecificGravity(value: Float) = (1 + value / (258.6 - 227.1 * value /258.2)).toFloat()
}