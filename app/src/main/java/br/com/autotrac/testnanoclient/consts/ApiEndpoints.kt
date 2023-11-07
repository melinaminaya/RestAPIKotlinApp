package br.com.autotrac.testnanoclient.consts

/**
 * Classe de constantes a serem empregadas na comunicação via integração NanoWebsocket e Http.
 * Refere-se aos endpoints e demais ajustes.
 * @author Melina Minaya
 */
class ApiEndpoints {
    companion object {
        //Lista da Integração referente à requisições

        /** Endpoint de envio de mensagens para o servidor.*/
        const val SEND_MESSAGE = "SEND_MESSAGE"

        /** Endpoint para envio de mensagem com arquivo. */
        const val SEND_FILE_MESSAGE = "SEND_FILE_MESSAGE"

        /**
         * Endpoint Geral de Eventos (Ações/Notificações).
         *
         * Exemplo de requisição com Endpoint: {"param1":"NOTIFICATION","param2":[22,7,10,18,21,24,15,9,8,11,5,23,4,3]}
         *
         * @return Resposta do Servidor do tipo [br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse].
         *
         * @param param1 Pode ser [NOTIFICATION], [REQUEST], [PARAMETER],

         * @see br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
         */
        const val NOTIFICATION = "NOTIFICATION"

        /**
         * Endpoint Geral de Requisição.
         *
         * Exemplo de requisição com Endpoint: {"param1":"REQUEST","param2":"REQ_MESSAGE_COUNT","param3":{"param1":"false","param2":"3"},"param4":0}
         *
         * @return Resposta do Servidor do tipo [br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse].
         * @see br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
         */
        const val REQUEST = "REQUEST"

        /**
         * Endpoint Geral de Requisição de Parâmetros.
         *
         * Exemplo de requisição com Endpoint: {"param1":"PARAMETER","param2":"SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S","param3":{"param1":"10"}}
         *
         *  @return Resposta do Servidor do tipo [br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse].
         *  @see br.com.autotrac.testnanoclient.requestObjects.ReceivedRequestResponse
         */
        const val PARAMETER = "PARAMETER"

        //Endpoints Específicos
        /**
         * Endpoint Específico de requisição de lista de mensagens de acordo com filtro especificado.
         * Filtro contém:
         * @param param1 [br.com.autotrac.testnanoclient.models.IntegrationMessage.code] - indica se as mensagens de envio ou retorno devem ser filtradas.
         *
         * Se este parâmetro for igual a 0, ele será ignorado. Se for diferente de 0, os demais filtros serão ignorados e somente a mensagem com o código especificado será retornada.
         * @param param2 [br.com.autotrac.testnanoclient.models.IntegrationMessage.isForward] - indica se as mensagens de envio ou retorno devem ser filtradas.
         *
         * Se true indica que as mensagens de envio serão filtradas. Se false as mensagens de retorno serão filtradas. Este parâmetro só é considerado se p_msgCode for igual a 0.
         * @param param3 [br.com.autotrac.testnanoclient.models.IntegrationMessage.msgStatusNum] - status das mensagens a serem filtradas.
         * @see [ActionValues.MessageStatusValues]
         *
         * Este parâmetro só é considerado se p_msgCode for igual a 0.
         */
        const val REQ_MESSAGE_LIST = "REQ_MESSAGE_LIST"

        //Constante interna deste app.
        const val REQ_MESSAGE_LIST_INBOX = "REQ_MESSAGE_LIST_INBOX"
        //Constante interna deste app.
        const val REQ_MESSAGE_LIST_OUTBOX = "REQ_MESSAGE_LIST_OUTBOX"

        /**
         * Contabiliza a quantidade de mensagens no banco de dados de acordo com o filtro especificado.
         *
         * Pode ser usado, por exemplo, para contabilizar a quantidade de mensagens a enviar.
         * Neste caso o parâmetro isForward deve ser true e msgStatusNum deve ser [ActionValues.MessageStatusValues.TO_SEND].
         * Ele deve ser preferido ao inves do metodo messageList(long, boolean, int) por motivos de performance.
         * Muitas vezes mais rapido do que o metodo messageList(long, boolean, int).
         *
         * @param param1 (isForward) - indica se as mensagens de envio ou retorno devem ser filtradas.
         * Se true filtra as mensagens de envio. Se false filtra as mensagens de retorno.
         * @param param2 (msgStatusNum) - status das mensagens a serem filtradas.
         * @see ActionValues.MessageStatusValues
         */
        const val REQ_MESSAGE_COUNT = "REQ_MESSAGE_COUNT"

        /**
         * Apaga uma mensagem (ou todas as mensagens) do banco de dados do serviço.
         * @param param1 (msgCode) - código da mensagem a ser apagada.
         *
         * **Atenção**: Se o código da mensagem for 0, apagam-se todas as mensagens!
         */
        const val REQ_MESSAGE_DELETE = "REQ_MESSAGE_DELETE"

        /**
         * Marca uma mensagem com o status de "lida".
         *
         * O status da mensagem recebe [ActionValues.MessageStatusValues.READ] e
         * se a mensagem exigir confirmação de leitura, a confirmação será gerada e enviada ao servidor.
         * @param param1 (msgCode) - código da mensagem que deve ser marcada como lida.
         */
        const val REQ_MESSAGE_SET_AS_READ = "REQ_MESSAGE_SET_AS_READ"

        /**
         * Configura a geração do log em arquivo para o serviço.
         * Este método configura a geração de log para o serviço de comunicação (não para a API).
         * @param param1 (enable) - boolean informa se o log deve ser ativado/desativado.
         * @param param2 (maxFileCount) - é a quantidade máxima de arquivos de logs a serem gerados.
         * Quando a quantidade máxima é atingida, o arquivo mais antigo é sobrescrito.
         * @param param3 (maxFileSize) - é o tamanho máximo de cada arquivo de log gerado. Ex.: 3*1024*1024 (3MB).
         */
        const val REQ_CONFIG_SERVICE_LOG = "REQ_CONFIG_SERVICE_LOG"

        /**
         * Copia os arquivos para o diretório especificado.
         *
         * Exemplo de requisição HTTP para copiar todos os logs para um diretório específico:
         * /REQ_FILE_OPERATION,{param3=[/storage/emulated/0/Download/LogA/], param4=[], param1=[7], param2=[1]}
         *
         * **Atenção**: Ao indicar o diretório final, utilizar a "/" para finalizar.
         *
         * Exemplo de requisição Websocket para zipar todos os arquivos em um arquivo com nome específico:
         * {"param1":"REQ_FILE_OPERATION","param2":{"param1":7,"param2":1,"param3":"/storage/emulated/0/Download/AutotracA/","param4":0}}
         *
         * @param param1 (files) - indica quais arquivos devem ser envolvidos na operação.
         * Este parâmetro é um mapa de bits. Mais de um arquivo pode ser especificado utilizando o operador OU bit a bit ("|").
         * @see ActionValues.FileOperationFiles
         * @param param2 (options) - opções a serem utilizadas na operação.
         * @see ActionValues.FileOperationOptions
         * @param param3 (destination) - no caso da opção [ActionValues.FileOperationOptions.COPY_FILES],
         * é o diretório de destino para os arquivos. E no caso [ActionValues.FileOperationOptions.ZIP_COMPRESSION],
         * pode ser um diretório ou o nome do arquivo de zip.
         * @param param4 (timeoutMs) - indica quanto tempo, em ms, que o método deve aguardar até o fim da operação.
         * Se este parâmetro for diferente de 0, o método só retornará quando a operação for concluída ou o timeout expirar.
         * Se este parâmetro for 0, o método retorna imediatamente e o cliente deve aguardar o evento
         * [ActionValues.FILE_OPERATION_STATUS] para determinar o status da operação.
         * Se o timeout se esgotar antes do fim da operação, uma exceção é gerada, mas mesmo assim, quando a operação for completada,
         * o evento [ActionValues.FILE_OPERATION_STATUS] será gerado.
         */
        const val REQ_FILE_OPERATION = "REQ_FILE_OPERATION"

        /**
         * Lista os formulários ou grupos de formulários de acordo com o filtro especificado
         * (Este método só terá aplicabilidade para produtos que possuem comunicação celular).
         * @param param1 (groupOnly) - se for true, indica que a consulta deve ser realizada nos grupos de formulário.
         * Neste caso, apenas o parâmetro formGroupCode é considerado.
         * Se for false, a consulta será realizada nos formulários.
         * @param param2 (formGroupCode) - indica qual grupo de formulário deve ser filtrado.
         * Somente os formulários pertencentes a este grupo serão retornados.
         * Se este parâmetro for igual a 0, o filtro será desabilitado.
         * @param param3 (formType) - é o tipo de formulário a ser filtrado.
         * Somente os formulários do tipo especificado serão retornados.
         * @see ActionValues.FormTypeValues
         */
        const val REQ_FORM_LIST = "REQ_FORM_LIST"

        /**
         * Endpoint Específico.
         *
         * Realiza as consultas necessárias para a realização do checkList.
         * @return Retorna um objeto do tipo [br.com.autotrac.testnanoclient.models.CheckList]
         * Com valores a seguir:
         *
         * Cellular Status - não implementado
         *
         * [REQ_CELL_SIGNAL]
         *
         * [GET_PARAM_HAS_SATELLITE_SIGNAL]
         *
         * Wifi Status - não implementado
         *
         * [REQ_WIFI_SIGNAL]
         *
         * [REQ_GET_POSITION_LAST]
         *
         *[GET_PARAM_SERVICE_VERSION]
         *
         * [GET_PARAM_USER_INTERFACE_VERSION]
         *
         * Database Version
         *
         * SO System Version
         */
        const val REQ_GET_CHECKLIST = "REQ_GET_CHECKLIST"

        /**
         * Endpoint específico de requisição de sinal celular.
         * @return porcentagem de sinal celular.
         */
        const val REQ_CELL_SIGNAL = "REQ_CELL_SIGNAL"

        /**
         * Endpoint específico de requisição de sinal wifi.
         * @return porcentagem de sinal wifi.
         */
        const val REQ_WIFI_SIGNAL = "REQ_WIFI_SIGNAL"

        /**
         * Endpoint especíico de Data/Hora corrente utilizada pelo sistema.
         * O sistema pode utilizar uma Data/Hora diferente da configurada no dispositivo.
         * Esta Data/Hora deve ser utilizada em todas as operações (campo de mensagem, etc.) que exigem a Data/Hora atual.
         * @return Data/Hora utilizada pelo sistema.
         */
        const val REQ_GET_CURRENT_DATE = "REQ_GET_CURRENT_DATE"

        /**
         * Endpoint específico de requisição de parâmetros do MCT.
         * @return Lista de parâmetros no seguinte modelo: [br.com.autotrac.testnanoclient.models.ParameterModel].
         */
        const val REQ_GET_MCT_PARAMETERS = "REQ_GET_MCT_PARAMETERS"

        /**
         * Retorna informações detalhadas da posição mais recente disponível.
         * @param param1 (posSourceType) - fonte da posição cujos dados devem ser retornados.
         * @see ActionValues.PositionSourceType
         */
        const val REQ_GET_POSITION_LAST = "REQ_GET_POSITION_LAST"

        /**
         * Contabiliza a quantidade de posições do histórico do banco de dados de acordo com o filtro especificado.
         * Este método pode ser usado, por exemplo, para contabilizar a quantidade de posições a enviar.
         * Neste caso o parâmetro msgStatusNum deve ser [ActionValues.MessageStatusValues.TO_SEND].
         * Ele deve ser preferido ao método positionHistoryList(long, int) devido ao seu desempenho superior,
         * demonstrando uma velocidade consideravelmente maior.
         * @param param1 (msgStatusNum) - status das mensagens a serem filtradas.
         * @see ActionValues.MessageStatusValues
         * */
        const val REQ_POSITION_HISTORY_COUNT = "REQ_POSITION_HISTORY_COUNT"

        /**
         * Lista todas as posições do histórico de acordo com o filtro especificado.
         * @param param1 (posCode) - código da posição a ser retornada. Se este parâmetro for igual a 0, ele será ignorado.
         * Se for diferente de 0, os demais filtros serão ignorados e somente a mensagem com o código especificado será retornada.
         * @param param2 (msgStatusNum) - status das mensagens a serem filtradas.
         * @see ActionValues.MessageStatusValues
         * Este parâmetro só é considerado se [param1] (posCode) for igual a 0.
         * */
        const val REQ_POSITION_HISTORY_LIST = "REQ_POSITION_HISTORY_LIST"

        /**
         * Endpoint específico de requisição para resetar o banco de dados.
         * Faz a limpeza de todas as mensagens armazenadas no Banco de Dados.
         * Não retorna nenhuma informação, somente OK do servidor sinalizando que a requisição foi recebida.
         */
        const val REQ_RESET_DATABASE = "REQ_RESET_DATABASE"

        //Lista da Integração referente a parâmetros

        /**
         *Indica se a UC possui algum dispositivo de comunicação alternativo associado.
         * Quando a UC possui um dispositivo de comunicação alternativo associado a ela, diz-se que ele está batizado.
         * @return [ActionValues.ValuesBaptismStatusParam1]
         */
        const val GET_PARAM_IS_BAPTIZED = "GET_PARAM_IS_BAPTIZED"

        /**
         * Endpoint específico para obter o endereço IP da rede Wi-Fi do dispositivo móvel (Android).
         * SSID da rede WiFi utilizada para se conectar ao dispositivo de comunicação alternativa.
         * O SSID (Service Set Identifier) identifica o nome da rede WiFi utilizada para se conectar ao dispositivo de comunicação alternativo.
         * (O SSID somente pode ser configurado para produtos que possuem comunicação exclusiva satélite).
         * @see ParameterValues.CmuSubtypeValues.MOBILE_SATELLITE
         */
        const val GET_PARAM_WIFI_SSID = "GET_PARAM_WIFI_SSID"

        /**
         * Endpoint específico para realizar o batismo com o endereço IP da rede Wi-Fi do dispositivo móvel (Android).
         *
         * Para sistemas que utilizam puramente o dispositivo de comunicação alternativa,
         * se for configurado um SSID, a associação com o dispositivo ocorre automaticamente em seguida.
         *
         * Caso o SSID fornecido seja vazio ("") e o sistema esteja associado a um dispositivo de comunicação alternativa,
         * a associação será desfeita.
         */
        const val SET_PARAM_WIFI_SSID = "SET_PARAM_WIFI_SSID"

        /**
         * Retorna o número da conta (também chamada de UC Fixa) em que a unidade móvel está contida.
         * (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
         *
         * O endereço de uma UC Fixa refere-se a um número único que identifica a unidade Fixa dentro do sistema de comunicação.
         */
        const val GET_PARAM_ACCOUNT_NUMBER = "GET_PARAM_ACCOUNT_NUMBER"

        /**
         * Intervalo, em segundos, entre comunicações com o dispositivo de comunicação alternativo.
         * Refere-se ao intervalo entre verificações de novas mensagens junto ao dispositivo de comunicação alternativo.
         */
        const val GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S = "GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S"

        /**
         * Enpoint específico para alterar o intervalo entre verificações de novas mensagens junto ao dispositivo de comunicação alternativo.
         *  O intervalo recomendado é 10 segundos.
         */
        const val SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S = "SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S"

        /**
         * Versão de firmware do dispositivo de comunicação alternativo, por exemplo: MCT, ACU2, Iridium, etc
         */
        const val GET_PARAM_ALT_COMM_FIRMWARE_VERSION = "GET_PARAM_ALT_COMM_FIRMWARE_VERSION"

        /**
         * Endereço do dispositivo de comunicação alternativo.
         * O dispositivo de comunicação alternativo geralmente é um dispositivo de comunicação satelital.
         */
        const val GET_PARAM_ALTERNATIVE_COMM_DEVICE_ADDRESS = "GET_PARAM_ALTERNATIVE_COMM_DEVICE_ADDRESS"

        /**
         * Endereço IP da rede Celular do dispositivo.
         * (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
         * Somente válido enquanto o dispositivo está conectado na rede celular.
         */
        const val GET_PARAM_CELL_IP_ADDRESS = "GET_PARAM_CELL_IP_ADDRESS"

        /**
         * Nome do diretório usado pelo app do cliente para gravar os arquivos de log.
         */
        const val GET_PARAM_CLIENT_LOGS_DIRECTORY = "GET_PARAM_CLIENT_LOGS_DIRECTORY"

        /**
         * Identificação do modelo do aparelho móvel.
         */
        const val GET_PARAM_COMM_UNIT_DEVICE_TYPE = "GET_PARAM_COMM_UNIT_DEVICE_TYPE"

        /**
         * Modo de comunicação corrente.
         * @see ParameterValues.ValuesNetworkTypes
         */
        const val GET_PARAM_CURRENT_COMM_MODE = "GET_PARAM_CURRENT_COMM_MODE"

        /**
         * Tipo de comunicação (meio físico) a ser utilizado com o dispositivo externo.
         * @see ParameterValues.ExternalDeviceCommunicationTypeValues
         */
        const val GET_PARAM_EXT_DEV_COMM_TYPE = "GET_PARAM_EXT_DEV_COMM_TYPE"

        /**
         * Enpoint específico para alterar o parâmetro de tipo de comunicação.
         *
         *  Alterar para 2 (ancoragem) para realizar o batismo.
         *  @see ParameterValues.ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_HOTSPOT
         */
        const val SET_PARAM_EXT_DEV_COMM_TYPE = "SET_PARAM_EXT_DEV_COMM_TYPE"

        /**
         * Status do processo de upload dos arquivos de log via FTP.
         */
        const val GET_PARAM_FTP_LOGS_STATUS = "GET_PARAM_FTP_LOGS_STATUS"

        /**
         * Indica se existe sinal celular.
         * ** Aplicável apensas a produtos que possuem comunicação celular.**
         * O retorno baseia-se na troca de mensagens via rede celular nos últimos 5 minutos.
         * Em caso de troca de pacotes nos últimos 5 minutos, considera-se que existe sinal celular.
         */
        const val GET_PARAM_HAS_CELLULAR_SIGNAL = "GET_PARAM_HAS_CELLULAR_SIGNAL"

        /**
         * Indica se existe sinal de satélite.
         */
        const val GET_PARAM_HAS_SATELLITE_SIGNAL = "GET_PARAM_HAS_SATELLITE_SIGNAL"

        /**
         * Indica se existe alguma atualização da aplicação disponível para instalação.
         * **Aplicável apenas a produtos que possuem comunicação celular.**
         */
        const val GET_PARAM_HAS_UPDATE_PENDING = "GET_PARAM_HAS_UPDATE_PENDING"

        /**
         * Indica as versões de firmware do conversor wi-fi serial homologados.
         */
        const val GET_PARAM_HOM_WIFI_SERIAL_DEV_FW_VERSIONLIST =
            "GET_PARAM_HOM_WIFI_SERIAL_DEV_FW_VERSIONLIST"

        /**
         * Controla se WiFi, Gprs, Gps, etc. não serão gerenciados (ligados/desligados).
         * @see ParameterValues.HWControlDisableValues
         */
        const val GET_PARAM_HW_CONTROL_DISABLE = "GET_PARAM_HW_CONTROL_DISABLE"

        /**
         * Número do ICCID do chip inserido no slot 1 do dispositivo.
         * **Aplicável apenas a produtos que possuem comunicação celular.**
         */
        const val GET_PARAM_ICCID1 = "GET_PARAM_ICCID1"

        /**
         * Indica o status da ignição reportada pelo dispositivo externo.
         * (=0 Desligada, =1 Ligada).
         * @see ActionValues.ValuesIgnitionStatusParam1
         */
        const val GET_PARAM_IGNITION_STATUS = "GET_PARAM_IGNITION_STATUS"

        /**
         * Número do IMEI principal do dispositivo.
         * **Aplicável apenas a produtos que possuem comunicação celular.**
         */
        const val GET_PARAM_IMEI1 = "GET_PARAM_IMEI1"

        /**
         * Data/hora (GMT) da última comunicação via rede celular.
         * ** Aplicável apenas a produtos que possuem comunicação celular.**
         *
         * A data da última comunicação é baseada na última troca de mensagem ou posição.
         */
        const val GET_PARAM_LAST_CELL_COMM_TIME = "GET_PARAM_LAST_CELL_COMM_TIME"

        /**
         * Último status conhecido da conexão via rede celular.
         * @see ParameterValues.ValuesConnectionStates
         */
        const val GET_PARAM_LAST_CELL_CONNECTION_STATUS = "GET_PARAM_LAST_CELL_CONNECTION_STATUS"

        /**
         * Data/hora (GMT) da última comunicação via rede satelital.
         * A data da última comunicação é baseada na última troca de mensagem ou posição.
         */
        const val GET_PARAM_LAST_SAT_COMM_TIME = "GET_PARAM_LAST_SAT_COMM_TIME"

        /**
         * Último status conhecido da conexão via rede satelital.
         * @see ParameterValues.ValuesConnectionStates
         */
        const val GET_PARAM_LAST_SAT_CONNECTION_STATUS = "GET_PARAM_LAST_SAT_CONNECTION_STATUS"

        /**
         * Indica se o uso da VPN pelo serviço de comunicação é permitido ou não.
         * @see ParameterValues.VpnDisableValues
         */
        const val GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION = "GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION"

        /**
         * Endpoint específico para habilitar ou desabilitar o uso da VPN.
         *  O valor 1 desabilita o uso da VPN pelo serviço de comunicação e o valor 0 habilita.
         *  Utilizada em operações envolvendo o uso simultâneo do HotSpot WiFi e da conexão celular.
         *  Ao final do uso da VPN em modo desabilitado,  deve ser novamente habilitado (configurando o valor como 0), se desejado.
         *  @see ParameterValues.VpnDisableValues
         */
        const val SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION = "SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION"

        /**
         * Indica se o uso do WiFi pelo serviço de comunicação é permitido ou não.
         * @see ParameterValues.WifiDisableValues
         */
        const val GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION = "GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION"

        /**
         * Enpoint específico para habilitar ou desabilitar o uso do WiFi.
         *  O valor 1 desabilita o uso do WiFi pelo serviço de comunicação e o valor 0 habilita.
         *  Utilizada quando a aplicação do cliente precisa fazer uso da rede WiFi
         *  e a unidade se encontra batizada com um dispositivo de comunicação satelital utilizando a rede WiFi.
         *  Ao final do uso do WiFi, deve ser reconfigurado para o valor 0.
         *  @see ParameterValues.WifiDisableValues
         */
        const val SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION = "SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION"

        /**
         * Diretório base onde os arquivos de mensagens longas (Out Of Band) serão armazenados.
         */
        const val GET_PARAM_OUT_OF_BAND_MSG_PATH = "GET_PARAM_OUT_OF_BAND_MSG_PATH"

        /**
         * Endpoint específico para alterar o diretório de armazenamento dos arquivos de mensagens longas (Out Of Band).
         * Os arquivos enviados estarão armazenados dentro de um subdiretório chamado "Sent" (PARAM_OUT_OF_BAND_MSG_PATH/Sent/).
         * Os arquivos baixados estarão armazenados dentro de um subdiretório chamado "Received" (PARAM_OUT_OF_BAND_MSG_PATH/Received/).
         * Para cada mensagem haverá ainda um subdiretório dentro deste, cujo nome será o código da mensagem no banco de dados
         * onde todos os arquivos referentes à mensagem estarão armazenados.
         * Ex.: "PARAM_OUT_OF_BAND_MSG_PATH/Received/00055/".
         * Este parâmetro somente deve conter o diretório base, os subdiretórios (Sent, Received, etc.) serão criados automaticamente.
         * **ATENÇÃO: É importante que este diretório esteja acessível por qualquer aplicação e independente de permissão específica para acesso.**
         */
        const val SET_PARAM_OUT_OF_BAND_MSG_PATH = "SET_PARAM_OUT_OF_BAND_MSG_PATH"

        /**
         * Provedor do SIM Card: Vivo, Claro, Tim, etc.
         * O valor deste parâmetro só deve ser alterado quando houver uma troca/detecção de SIM Card.
         * Utilizado para identificar quando o cliente trocou o SIM Card para um outro provedor.
         */
        const val GET_PARAM_PHONE_PROVIDER_NAME = "GET_PARAM_PHONE_PROVIDER_NAME"

        /**
         * Indica quais os pacotes permitidos para acessar o serviço de redirecionamento de internet.
         */
        const val GET_PARAM_PROXY_APPS_ON_WHITE_LIST = "GET_PARAM_PROXY_APPS_ON_WHITE_LIST"

        /**
         * Endereço primário do servidor de conexão (AMH).
         */
        const val GET_PARAM_SERVER_IP1 = "GET_PARAM_SERVER_IP1"

        /**
         * Porta do servidor de conexão (AMH).
         */
        const val GET_PARAM_SERVER_PORT1 = "GET_PARAM_SERVER_PORT1"

        /**
         * Versão do serviço Autotrac Mobile Communicator atualmente instalada, no formato x.x.xx".
         */
        const val GET_PARAM_SERVICE_VERSION = "GET_PARAM_SERVICE_VERSION"

        /**
         * Tempo de espera, em segundos, para que uma mensagem seja enviada pela rede celular antes de tentar enviar pela rede satelital
         * **Aplicável apenas a produtos que possuem comunicação celular.**
         */
        const val GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG = "GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG"

        /**
         * Altera o tempo de espera, em segundos, para que uma mensagem seja enviada pela rede satelital antes de tentar enviar pela rede
         * Obs.: Só é permitido configurar valores entre 10 e 30 segundos.
         * O valor recomendado é de 30 segundos.
         */
        const val SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG = "SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG"

        /**
         * Endereço da UC (Unidade de Comunicação).
         * **Aplicável apenas a produtos que possuem comunicação celular.**
         * O endereço de uma UC é um número único que identifica a unidade dentro do sistema de comunicação.
         */
        const val GET_PARAM_UC_ADDRESS = "GET_PARAM_UC_ADDRESS"

        /**
         * Status atualizado da Uc Móvel após a tentativa de estabelecimento da comunicação via celular com o servidor.
         * @return [ParameterValues.UcStatusValues]
         */
        const val GET_PARAM_UC_STATUS = "GET_PARAM_UC_STATUS"

        /**
         * Subtipo da UC móvel.
         *@see ParameterValues.CmuSubtypeValues
         */
        const val GET_PARAM_UC_SUBTYPE = "GET_PARAM_UC_SUBTYPE"

        /**
         * Versão da Interface de Usuário do serviço Autotrac Mobile Communicator atualmente instalada,
         * no formato "x.x.xx".
         */
        const val GET_PARAM_USER_INTERFACE_VERSION = "GET_PARAM_USER_INTERFACE_VERSION"

        /**
         * Indica o status atual da conexão VPN (=0 desconectada, =1 conectada).
         * @see ParameterValues.VpnConnectionStatusValues
         */
        const val GET_PARAM_VPN_CONNECTION_STATUS = "GET_PARAM_VPN_CONNECTION_STATUS"

        /**
         * Serial Number do dispositivo Conversor Wi-Fi/Serial utilizado.
         * **Aplicável apenas a produtos que possuem comunicação satelital.**
         */
        const val GET_PARAM_WIFI_CONVERTER_SERIAL_NUMBER = "GET_PARAM_WIFI_CONVERTER_SERIAL_NUMBER"

        /**
         * Endereço IP da rede Wi-Fi do dispositivo móvel (Android).
         */
        const val GET_PARAM_WIFI_IP_ADDRESS = "GET_PARAM_WIFI_IP_ADDRESS"

        /**
         * Versão de firmware do conversor WiFi/Serial.
         */
        const val GET_PARAM_WIFI_SERIAL_DEV_FIRMWARE_VERSION =
            "GET_PARAM_WIFI_SERIAL_DEV_FIRMWARE_VERSION"

    }
}