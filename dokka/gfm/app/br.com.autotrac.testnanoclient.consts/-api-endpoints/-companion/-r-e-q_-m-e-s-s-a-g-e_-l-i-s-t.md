//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ApiEndpoints](../index.md)/[Companion](index.md)/[REQ_MESSAGE_LIST](-r-e-q_-m-e-s-s-a-g-e_-l-i-s-t.md)

# REQ_MESSAGE_LIST

[androidJvm]\
const val [REQ_MESSAGE_LIST](-r-e-q_-m-e-s-s-a-g-e_-l-i-s-t.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Endpoint Específico de requisição de lista de mensagens de acordo com filtro especificado. Filtro contém:

#### Parameters

androidJvm

| | |
|---|---|
| param1 | [br.com.autotrac.testnanoclient.dataRemote.IntegrationMessage.code](../../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/code.md) - indica se as mensagens de envio ou retorno devem ser filtradas.<br>Se este parâmetro for igual a 0, ele será ignorado. Se for diferente de 0, os demais filtros serão ignorados e somente a mensagem com o código especificado será retornada. |
| param2 | [br.com.autotrac.testnanoclient.dataRemote.IntegrationMessage.isForward](../../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/is-forward.md) - indica se as mensagens de envio ou retorno devem ser filtradas.<br>Se true indica que as mensagens de envio serão filtradas. Se false as mensagens de retorno serão filtradas. Este parâmetro só é considerado se p_msgCode for igual a 0. |
| param3 | [br.com.autotrac.testnanoclient.dataRemote.IntegrationMessage.msgStatusNum](../../../br.com.autotrac.testnanoclient.dataRemote/-integration-message/msg-status-num.md) - status das mensagens a serem filtradas. |

#### See also

| | |
|---|---|
| [ActionValues.MessageStatusValues](../../-action-values/-message-status-values/index.md) | Este parâmetro só é considerado se p_msgCode for igual a 0. |
