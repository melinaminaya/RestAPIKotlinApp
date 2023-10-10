//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ActionValues](index.md)/[MESSAGE_STATUS](-m-e-s-s-a-g-e_-s-t-a-t-u-s.md)

# MESSAGE_STATUS

[androidJvm]\
const val [MESSAGE_STATUS](-m-e-s-s-a-g-e_-s-t-a-t-u-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 22

Indica o status de uma mensagem no banco de dados. Esta ação pode ser utilizada quando uma nova mensagem for recebida ou quando uma mensagem que foi postada para envio acabou de ser enviada, assim como outros status. Uma mensagem não lida recebe o status [MessageStatusValues.NOT_READ](-message-status-values/-n-o-t_-r-e-a-d.md). Uma mensagem que foi enviada recebe o status [MessageStatusValues.SENT](-message-status-values/-s-e-n-t.md).

#### Parameters

androidJvm

| | |
|---|---|
| Param1 | Código da mensagem no banco de dados. |
| Param2 | Status da mensagem.<br>Este campo deve ser utilizado para distinguir entre os diferentes status de uma mensagem. Ver [MessageStatusValues](-message-status-values/index.md).<br>Este campo deve ser tratado como um inteiro de 32bits com sinal (signed int). |
| Param3 | Não utilizado. |
| Param4 | Não utilizado. |
