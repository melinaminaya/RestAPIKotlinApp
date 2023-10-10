//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ActionValues](../index.md)/[MessageStatusValues](index.md)/[NOT_READ_OUT_OF_BAND](-n-o-t_-r-e-a-d_-o-u-t_-o-f_-b-a-n-d.md)

# NOT_READ_OUT_OF_BAND

[androidJvm]\
const val [NOT_READ_OUT_OF_BAND](-n-o-t_-r-e-a-d_-o-u-t_-o-f_-b-a-n-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 7

Mensagem longa (Out Of Band) recebida e não processada (seus arquivos ainda não foram baixados). Será processada e marcada como [MessageStatusValues.NOT_READ](-n-o-t_-r-e-a-d.md) quando os arquivos forem baixados do servidor. **Somente para mensagens de Retorno.**
