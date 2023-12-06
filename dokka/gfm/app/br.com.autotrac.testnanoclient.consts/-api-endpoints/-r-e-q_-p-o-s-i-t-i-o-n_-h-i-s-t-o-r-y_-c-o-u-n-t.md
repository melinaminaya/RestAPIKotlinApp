//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ApiEndpoints](index.md)/[REQ_POSITION_HISTORY_COUNT](-r-e-q_-p-o-s-i-t-i-o-n_-h-i-s-t-o-r-y_-c-o-u-n-t.md)

# REQ_POSITION_HISTORY_COUNT

[androidJvm]\
const val [REQ_POSITION_HISTORY_COUNT](-r-e-q_-p-o-s-i-t-i-o-n_-h-i-s-t-o-r-y_-c-o-u-n-t.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Contabiliza a quantidade de posições do histórico do banco de dados de acordo com o filtro especificado. Este método pode ser usado, por exemplo, para contabilizar a quantidade de posições a enviar. Neste caso o parâmetro msgStatusNum deve ser [ActionValues.MessageStatusValues.TO_SEND](../-action-values/-message-status-values/-t-o_-s-e-n-d.md). Ele deve ser preferido ao método positionHistoryList(long, int) devido ao seu desempenho superior, demonstrando uma velocidade consideravelmente maior.

#### Parameters

androidJvm

| | |
|---|---|
| param1 | (msgStatusNum) - status das mensagens a serem filtradas. |

#### See also

| |
|---|
| [ActionValues.MessageStatusValues](../-action-values/-message-status-values/index.md) |
