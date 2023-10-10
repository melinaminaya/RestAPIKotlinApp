//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ActionValues](index.md)

# ActionValues

[androidJvm]\
object [ActionValues](index.md)

Identificadores das ações de notificação.

#### Author

Melina Minaya

## Types

| Name | Summary |
|---|---|
| [FileOperationFiles](-file-operation-files/index.md) | [androidJvm]<br>object [FileOperationFiles](-file-operation-files/index.md)<br>Valores de arquivos utilizados no [ApiEndpoints.REQ_CONFIG_SERVICE_LOG](../-api-endpoints/-companion/-r-e-q_-c-o-n-f-i-g_-s-e-r-v-i-c-e_-l-o-g.md) |
| [FileOperationOptions](-file-operation-options/index.md) | [androidJvm]<br>object [FileOperationOptions](-file-operation-options/index.md)<br>Opções a serem utilizadas na cópia de arquivos setados pelo Param2 da requisição [ApiEndpoints.REQ_FILE_OPERATION](../-api-endpoints/-companion/-r-e-q_-f-i-l-e_-o-p-e-r-a-t-i-o-n.md). |
| [FormTypeValues](-form-type-values/index.md) | [androidJvm]<br>object [FormTypeValues](-form-type-values/index.md)<br>Valores do tipo de formulário. |
| [MessageStatusValues](-message-status-values/index.md) | [androidJvm]<br>object [MessageStatusValues](-message-status-values/index.md)<br>Valores dos status de mensagens. |
| [PositionSourceType](-position-source-type/index.md) | [androidJvm]<br>object [PositionSourceType](-position-source-type/index.md)<br>Valores da requisição de última posição. |
| [ValuesBaptismStatusParam1](-values-baptism-status-param1/index.md) | [androidJvm]<br>object [ValuesBaptismStatusParam1](-values-baptism-status-param1/index.md)<br>Status retornados pelo Param1 de ação [ActionValues.BAPTISM_STATUS](-b-a-p-t-i-s-m_-s-t-a-t-u-s.md). Utilizado também no retorno da [ApiEndpoints.GET_PARAM_IS_BAPTIZED](../-api-endpoints/-companion/-g-e-t_-p-a-r-a-m_-i-s_-b-a-p-t-i-z-e-d.md) . |
| [ValuesFileOperationStatusParam1](-values-file-operation-status-param1/index.md) | [androidJvm]<br>object [ValuesFileOperationStatusParam1](-values-file-operation-status-param1/index.md)<br>Status posiveis retornados pelo Param1 da ação [ActionValues.FILE_OPERATION_STATUS](-f-i-l-e_-o-p-e-r-a-t-i-o-n_-s-t-a-t-u-s.md). |
| [ValuesIgnitionStatusParam1](-values-ignition-status-param1/index.md) | [androidJvm]<br>object [ValuesIgnitionStatusParam1](-values-ignition-status-param1/index.md)<br>Status retornados pelo Param1 da ação [ActionValues.IGNITION_STATUS](-i-g-n-i-t-i-o-n_-s-t-a-t-u-s.md). Utilizado também para [ApiEndpoints.GET_PARAM_IGNITION_STATUS](../-api-endpoints/-companion/-g-e-t_-p-a-r-a-m_-i-g-n-i-t-i-o-n_-s-t-a-t-u-s.md) |
| [ValuesSysResourceReqParam1](-values-sys-resource-req-param1/index.md) | [androidJvm]<br>object [ValuesSysResourceReqParam1](-values-sys-resource-req-param1/index.md)<br>Possiveis recursos do sistema cujos status da solicitação podem ser informados. |
| [ValuesSysResourceStatusParam2](-values-sys-resource-status-param2/index.md) | [androidJvm]<br>object [ValuesSysResourceStatusParam2](-values-sys-resource-status-param2/index.md)<br>Possiveis status dos recursos solicitados ao sistema. |

## Properties

| Name | Summary |
|---|---|
| [BAPTISM_STATUS](-b-a-p-t-i-s-m_-s-t-a-t-u-s.md) | [androidJvm]<br>const val [BAPTISM_STATUS](-b-a-p-t-i-s-m_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 7<br>Indica o status do processo de batismo. |
| [BAPTISM_STATUS_OBSERVABLE](-b-a-p-t-i-s-m_-s-t-a-t-u-s_-o-b-s-e-r-v-a-b-l-e.md) | [androidJvm]<br>const val [BAPTISM_STATUS_OBSERVABLE](-b-a-p-t-i-s-m_-s-t-a-t-u-s_-o-b-s-e-r-v-a-b-l-e.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [COMMUNICATION_MODE_CHANGED](-c-o-m-m-u-n-i-c-a-t-i-o-n_-m-o-d-e_-c-h-a-n-g-e-d.md) | [androidJvm]<br>const val [COMMUNICATION_MODE_CHANGED](-c-o-m-m-u-n-i-c-a-t-i-o-n_-m-o-d-e_-c-h-a-n-g-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 10<br>Mudanca no modo de comunicação. |
| [DATE_TIME_CHANGED](-d-a-t-e_-t-i-m-e_-c-h-a-n-g-e-d.md) | [androidJvm]<br>const val [DATE_TIME_CHANGED](-d-a-t-e_-t-i-m-e_-c-h-a-n-g-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 18<br>Notifica que a Data/Hora do serviço foi alterada. |
| [FILE_OPERATION_STATUS](-f-i-l-e_-o-p-e-r-a-t-i-o-n_-s-t-a-t-u-s.md) | [androidJvm]<br>const val [FILE_OPERATION_STATUS](-f-i-l-e_-o-p-e-r-a-t-i-o-n_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 21<br>Indica o status de uma [ApiEndpoints.REQ_FILE_OPERATION](../-api-endpoints/-companion/-r-e-q_-f-i-l-e_-o-p-e-r-a-t-i-o-n.md) iniciada anteriormente. |
| [FORM_DELETED](-f-o-r-m_-d-e-l-e-t-e-d.md) | [androidJvm]<br>const val [FORM_DELETED](-f-o-r-m_-d-e-l-e-t-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 4<br>Um formulário foi removido. |
| [FORM_RECEIVED](-f-o-r-m_-r-e-c-e-i-v-e-d.md) | [androidJvm]<br>const val [FORM_RECEIVED](-f-o-r-m_-r-e-c-e-i-v-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3<br>Um formulário foi recebido ou atualizado. |
| [IGNITION_STATUS](-i-g-n-i-t-i-o-n_-s-t-a-t-u-s.md) | [androidJvm]<br>const val [IGNITION_STATUS](-i-g-n-i-t-i-o-n_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 24<br>Indica o status da ignição do dispositivo externo. |
| [MCT_M0_M9_PARAMS_UPDATED](-m-c-t_-m0_-m9_-p-a-r-a-m-s_-u-p-d-a-t-e-d.md) | [androidJvm]<br>const val [MCT_M0_M9_PARAMS_UPDATED](-m-c-t_-m0_-m9_-p-a-r-a-m-s_-u-p-d-a-t-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 15<br>Informa que os parâmetros do Mct (M0..M9) foram atualizados no banco de dados e traz o valor de dois parâmetros M. |
| [MESSAGE_STATUS](-m-e-s-s-a-g-e_-s-t-a-t-u-s.md) | [androidJvm]<br>const val [MESSAGE_STATUS](-m-e-s-s-a-g-e_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 22<br>Indica o status de uma mensagem no banco de dados. Esta ação pode ser utilizada quando uma nova mensagem for recebida ou quando uma mensagem que foi postada para envio acabou de ser enviada, assim como outros status. Uma mensagem não lida recebe o status [MessageStatusValues.NOT_READ](-message-status-values/-n-o-t_-r-e-a-d.md). Uma mensagem que foi enviada recebe o status [MessageStatusValues.SENT](-message-status-values/-s-e-n-t.md). |
| [NETWORK_CONNECTION_STATUS](-n-e-t-w-o-r-k_-c-o-n-n-e-c-t-i-o-n_-s-t-a-t-u-s.md) | [androidJvm]<br>const val [NETWORK_CONNECTION_STATUS](-n-e-t-w-o-r-k_-c-o-n-n-e-c-t-i-o-n_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 9<br>Status das conexões de rede. |
| [READY_TO_UPDATE_SOFTWARE](-r-e-a-d-y_-t-o_-u-p-d-a-t-e_-s-o-f-t-w-a-r-e.md) | [androidJvm]<br>const val [READY_TO_UPDATE_SOFTWARE](-r-e-a-d-y_-t-o_-u-p-d-a-t-e_-s-o-f-t-w-a-r-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 8<br>Os arquivos de atualização foram baixados e estão prontos para iniciar a atualização. |
| [SATELLITE_SIGNAL_CHANGED](-s-a-t-e-l-l-i-t-e_-s-i-g-n-a-l_-c-h-a-n-g-e-d.md) | [androidJvm]<br>const val [SATELLITE_SIGNAL_CHANGED](-s-a-t-e-l-l-i-t-e_-s-i-g-n-a-l_-c-h-a-n-g-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 11<br>Mudanca no sinal do satélite. |
| [SIGN_ON_STATUS](-s-i-g-n_-o-n_-s-t-a-t-u-s.md) | [androidJvm]<br>const val [SIGN_ON_STATUS](-s-i-g-n_-o-n_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 5<br>A unidade se conectou ao servidor AMH. |
| [SYSTEM_RESOURCE_REQ_STATUS](-s-y-s-t-e-m_-r-e-s-o-u-r-c-e_-r-e-q_-s-t-a-t-u-s.md) | [androidJvm]<br>const val [SYSTEM_RESOURCE_REQ_STATUS](-s-y-s-t-e-m_-r-e-s-o-u-r-c-e_-r-e-q_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 23<br>Indica o status de requisição de algum recurso do sistema. Esta ação pode ser utilizada para informar status de requisições de recursos do sistema, mas geralmente é utilizada para informar quando algum recurso do sistema que é necessario ao bom funcionamento da aplicação não pode ser obtido. |
