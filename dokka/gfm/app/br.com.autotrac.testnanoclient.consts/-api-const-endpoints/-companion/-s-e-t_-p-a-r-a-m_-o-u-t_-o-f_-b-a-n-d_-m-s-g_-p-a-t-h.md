//[app](../../../../index.md)/[br.com.autotrac.testnanoclient.consts](../../index.md)/[ApiConstEndpoints](../index.md)/[Companion](index.md)/[SET_PARAM_OUT_OF_BAND_MSG_PATH](-s-e-t_-p-a-r-a-m_-o-u-t_-o-f_-b-a-n-d_-m-s-g_-p-a-t-h.md)

# SET_PARAM_OUT_OF_BAND_MSG_PATH

[androidJvm]\
const val [SET_PARAM_OUT_OF_BAND_MSG_PATH](-s-e-t_-p-a-r-a-m_-o-u-t_-o-f_-b-a-n-d_-m-s-g_-p-a-t-h.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Endpoint específico para alterar o diretório de armazenamento dos arquivos de mensagens longas (Out Of Band). Os arquivos enviados estarão armazenados dentro de um subdiretório chamado "Sent" (PARAM_OUT_OF_BAND_MSG_PATH/Sent/). Os arquivos baixados estarão armazenados dentro de um subdiretório chamado "Received" (PARAM_OUT_OF_BAND_MSG_PATH/Received/). Para cada mensagem haverá ainda um subdiretório dentro deste, cujo nome será o código da mensagem no banco de dados onde todos os arquivos referentes à mensagem estarão armazenados. Ex.: "PARAM_OUT_OF_BAND_MSG_PATH/Received/00055/". Este parâmetro somente deve conter o diretório base, os subdiretórios (Sent, Received, etc.) serão criados automaticamente. **ATENÇÃO: É importante que este diretório esteja acessível por qualquer aplicação e independente de permissão específica para acesso.**
