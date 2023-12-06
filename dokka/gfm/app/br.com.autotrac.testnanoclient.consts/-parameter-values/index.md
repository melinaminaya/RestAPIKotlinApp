//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ParameterValues](index.md)

# ParameterValues

[androidJvm]\
object [ParameterValues](index.md)

Valores para parâmetros da integração. Cada parâmetro pode retornar um valor específico.

#### Author

Melina Minaya

## Types

| Name | Summary |
|---|---|
| [CmuSubtypeValues](-cmu-subtype-values/index.md) | [androidJvm]<br>object [CmuSubtypeValues](-cmu-subtype-values/index.md)<br>Valores possíveis para o parâmetro: |
| [ExternalDeviceCommunicationTypeValues](-external-device-communication-type-values/index.md) | [androidJvm]<br>object [ExternalDeviceCommunicationTypeValues](-external-device-communication-type-values/index.md)<br>Valores possíveis para os tipos de comunicação (meio físico) a ser utilizado com o dispositivo externo. |
| [HasUpdatePendingValues](-has-update-pending-values/index.md) | [androidJvm]<br>object [HasUpdatePendingValues](-has-update-pending-values/index.md)<br>Valores possíveis para o [ApiEndpoints.GET_PARAM_HAS_UPDATE_PENDING](../-api-endpoints/-g-e-t_-p-a-r-a-m_-h-a-s_-u-p-d-a-t-e_-p-e-n-d-i-n-g.md). |
| [HWControlDisableValues](-h-w-control-disable-values/index.md) | [androidJvm]<br>object [HWControlDisableValues](-h-w-control-disable-values/index.md)<br>Possíveis valores para [ApiEndpoints.GET_PARAM_HW_CONTROL_DISABLE](../-api-endpoints/-g-e-t_-p-a-r-a-m_-h-w_-c-o-n-t-r-o-l_-d-i-s-a-b-l-e.md). Mapa de bits. Pode ser uma combinação de um dos valores. Um bit setado indica que o gerenciamento do dispositivo em questão não deve ser executado. |
| [MctM0M9Params](-mct-m0-m9-params/index.md) | [androidJvm]<br>object [MctM0M9Params](-mct-m0-m9-params/index.md)<br>Identificadores dos parâmetros do MCT (number). Retornados pelo endpoint [ApiEndpoints.REQ_GET_MCT_PARAMETERS](../-api-endpoints/-r-e-q_-g-e-t_-m-c-t_-p-a-r-a-m-e-t-e-r-s.md). |
| [MctSignalValues](-mct-signal-values/index.md) | [androidJvm]<br>object [MctSignalValues](-mct-signal-values/index.md)<br>Indica se o Mct está com sinal ou não (=0 sem sinal, =1 com sinal). |
| [UcStatusValues](-uc-status-values/index.md) | [androidJvm]<br>object [UcStatusValues](-uc-status-values/index.md)<br>Valores possíveis para o status atualizado da Uc Móvel após a tentativa de estabelecimento de comunicação via celular com o servidor. |
| [ValuesConnectionStates](-values-connection-states/index.md) | [androidJvm]<br>object [ValuesConnectionStates](-values-connection-states/index.md)<br>Tipos possiveis de conexão. |
| [ValuesNetworkTypes](-values-network-types/index.md) | [androidJvm]<br>object [ValuesNetworkTypes](-values-network-types/index.md)<br>Tipos de redes de comunicação disponiveis. |
| [VpnConnectionStatusValues](-vpn-connection-status-values/index.md) | [androidJvm]<br>object [VpnConnectionStatusValues](-vpn-connection-status-values/index.md)<br>Indica o status atual da conexão VPN (=0 desconectada, =1 conectada). |
| [VpnDisableValues](-vpn-disable-values/index.md) | [androidJvm]<br>object [VpnDisableValues](-vpn-disable-values/index.md)<br>Indica se o uso da VPN pelo serviço de comunicação é permitido ou não. |
| [WifiDisableValues](-wifi-disable-values/index.md) | [androidJvm]<br>object [WifiDisableValues](-wifi-disable-values/index.md)<br>Desabilita o uso da rede WiFi. (=0 habilita o uso, =1 desabilita o uso. Aplicações externas podem utilizar o WiFi.) |

## Properties

| Name | Summary |
|---|---|
| [listExtDevCommType](list-ext-dev-comm-type.md) | [androidJvm]<br>val [listExtDevCommType](list-ext-dev-comm-type.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
