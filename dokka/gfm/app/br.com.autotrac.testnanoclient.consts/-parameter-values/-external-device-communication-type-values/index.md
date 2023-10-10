//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ParameterValues](../index.md)/[ExternalDeviceCommunicationTypeValues](index.md)

# ExternalDeviceCommunicationTypeValues

object [ExternalDeviceCommunicationTypeValues](index.md)

Valores possíveis para os tipos de comunicação (meio físico) a ser utilizado com o dispositivo externo.

#### See also

| |
|---|
| [ApiEndpoints.Companion.GET_PARAM_EXT_DEV_COMM_TYPE](../../-api-endpoints/-companion/-g-e-t_-p-a-r-a-m_-e-x-t_-d-e-v_-c-o-m-m_-t-y-p-e.md) |
| [ApiEndpoints.Companion.SET_PARAM_EXT_DEV_COMM_TYPE](../../-api-endpoints/-companion/-s-e-t_-p-a-r-a-m_-e-x-t_-d-e-v_-c-o-m-m_-t-y-p-e.md) |

## Properties

| Name | Summary |
|---|---|
| [RS232](-r-s232.md) | [androidJvm]<br>const val [RS232](-r-s232.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>Utilizar comunicação via interface serial RS232. |
| [RS232_AND_SOCKET](-r-s232_-a-n-d_-s-o-c-k-e-t.md) | [androidJvm]<br>const val [RS232_AND_SOCKET](-r-s232_-a-n-d_-s-o-c-k-e-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3<br>Utilizar comunicação via interface serial RS232 ou comunicação via socket, com acesso direto à rede (sem NetworkAccess) de acordo com a demanda (quando um falhar o outro será utilizado). |
| [RS232_AND_SOCKET_WIFI_CLIENT](-r-s232_-a-n-d_-s-o-c-k-e-t_-w-i-f-i_-c-l-i-e-n-t.md) | [androidJvm]<br>const val [RS232_AND_SOCKET_WIFI_CLIENT](-r-s232_-a-n-d_-s-o-c-k-e-t_-w-i-f-i_-c-l-i-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 6<br>Utilizar comunicação via interface serial RS232 ou comunicação via socket, com acesso à rede via Cliente WiFi de acordo com a demanda (quando um falhar o outro será utilizado). |
| [RS232_AND_SOCKET_WIFI_HOTSPOT](-r-s232_-a-n-d_-s-o-c-k-e-t_-w-i-f-i_-h-o-t-s-p-o-t.md) | [androidJvm]<br>const val [RS232_AND_SOCKET_WIFI_HOTSPOT](-r-s232_-a-n-d_-s-o-c-k-e-t_-w-i-f-i_-h-o-t-s-p-o-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 4<br>Utilizar comunicação via interface serial RS232 ou comunicação via socket, com acesso à rede via HotSpot WiFi de acordo com a demanda (quando um falhar o outro será utilizado). |
| [SOCKET](-s-o-c-k-e-t.md) | [androidJvm]<br>const val [SOCKET](-s-o-c-k-e-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1<br>Utilizar comunicação via socket, com acesso direto à rede (sem NetworkAccess). |
| [SOCKET_WIFI_CLIENT](-s-o-c-k-e-t_-w-i-f-i_-c-l-i-e-n-t.md) | [androidJvm]<br>const val [SOCKET_WIFI_CLIENT](-s-o-c-k-e-t_-w-i-f-i_-c-l-i-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 5<br>Utilizar comunicação via socket, com acesso à rede via Cliente WiFi. |
| [SOCKET_WIFI_HOTSPOT](-s-o-c-k-e-t_-w-i-f-i_-h-o-t-s-p-o-t.md) | [androidJvm]<br>const val [SOCKET_WIFI_HOTSPOT](-s-o-c-k-e-t_-w-i-f-i_-h-o-t-s-p-o-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 2<br>Utilizar comunicação via socket, com acesso à rede via HotSpot WiFi. |
