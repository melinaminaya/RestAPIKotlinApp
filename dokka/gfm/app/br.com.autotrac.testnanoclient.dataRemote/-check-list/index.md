//[app](../../../index.md)/[br.com.autotrac.testnanoclient.dataRemote](../index.md)/[CheckList](index.md)

# CheckList

[androidJvm]\
data class [CheckList](index.md)(val wifiStatus: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val wifiSignal: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val cellularStatus: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, var cellularSignalLevel: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val hasSatelliteSignal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val lastGpsPosDate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val lastCommSatellite: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val lastCommCellular: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val batteryPowerPercent: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val batteryPowerDescription: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val batteryChargerState: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val gpsConfig: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val serviceVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val interfaceVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val databaseVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val soSystemVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val serviceStatus: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?)

Objeto de retorno da requisição [br.com.autotrac.testnanoclient.consts.ApiEndpoints.REQ_GET_CHECKLIST](../../br.com.autotrac.testnanoclient.consts/-api-endpoints/-companion/-r-e-q_-g-e-t_-c-h-e-c-k-l-i-s-t.md).

## Constructors

| | |
|---|---|
| [CheckList](-check-list.md) | [androidJvm]<br>constructor(wifiStatus: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, wifiSignal: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, cellularStatus: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, cellularSignalLevel: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, hasSatelliteSignal: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, lastGpsPosDate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, lastCommSatellite: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, lastCommCellular: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, batteryPowerPercent: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, batteryPowerDescription: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, batteryChargerState: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, gpsConfig: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, serviceVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, interfaceVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, databaseVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, soSystemVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, serviceStatus: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |

## Properties

| Name | Summary |
|---|---|
| [batteryChargerState](battery-charger-state.md) | [androidJvm]<br>val [batteryChargerState](battery-charger-state.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [batteryPowerDescription](battery-power-description.md) | [androidJvm]<br>val [batteryPowerDescription](battery-power-description.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [batteryPowerPercent](battery-power-percent.md) | [androidJvm]<br>val [batteryPowerPercent](battery-power-percent.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [cellularSignalLevel](cellular-signal-level.md) | [androidJvm]<br>var [cellularSignalLevel](cellular-signal-level.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [cellularStatus](cellular-status.md) | [androidJvm]<br>val [cellularStatus](cellular-status.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [databaseVersion](database-version.md) | [androidJvm]<br>val [databaseVersion](database-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [gpsConfig](gps-config.md) | [androidJvm]<br>val [gpsConfig](gps-config.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [hasSatelliteSignal](has-satellite-signal.md) | [androidJvm]<br>val [hasSatelliteSignal](has-satellite-signal.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [interfaceVersion](interface-version.md) | [androidJvm]<br>val [interfaceVersion](interface-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [lastCommCellular](last-comm-cellular.md) | [androidJvm]<br>val [lastCommCellular](last-comm-cellular.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [lastCommSatellite](last-comm-satellite.md) | [androidJvm]<br>val [lastCommSatellite](last-comm-satellite.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [lastGpsPosDate](last-gps-pos-date.md) | [androidJvm]<br>val [lastGpsPosDate](last-gps-pos-date.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [serviceStatus](service-status.md) | [androidJvm]<br>val [serviceStatus](service-status.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [serviceVersion](service-version.md) | [androidJvm]<br>val [serviceVersion](service-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [soSystemVersion](so-system-version.md) | [androidJvm]<br>val [soSystemVersion](so-system-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [wifiSignal](wifi-signal.md) | [androidJvm]<br>val [wifiSignal](wifi-signal.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [wifiStatus](wifi-status.md) | [androidJvm]<br>val [wifiStatus](wifi-status.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
