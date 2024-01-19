//[app](../../../index.md)/[br.com.autotrac.testnanoclient.consts](../index.md)/[ApiEndpoints](index.md)/[REQ_FILE_OPERATION](-r-e-q_-f-i-l-e_-o-p-e-r-a-t-i-o-n.md)

# REQ_FILE_OPERATION

[androidJvm]\
const val [REQ_FILE_OPERATION](-r-e-q_-f-i-l-e_-o-p-e-r-a-t-i-o-n.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Copia os arquivos para o diretório especificado.

Exemplo de requisição HTTP para copiar todos os logs para um diretório específico: /REQ_FILE_OPERATION,{param3=/storage/emulated/0/Download/LogA/, param4=[], param1=7, param2=1}

**Atenção**: Ao indicar o diretório final, utilizar a &quot;/&quot; para finalizar.

Exemplo de requisição Websocket para zipar todos os arquivos em um arquivo com nome específico: {&quot;param1&quot;:&quot;REQ_FILE_OPERATION&quot;,&quot;param2&quot;:{&quot;param1&quot;:7,&quot;param2&quot;:1,&quot;param3&quot;:&quot;/storage/emulated/0/Download/AutotracA/&quot;,&quot;param4&quot;:0}}

#### Parameters

androidJvm

| | |
|---|---|
| param1 | (files) - indica quais arquivos devem ser envolvidos na operação. Este parâmetro é um mapa de bits. Mais de um arquivo pode ser especificado utilizando o operador OU bit a bit (&quot;|&quot;). |
| param2 | (options) - opções a serem utilizadas na operação. |
| param3 | (destination) - no caso da opção [ActionValues.FileOperationOptions.COPY_FILES](../-action-values/-file-operation-options/-c-o-p-y_-f-i-l-e-s.md), é o diretório de destino para os arquivos. E no caso [ActionValues.FileOperationOptions.ZIP_COMPRESSION](../-action-values/-file-operation-options/-z-i-p_-c-o-m-p-r-e-s-s-i-o-n.md), pode ser um diretório ou o nome do arquivo de zip. |
| param4 | (timeoutMs) - indica quanto tempo, em ms, que o método deve aguardar até o fim da operação. Se este parâmetro for diferente de 0, o método só retornará quando a operação for concluída ou o timeout expirar. Se este parâmetro for 0, o método retorna imediatamente e o cliente deve aguardar o evento [ActionValues.FILE_OPERATION_STATUS](../-action-values/-f-i-l-e_-o-p-e-r-a-t-i-o-n_-s-t-a-t-u-s.md) para determinar o status da operação. Se o timeout se esgotar antes do fim da operação, uma exceção é gerada, mas mesmo assim, quando a operação for completada, o evento [ActionValues.FILE_OPERATION_STATUS](../-action-values/-f-i-l-e_-o-p-e-r-a-t-i-o-n_-s-t-a-t-u-s.md) será gerado. |

#### See also

| |
|---|
| [ActionValues.FileOperationFiles](../-action-values/-file-operation-files/index.md) |
| [ActionValues.FileOperationOptions](../-action-values/-file-operation-options/index.md) |
