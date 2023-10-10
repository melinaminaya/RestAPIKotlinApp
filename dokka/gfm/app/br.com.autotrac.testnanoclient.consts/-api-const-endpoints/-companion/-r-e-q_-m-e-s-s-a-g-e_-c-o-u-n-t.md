//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ApiConstEndpoints](../index.md)/[Companion](index.md)/[REQ_MESSAGE_COUNT](-r-e-q_-m-e-s-s-a-g-e_-c-o-u-n-t.md)

# REQ_MESSAGE_COUNT

[androidJvm]\
const val [REQ_MESSAGE_COUNT](-r-e-q_-m-e-s-s-a-g-e_-c-o-u-n-t.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Contabiliza a quantidade de mensagens no banco de dados de acordo com o filtro especificado.

Pode ser usado, por exemplo, para contabilizar a quantidade de mensagens a enviar. Neste caso o parâmetro isForward deve ser true e msgStatusNum deve ser [ActionValues.MessageStatusValues.TO_SEND](../../-action-values/-message-status-values/-t-o_-s-e-n-d.md). Ele deve ser preferido ao inves do metodo messageList(long, boolean, int) por motivos de performance. Muitas vezes mais rapido do que o metodo messageList(long, boolean, int).

## See also

androidJvm

| | |
|---|---|
| [br.com.autotrac.testnanoclient.consts.ActionValues.MessageStatusValues](../../-action-values/-message-status-values/index.md) |  |
