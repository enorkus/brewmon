package com.enorkus.brewmon.request

import org.springframework.data.mongodb.core.mapping.Document

@Document
class MonitoringDataRequest(
        val name: String,
        val ID: Long,
        val token: String,
        val angle: String,
        val temperature: String,
        val temp_units: String,
        val battery: String,
        val gravity: String,
        val interval: String,
        val rssi: String
)