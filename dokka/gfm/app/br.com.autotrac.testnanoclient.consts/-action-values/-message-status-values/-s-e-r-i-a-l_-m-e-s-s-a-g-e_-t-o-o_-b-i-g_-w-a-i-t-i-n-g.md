//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ActionValues](../index.md)/[MessageStatusValues](index.md)/[SERIAL_MESSAGE_TOO_BIG_WAITING](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-t-o-o_-b-i-g_-w-a-i-t-i-n-g.md)

# SERIAL_MESSAGE_TOO_BIG_WAITING

[androidJvm]\
const val [SERIAL_MESSAGE_TOO_BIG_WAITING](-s-e-r-i-a-l_-m-e-s-s-a-g-e_-t-o-o_-b-i-g_-w-a-i-t-i-n-g.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1000

Sinaliza que uma mensagem do tipo Serial está bloqueando a fila de mensagens por ser muito grande para a rede satelital.

Uma mensagem do tipo Serial pode ser muito grande para ser enviada via satélite, mas pode ser enviada via celular (quando configurada para ser enviada por qualquer rede disponivel). No entanto, caso não haja rede celular disponivel no momento para envio, a mensagem bloqueia a fila. A fila retorna ao normal quando a rede celular estiver disponivel novamente.

**Status apenas informativo, não gravado no banco de dados.**
