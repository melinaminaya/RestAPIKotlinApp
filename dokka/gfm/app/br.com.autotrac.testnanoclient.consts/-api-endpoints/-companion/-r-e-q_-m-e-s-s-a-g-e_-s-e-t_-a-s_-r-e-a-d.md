//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ApiEndpoints](../index.md)/[Companion](index.md)/[REQ_MESSAGE_SET_AS_READ](-r-e-q_-m-e-s-s-a-g-e_-s-e-t_-a-s_-r-e-a-d.md)

# REQ_MESSAGE_SET_AS_READ

[androidJvm]\
const val [REQ_MESSAGE_SET_AS_READ](-r-e-q_-m-e-s-s-a-g-e_-s-e-t_-a-s_-r-e-a-d.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Marca uma mensagem com o status de &quot;lida&quot;.

O status da mensagem recebe [ActionValues.MessageStatusValues.READ](../../-action-values/-message-status-values/-r-e-a-d.md) e se a mensagem exigir confirmação de leitura, a confirmação será gerada e enviada ao servidor.

#### Parameters

androidJvm

| | |
|---|---|
| param1 | (msgCode) - código da mensagem que deve ser marcada como lida. |
