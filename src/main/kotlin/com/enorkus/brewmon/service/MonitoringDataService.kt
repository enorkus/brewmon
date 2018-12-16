package com.enorkus.brewmon.service

import com.enorkus.brewmon.data.*
import com.enorkus.brewmon.repository.*
import com.enorkus.brewmon.request.MonitoringDataRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MonitoringDataService {

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

        if (!unitExists) monitoringUnitRepository.insert(MonitoringUnit(request.name))

        val currentTime = System.currentTimeMillis()

        angleRepository.insert(Angle(currentTime, request.name, request.angle))
        temperatureRepository.insert(Temperature(currentTime, request.name, request.temperature))
        batteryRepository.insert(Battery(currentTime, request.name, request.battery))
        gravityRepository.insert(Gravity(currentTime, request.name, request.gravity))
        intervalRepository.insert(Interval(currentTime, request.name, request.interval))
        rssiRepository.insert(RSSI(currentTime, request.name, request.rssi))
    }

    fun fetchAllMonitoringUnits(): List<MonitoringUnit> = monitoringUnitRepository.findAll()

    fun fetchAngleDataByUnitName(name: String): List<Angle> = angleRepository.findByName(name)

    fun fetchTemperatureDataByUnitName(name: String) = temperatureRepository.findByName(name)

    fun fetchBatteryDataByUnitName(name: String) = batteryRepository.findByName(name)

    fun fetchGravityDataByUnitName(name: String) = gravityRepository.findByName(name)

    fun fetchIntervalDataByUnitName(name: String) = intervalRepository.findByName(name)

    fun fetchRSSIDataByUnitName(name: String) = rssiRepository.findByName(name)


    //TEST DATA

    fun insertTestData() {

        val unit1 = "spindel-1"
        val unit2 = "spindel-2"

        monitoringUnitRepository.insert(MonitoringUnit(unit1))
        monitoringUnitRepository.insert(MonitoringUnit(unit2))

        val currentTime = System.currentTimeMillis()

        for(i in 1..10) {
            angleRepository.insert(Angle(currentTime, unit1, i.toString()))
            temperatureRepository.insert(Temperature(currentTime, unit1, i.toString()))
            batteryRepository.insert(Battery(currentTime, unit1, i.toString()))
            gravityRepository.insert(Gravity(currentTime, unit1, i.toString()))
            intervalRepository.insert(Interval(currentTime, unit1, "1200"))
            rssiRepository.insert(RSSI(currentTime, unit1, i.toString()))
        }

        for(i in 11..20) {
            angleRepository.insert(Angle(currentTime, unit2, i.toString()))
            temperatureRepository.insert(Temperature(currentTime, unit2, i.toString()))
            batteryRepository.insert(Battery(currentTime, unit2, i.toString()))
            gravityRepository.insert(Gravity(currentTime, unit2, i.toString()))
            intervalRepository.insert(Interval(currentTime, unit2, "1200"))
            rssiRepository.insert(RSSI(currentTime, unit2, i.toString()))
        }
    }

    fun clearDataBase() {
        monitoringUnitRepository.deleteAll()
        angleRepository.deleteAll()
        temperatureRepository.deleteAll()
        batteryRepository.deleteAll()
        gravityRepository.deleteAll()
        intervalRepository.deleteAll()
        rssiRepository.deleteAll()
    }
}