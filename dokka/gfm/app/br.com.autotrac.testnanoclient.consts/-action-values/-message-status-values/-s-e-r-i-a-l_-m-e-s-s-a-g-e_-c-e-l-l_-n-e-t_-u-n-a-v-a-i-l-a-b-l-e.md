//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ActionValues](../index.md)/[MessageStatusValues](index.md)/[SERIAL_MESSAGE_CELL_NET_UNAVAILABLE](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-c-e-l-l_-n-e-t_-u-n-a-v-a-i-l-a-b-l-e.md)

# SERIAL_MESSAGE_CELL_NET_UNAVAILABLE

[androidJvm]\
const val [SERIAL_MESSAGE_CELL_NET_UNAVAILABLE](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-c-e-l-l_-n-e-t_-u-n-a-v-a-i-l-a-b-l-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1001

Sinaliza que uma mensagem do tipo Serial está bloqueando a fila de mensagens porque o tipo de rede escolhido para o envio da mensagem não esta disponivel.

Uma mensagem do tipo Serial que possui o tipo de canal de transmissão igual a 'Cellular', somente será enviada quando houver rede celular disponivel.

**Status apenas informativo, não gravado no banco de dados.**
