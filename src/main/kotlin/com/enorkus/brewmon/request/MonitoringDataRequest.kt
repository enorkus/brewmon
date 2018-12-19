package com.enorkus.brewmon.request

import org.springframework.data.mongodb.core.mapping.Document

@Document
class MonitoringDataRequest(
        val name: String,
        val ID: Long,
        val token: String,
        val angle: Float,
        val temperature: Float,
        val temp_units: String,
        val battery: Float,
        val gravity: Float,
        val interval: Float,
        val rssi: Float
)