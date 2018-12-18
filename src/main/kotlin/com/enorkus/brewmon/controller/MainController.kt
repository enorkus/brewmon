package com.enorkus.brewmon.controller

import com.enorkus.brewmon.data.*
import com.enorkus.brewmon.repository.*
import com.enorkus.brewmon.request.MonitoringDataRequest
import com.enorkus.brewmon.service.MonitoringDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/data")
class MainController {

    @Autowired
    lateinit var dataService: MonitoringDataService

    @PostMapping("/insert")
    fun insertData(@RequestBody request: MonitoringDataRequest) = dataService.insertData(request)

    @RequestMapping("/units")
    fun fetchAllMonitoringUnits() = dataService.fetchAllMonitoringUnits()

    @RequestMapping("/angle")
    fun fetchAngleDataByUnitName(@RequestParam unitName: String) = dataService.fetchAngleDataByUnitName(unitName)

    @RequestMapping("/temperature")
    fun fetchTemperatureDataByUnitName(@RequestParam unitName: String) = dataService.fetchTemperatureDataByUnitName(unitName)

    @RequestMapping("/battery")
    fun fetchBatteryDataByUnitName(@RequestParam unitName: String) = dataService.fetchBatteryDataByUnitName(unitName)

    @RequestMapping("/gravity")
    fun fetchGravityDataByUnitName(@RequestParam unitName: String) = dataService.fetchGravityDataByUnitName(unitName)

    @RequestMapping("/interval")
    fun fetchIntervalDataByUnitName(@RequestParam unitName: String) = dataService.fetchLatestIntervalDataByUnitName(unitName)

    @RequestMapping("/rssi")
    fun fetchRSSIDataByUnitName(@RequestParam unitName: String) = dataService.fetchRSSIDataByUnitName(unitName)


    //TEST DATA

    @RequestMapping("/insertTestData")
    fun insertTestData() {
        dataService.insertTestData()
    }

    @RequestMapping("/clearDataBase")
    fun clearDataBase() {
        dataService.clearDataBase()
    }

}