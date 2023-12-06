//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ApiEndpoints](index.md)/[REQ_CONFIG_SERVICE_LOG](-r-e-q_-c-o-n-f-i-g_-s-e-r-v-i-c-e_-l-o-g.md)

# REQ_CONFIG_SERVICE_LOG

[androidJvm]\
const val [REQ_CONFIG_SERVICE_LOG](-r-e-q_-c-o-n-f-i-g_-s-e-r-v-i-c-e_-l-o-g.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Configura a geração do log em arquivo para o serviço. Este método configura a geração de log para o serviço de comunicação (não para a API).

#### Parameters

androidJvm

| | |
|---|---|
| param1 | (enable) - boolean informa se o log deve ser ativado/desativado. |
| param2 | (maxFileCount) - é a quantidade máxima de arquivos de logs a serem gerados. Quando a quantidade máxima é atingida, o arquivo mais antigo é sobrescrito. |
| param3 | (maxFileSize) - é o tamanho máximo de cada arquivo de log gerado. Ex.: 3*1024*1024 (3MB). |
