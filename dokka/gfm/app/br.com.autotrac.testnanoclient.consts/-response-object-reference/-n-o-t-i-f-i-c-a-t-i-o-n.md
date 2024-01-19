//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ResponseObjectReference](index.md)/[NOTIFICATION](-n-o-t-i-f-i-c-a-t-i-o-n.md)

# NOTIFICATION

[androidJvm]\
const val [NOTIFICATION](-n-o-t-i-f-i-c-a-t-i-o-n.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Endpoint Geral de Configuração de Eventos (Ações/Notificações). Envia a lista de endpoint de notificações que desejam ser escutadas.

[ActionValues.MESSAGE_STATUS](../-action-values/-m-e-s-s-a-g-e_-s-t-a-t-u-s.md) : retorna [ActionValues.MessageStatusValues](../-action-values/-message-status-values/index.md)

[ActionValues.COMMUNICATION_MODE_CHANGED](../-action-values/-c-o-m-m-u-n-i-c-a-t-i-o-n_-m-o-d-e_-c-h-a-n-g-e-d.md) : [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesNetworkTypes](../-parameter-values/-values-network-types/index.md)

[ActionValues.FILE_OPERATION_STATUS](../-action-values/-f-i-l-e_-o-p-e-r-a-t-i-o-n_-s-t-a-t-u-s.md) : [ActionValues.ValuesFileOperationStatusParam1](../-action-values/-values-file-operation-status-param1/index.md)

[ActionValues.IGNITION_STATUS](../-action-values/-i-g-n-i-t-i-o-n_-s-t-a-t-u-s.md) : [ActionValues.ValuesIgnitionStatusParam1](../-action-values/-values-ignition-status-param1/index.md)

[ActionValues.NETWORK_CONNECTION_STATUS](../-action-values/-n-e-t-w-o-r-k_-c-o-n-n-e-c-t-i-o-n_-s-t-a-t-u-s.md) : [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesNetworkTypes](../-parameter-values/-values-network-types/index.md) e [br.com.autotrac.testnanoclient.consts.ParameterValues.ValuesConnectionStates](../-parameter-values/-values-connection-states/index.md)

[ActionValues.SYSTEM_RESOURCE_REQ_STATUS](../-action-values/-s-y-s-t-e-m_-r-e-s-o-u-r-c-e_-r-e-q_-s-t-a-t-u-s.md) : [ActionValues.ValuesSysResourceReqParam1](../-action-values/-values-sys-resource-req-param1/index.md) e [ActionValues.ValuesSysResourceStatusParam2](../-action-values/-values-sys-resource-status-param2/index.md)

[ActionValues.FORM_RECEIVED](../-action-values/-f-o-r-m_-r-e-c-e-i-v-e-d.md) e [ActionValues.FORM_DELETED](../-action-values/-f-o-r-m_-d-e-l-e-t-e-d.md) : [br.com.autotrac.testnanoclient.models.IntegrationForm.code](../../br.com.autotrac.testnanoclient.models/-integration-form/code.md)

Exemplo de requisição com Endpoint: {&quot;param1&quot;:&quot;NOTIFICATION&quot;,&quot;param2&quot;:22,7,10,18,21,24,15,9,8,11,5,23,4,3}

#### Return

Resposta do Servidor do tipo [br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse](../../br.com.autotrac.testnanoclient.requestObjects/-received-request-response/index.md).

#### Parameters

androidJvm

| | |
|---|---|
| param1 | Pode ser [NOTIFICATION](-n-o-t-i-f-i-c-a-t-i-o-n.md), [REQUEST](-r-e-q-u-e-s-t.md), [PARAMETER](-p-a-r-a-m-e-t-e-r.md), |

#### See also

| |
|---|
| [ReceivedRequestResponse](../../br.com.autotrac.testnanoclient.requestObjects/-received-request-response/index.md) |
