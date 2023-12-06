//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ApiEndpoints](index.md)/[REQ_POSITION_HISTORY_LIST](-r-e-q_-p-o-s-i-t-i-o-n_-h-i-s-t-o-r-y_-l-i-s-t.md)

# REQ_POSITION_HISTORY_LIST

[androidJvm]\
const val [REQ_POSITION_HISTORY_LIST](-r-e-q_-p-o-s-i-t-i-o-n_-h-i-s-t-o-r-y_-l-i-s-t.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Lista todas as posições do histórico de acordo com o filtro especificado.

**Atenção**: A lista de posições do histórico se baseia em mensagens da caixa de saída, portanto somente retornam posições especificadas com status de enviada ([ActionValues.MessageStatusValues.SENT](../-action-values/-message-status-values/-s-e-n-t.md)) ou a enviar ([ActionValues.MessageStatusValues.TO_SEND](../-action-values/-message-status-values/-t-o_-s-e-n-d.md)) no param2 desta requisição.

#### Parameters

androidJvm

| | |
|---|---|
| param1 | (posCode) - código da posição a ser retornada. Se este parâmetro for igual a 0, ele será ignorado. Se for diferente de 0, os demais filtros serão ignorados e somente a mensagem com o código especificado será retornada. |
| param2 | (msgStatusNum) - status das mensagens a serem filtradas. Este parâmetro só é considerado se param1 (posCode) for igual a 0. |

#### See also

| |
|---|
| [ActionValues.MessageStatusValues](../-action-values/-message-status-values/index.md) |
