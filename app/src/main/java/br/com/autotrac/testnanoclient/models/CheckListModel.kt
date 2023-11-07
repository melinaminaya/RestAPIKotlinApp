package br.com.autotrac.testnanoclient.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Objeto de retorno da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_GET_CHECKLIST].
 */
data class CheckList(

    @JsonProperty("WiFiStatus") val wifiStatus: Any?,
    @JsonProperty("WiFiSignal") val wifiSignal: Any?,
    @JsonProperty("CellularStatus") val cellularStatus:Any?,
    @JsonProperty("CellularSignalLevel") var cellularSignalLevel: Any?,
    @JsonProperty("HasSatelliteSignal") val hasSatelliteSignal: String?,
    @JsonProperty("LastGpsPosDate") val lastGpsPosDate : String?,
    @JsonProperty("LastCommSatellite") val lastCommSatellite:String?,
    @JsonProperty("LastCommCellular") val lastCommCellular:String?,
    @JsonProperty("BattPowerPercent") val batteryPowerPercent:Any?,
    @JsonProperty("BattPowerDescription") val batteryPowerDescription:Any?,
    @JsonProperty("BattChargerState") val batteryChargerState:Any?,
    @JsonProperty("GpsConfig") val gpsConfig:Any?,
    @JsonProperty("ServiceVersion") val serviceVersion:String?,
    @JsonProperty("InterfaceVersion") val interfaceVersion:String?,
    @JsonProperty("DatabaseVersion") val databaseVersion:String?,
    @JsonProperty("SOSystemVersion") val soSystemVersion:String?,
    @JsonProperty("ServiceStatus") val serviceStatus:Any?,

    )
