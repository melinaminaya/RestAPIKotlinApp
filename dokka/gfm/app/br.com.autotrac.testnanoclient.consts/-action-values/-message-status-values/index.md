//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ActionValues](../index.md)/[MessageStatusValues](index.md)

# MessageStatusValues

[androidJvm]\
object [MessageStatusValues](index.md)

Valores dos status de mensagens.

## Properties

| Name | Summary |
|---|---|
| [MESSAGE_DUPLICATE](-m-e-s-s-a-g-e_-d-u-p-l-i-c-a-t-e.md) | [androidJvm]<br>const val [MESSAGE_DUPLICATE](-m-e-s-s-a-g-e_-d-u-p-l-i-c-a-t-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Mensagem duplicada/descartada. |
| [MESSAGE_TOO_BIG](-m-e-s-s-a-g-e_-t-o-o_-b-i-g.md) | [androidJvm]<br>const val [MESSAGE_TOO_BIG](-m-e-s-s-a-g-e_-t-o-o_-b-i-g.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Mensagem muito grande para ser transmitida para o receptor. **Somente para mensagens de Envio.** |
| [NONE](-n-o-n-e.md) | [androidJvm]<br>const val [NONE](-n-o-n-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>Nenhum status associado. |
| [NOT_PROCESSED](-n-o-t_-p-r-o-c-e-s-s-e-d.md) | [androidJvm]<br>const val [NOT_PROCESSED](-n-o-t_-p-r-o-c-e-s-s-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 5<br>Não processada. **Somente para mensagens de Envio.** |
| [NOT_READ](-n-o-t_-r-e-a-d.md) | [androidJvm]<br>const val [NOT_READ](-n-o-t_-r-e-a-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3<br>Mensagem recebida não lida. **Somente para mensagens de retorno.** |
| [NOT_READ_OUT_OF_BAND](-n-o-t_-r-e-a-d_-o-u-t_-o-f_-b-a-n-d.md) | [androidJvm]<br>const val [NOT_READ_OUT_OF_BAND](-n-o-t_-r-e-a-d_-o-u-t_-o-f_-b-a-n-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 7<br>Mensagem longa (Out Of Band) recebida e não processada (seus arquivos ainda não foram baixados). Será processada e marcada como [MessageStatusValues.NOT_READ](-n-o-t_-r-e-a-d.md) quando os arquivos forem baixados do servidor. **Somente para mensagens de Retorno.** |
| [OUT_OF_BAND_FILE_NOT_FOUND](-o-u-t_-o-f_-b-a-n-d_-f-i-l-e_-n-o-t_-f-o-u-n-d.md) | [androidJvm]<br>const val [OUT_OF_BAND_FILE_NOT_FOUND](-o-u-t_-o-f_-b-a-n-d_-f-i-l-e_-n-o-t_-f-o-u-n-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Mensagem OutOfBand: Arquivo não encontrado. |
| [READ](-r-e-a-d.md) | [androidJvm]<br>const val [READ](-r-e-a-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 4<br>Mensagem recebida lida. **Somente para mensagens de retorno.** |
| [SENT](-s-e-n-t.md) | [androidJvm]<br>const val [SENT](-s-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 2<br>Enviada. **Somente para mensagens de Envio.** |
| [SERIAL_MESSAGE_CELL_NET_UNAVAILABLE](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-c-e-l-l_-n-e-t_-u-n-a-v-a-i-l-a-b-l-e.md) | [androidJvm]<br>const val [SERIAL_MESSAGE_CELL_NET_UNAVAILABLE](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-c-e-l-l_-n-e-t_-u-n-a-v-a-i-l-a-b-l-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1001<br>Sinaliza que uma mensagem do tipo Serial está bloqueando a fila de mensagens porque o tipo de rede escolhido para o envio da mensagem não esta disponivel. |
| [SERIAL_MESSAGE_TOO_BIG_WAITING](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-t-o-o_-b-i-g_-w-a-i-t-i-n-g.md) | [androidJvm]<br>const val [SERIAL_MESSAGE_TOO_BIG_WAITING](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-t-o-o_-b-i-g_-w-a-i-t-i-n-g.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1000<br>Sinaliza que uma mensagem do tipo Serial está bloqueando a fila de mensagens por ser muito grande para a rede satelital. |
| [TO_SEND](-t-o_-s-e-n-d.md) | [androidJvm]<br>const val [TO_SEND](-t-o_-s-e-n-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1<br>A enviar. **Somente para mensagens de Envio.** |
| [TRANSMITTED](-t-r-a-n-s-m-i-t-t-e-d.md) | [androidJvm]<br>const val [TRANSMITTED](-t-r-a-n-s-m-i-t-t-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 6<br>Transmitida. Uma mensagem foi transmitida pela rede satelital. Depois da confirmação de entrega pelo servidor, ela  recebe status de enviada. **Somente para mensagens de Envio.** |
| [TRANSMITTING](-t-r-a-n-s-m-i-t-t-i-n-g.md) | [androidJvm]<br>const val [TRANSMITTING](-t-r-a-n-s-m-i-t-t-i-n-g.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 8<br>Em transmissão. A mensagem se encontra em transmissão pela rede satelital, seu envio se encontra em progresso, a mensagem ainda não foi completamente transmitida pelo dispositivo. Quando a mensagem for transmitida, ela recebe o status de [MessageStatusValues.TRANSMITTED](-t-r-a-n-s-m-i-t-t-e-d.md). **Somente para mensagens de Envio.** |
| [UNKNOWN_ERROR](-u-n-k-n-o-w-n_-e-r-r-o-r.md) | [androidJvm]<br>const val [UNKNOWN_ERROR](-u-n-k-n-o-w-n_-e-r-r-o-r.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Erro desconhecido. |
