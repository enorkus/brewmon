package com.enorkus.brewmon.controller

import com.enorkus.brewmon.data.*
import com.enorkus.brewmon.repository.*
import com.enorkus.brewmon.request.MonitoringDataRequest
import com.enorkus.brewmon.response.HttpStatusForbidden
import com.enorkus.brewmon.service.MonitoringDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException

@RestController
@RequestMapping("/data")
class MainController {

    @Autowired
    lateinit var dataService: MonitoringDataService
    @Value("\${api.token}")
    lateinit var apiToken: String

    @PostMapping("/insert")
    fun insertData(@RequestBody request: MonitoringDataRequest) {
        if(apiToken.equals(request.token)) {
            dataService.insertData(request)
        } else {
            throw HttpStatusForbidden()
        }
    }

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
}