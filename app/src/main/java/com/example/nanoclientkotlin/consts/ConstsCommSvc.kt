package com.example.nanoclientkotlin.consts

/**
 * Classe de constantes a serem empregadas na comunicação via integraçao NanoWebsocket e Http.
 * Refere-se aos endpoints e demais configuraçoes.
 * @author Melina Minaya
 */
class ConstsCommSvc {


    companion object {
        /**
         * Intent utilizado para inicializar o serviço.
         * A inicializaçao consiste em criar arquivos necessarios ao funcionamento do serviço.
         */
        // private static final String INTENT_SVC_INIT = "br.com.autotrac.jATMobileCommSvc.Init";
        const val INTENT_SVC_INITIALIZE = "br.com.autotrac.jATMobileCommSvc.Initialize"

        /** Intent utilizado para iniciar o funcionamento do serviço.  */
        const val INTENT_SVC_START = "br.com.autotrac.jATMobileCommSvc.Start"

        /** Intent utilizado para parar o funcionamento do serviço.  */
        const val INTENT_SVC_STOP = "br.com.autotrac.jATMobileCommSvc.Stop"

        /** Intent utilizado para finalizar o serviço.  */
        const val INTENT_SVC_FINALIZE = "br.com.autotrac.jATMobileCommSvc.Finalize"

        /** Package do serviço.  */
        const val INTENT_SVC_PACKAGE_NAME = "br.com.autotrac.jatmobilecommsvc"

        /** Classe do serviço.  */
        const val SERVICE_CLASSNAME = "br.com.autotrac.service.ATMobileCommSvc"

        /** Intent utilizado para o knox.  */
        const val INTENT_ACTION_NEED_KNOX = "need_knox"

        const val BASE_URL = "http://127.0.0.1:8081"
        const val AUTH_ENDPOINT = "auth"

        //Lista da Integração referente à requisições

        /**
         * Endpoint de envio de mensagens para o servidor.
         *
         *  [SEND_MESSAGE] para resposta referente ao envio de mensagem.
         *
         *  [SEND_FILE_MESSAGE] para resposta referente ao envio de arquivo.
         */
        const val SEND_MESSAGE = "SEND_MESSAGE"
        const val SEND_FILE_MESSAGE = "SEND_FILE_MESSAGE"
        const val SEND_FILE_CHUNK = "SEND_FILE_CHUNK"

        /**
         * Endpoint de notificacao.
         *
         * @return Resposta do Servidor será um objeto do tipo [ReceivedRequestResponse].
         *
         * @param param1 Pode ser notificacao, requisicao, parametro,

         * @see com.example.nanoclientkotlin.dataRemote.ReceivedRequestResponse
         *
         */
        const val NOTIFICATION = "NOTIFICATION"
        const val REQUEST = "REQUEST"
        const val PARAMETER = "PARAMETER"

        const val REQ_MESSAGE_LIST = "REQ_MESSAGE_LIST"
        const val REQ_MESSAGE_LIST_INBOX = "REQ_MESSAGE_LIST_INBOX"
        const val REQ_MESSAGE_LIST_OUTBOX = "REQ_MESSAGE_LIST_OUTBOX"

        /**
         * Contabiliza a quantidade de mensagens no banco de dados de acordo com o filtro especificado.
         *
         * Pode ser usado, por exemplo, para contabilizar a quantidade de mensagens a enviar.
         * Neste caso o parametro isForward deve ser true e msgStatusNum deve ser [ActionValues.MessageStatusValues.TO_SEND].
         * Ele deve ser preferido ao inves do metodo messageList(long, boolean, int) por motivos de performance.
         * Muitas vezes mais rapido do que o metodo messageList(long, boolean, int).
         * @param1: (isForward) - indica se as mensagens de envio ou retorno devem ser filtradas.
         * Se true indica que as mensagens de envio serao filtradas. Se false as mensagens de retorno serao filtradas.
         * @param2: (msgStatusNum) - é número do status a ser filtrado.
         * @see ActionValues.MessageStatusValues
         */
        const val REQ_MESSAGE_COUNT = "REQ_MESSAGE_COUNT"

        /**
         * Apaga uma mensagem (ou todas as mensagens) do banco de dados do serviço.
         * @param1: (msgCode) - codigo da mensagem a ser apagada.
         *
         * **Atenção**: Se o codigo da mensagem for 0, todas as mensagens serao apagadas!
         */
        const val REQ_MESSAGE_DELETE = "REQ_MESSAGE_DELETE"

        /**
         * Marca uma mensagem com o status de "lida".
         *
         * O status da mensagem será configurado para [ActionValues.MessageStatusValues.READ] e
         * se a mensagem exigir confirmaçao de leitura, a confirmação sera gerada e enviada ao servidor.
         * @param1: (msgCode) - codigo da mensagem que deve ser marcada como lida.
         */
        const val REQ_MESSAGE_SET_AS_READ = "REQ_MESSAGE_SET_AS_READ"

        /**
         * Configura a geração do log em arquivo para o serviço.
         * Este método configura a geração de log para o serviço de comunicação (não para a API).
         * @param1: (enable) - boolean informa se o log deve ser ativado/desativado.
         * @param2: (maxFileCount) - é a quantidade máxima de arquivos de logs a serem gerados.
         * Quando a quantidade máxima é atingida, o arquivo mais antigo é sobrescrito.
         * @param3: (maxFileSize) - é o tamanho máximo de cada arquivo de log gerado. Ex.: 3*1024*1024 (3MB).
         */
        const val REQ_CONFIG_SERVICE_LOG = "REQ_CONFIG_SERVICE_LOG"

        //TODO: Check FILE_OPERATION_STATUS and ACTION
        /**
         * Copia os arquivos para o diretório especificado.
         * Parameters:
         * @param1: (files) - indica quais arquivos devem ser envolvidos na operação.
         * Este parâmetro é um mapa de bits. Mais de um arquivo pode ser especificado utilizando o operador OU bit a bit ("|").
         * @see ActionValues.FileOperationFiles
         * @param2: (options) - opções a serem utilizadas na operação.
         * @see ActionValues.FileOperationOptions
         * @param3: (destination) - no caso da opção ActionValues.FileOperationOptions.COPY_FILES,
         * é o diretório de destino para os arquivos.
         * @param4: (timeoutMs) - indica quanto tempo, em ms, que o método deve aguardar até o fim da operação.
         * Se este parâmetro for diferente de 0, o método só retornará quando a operação for concluída ou o timeout expirar.
         * Se este parâmetro for 0, o método retorna imediatamente e o usuário deve aguardar o evento
         * McuibConsts.McuibValuesActionIds.FILE_OPERATION_STATUS para determinar o status da operação.
         * Se o timeout se esgotar antes do fim da operação, uma exceção é gerada, mas mesmo assim, quando a operação for completada,
         * o evento McuibConsts.McuibValuesActionIds.FILE_OPERATION_STATUS será gerado.
         */
        const val REQ_FILE_OPERATION = "REQ_FILE_OPERATION"

        /**
         * Lista os formulários ou grupos de formulários de acordo com o filtro especificado
         * (Este método só terá aplicabilidade para produtos que possuem comunicação celular).
         * @param1: (groupOnly) - se for true, indica que a consulta deve ser realizada nos grupos de formulário.
         * Neste caso, apenas o parâmetro formGroupCode é considerado.
         * Se for false, a consulta será realizada nos formulários.
         * @param2: (formGroupCode) - indica qual grupo de formulário deve ser filtrado.
         * Somente os formulários pertencentes a este grupo serão retornados.
         * Se este parâmetro for igual a 0, o filtro será desabilitado.
         * @param3: (formType) - é o tipo de formulário a ser filtrado.
         * Somente os formulários do tipo especificado serão retornados.
         * @see ActionValues.FormTypeValues
         */
        const val REQ_FORM_LIST = "REQ_FORM_LIST"
        const val REQ_GET_CHECKLIST = "REQ_GET_CHECKLIST"
        const val REQ_CELL_SIGNAL = "REQ_CELL_SIGNAL"
        const val REQ_WIFI_SIGNAL = "REQ_WIFI_SIGNAL"
        const val REQ_GET_CURRENT_DATE = "REQ_GET_CURRENT_DATE"
        const val REQ_GET_MCT_PARAMETERS = "REQ_GET_MCT_PARAMETERS"

        /**
         * Retorna informações detalhadas da posição mais recente disponível.
         * @param1: (posSourceType) - é a fonte da posição cujos dados devem ser retornados.
         * @see ActionValues.PositionSourceType
         */
        const val REQ_GET_POSITION_LAST = "REQ_GET_POSITION_LAST"

        /**
         * Contabiliza a quantidade de posições do histórico do banco de dados de acordo com o filtro especificado.
         * Este método pode ser usado, por exemplo, para contabilizar a quantidade de posições a enviar.
         * Neste caso o parâmetro msgStatusNum deve ser [ActionValues.MessageStatusValues.TO_SEND].
         * Ele deve ser preferido ao invés do método positionHistoryList(long, int) por questão de performance.
         * Este método é muitas vezes mais rápido do que o método positionHistoryList(long, int).
         * @param1: (msgStatusNum) - é número do status a ser filtrado.
         * @see ActionValues.MessageStatusValues
         * */
        const val REQ_POSITION_HISTORY_COUNT = "REQ_POSITION_HISTORY_COUNT"

        /**
         * Lista todas as posições do histórico de acordo com o filtro especificado.
         * @param1: (posCode) - é o código da posição a ser retornada. Se este parâmetro for igual a 0, ele será ignorado.
         * Se for diferente de 0, os demais filtros serão ignorados e somente a mensagem com o código especificado será retornada.
         * @param2: (msgStatusNum) - é número do status a ser filtrado.
         * @see ActionValues.MessageStatusValues
         * Este parâmetro só é considerado se @param2 (posCode) for igual a 0.
         * */
        const val REQ_POSITION_HISTORY_LIST = "REQ_POSITION_HISTORY_LIST"
        const val REQ_RESET_DATABASE = "REQ_RESET_DATABASE"

        //Lista da Integração referente a parâmetros

        /**
         *Indica se a UC possui algum dispositivo de comunicação alternativo associado.
         * Quando a UC possui um dispositivo de comunicação alternativo associado a ela, diz-se que ele está batizado.
         */
        const val GET_PARAM_IS_BAPTIZED = "GET_PARAM_IS_BAPTIZED"

        /**
         * SSID da rede WiFi utilizada para se conectar ao dispositivo de comunicação alternativa.
         * O SSID (Service Set Identifier) é o nome da rede WiFi utilizada para se conectar ao dispositivo de comunicação alternativo.
         * (O SSID só pode ser configurado para produtos que possuem comunicação exclusiva satélite).
         * @see ParameterValues.CmuSubtypeValues.MOBILE_SATELLITE
         * Para sistemas que utilizam puramente o dispositivo de comunicação alternativa,
         * se for configurado um SSID, a associação com o dispositivo ocorrerá automaticamente em seguida.
         * Caso o SSID fornecido seja vazio ("") e o sistema já esteja associado a um dispositivo de comunicação alternativa, a associação será desfeita.
         */
        const val GET_PARAM_WIFI_SSID = "GET_PARAM_WIFI_SSID"
        const val SET_PARAM_WIFI_SSID = "SET_PARAM_WIFI_SSID"

        /**
         * Retorna o número da conta (também chamada de UC Fixa) em que a unidade móvel está contida (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
        O endereço de uma UC Fixa é um número único que identifica a unidade Fixa dentro do sistema de comunicação.
         */
        const val GET_PARAM_ACCOUNT_NUMBER = "GET_PARAM_ACCOUNT_NUMBER"

        /**
         * Intervalo, em segundos, entre comunicações com o dispositivo de comunicação alternativo.
         * Este intervalo é o intervalo entre verificações de novas mensagens junto ao dispositivo de comunicação alternativo.
         * O intervalo recomendado é 10 segundos.
         */
        const val GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S = "GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S"
        const val SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S = "SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S"

        /**
         * Versao de firmware do dispositivo de comunicacao alternativo, por exemplo: MCT, ACU2, Iridium, etc
         */
        const val GET_PARAM_ALT_COMM_FIRMWARE_VERSION = "GET_PARAM_ALT_COMM_FIRMWARE_VERSION"

        /**
         * Endereço do dispositivo de comunicação alternativo. O dispositivo de comunicação alternativo geralmente é um dispositivo de comunicação satelital.
         */
        const val GET_PARAM_ALTERNATIVE_COMM_DEVICE_ADDRESS = "GET_PARAM_ALTERNATIVE_COMM_DEVICE_ADDRESS"

        /**
         * Endereço IP da rede Celular do dispositivo. (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
        Este parâmetro só é válido enquanto o dispositivo está conectado na rede celular.
         */
        const val GET_PARAM_CELL_IP_ADDRESS = "GET_PARAM_CELL_IP_ADDRESS"

        /**
         * Nome do diretorio usado pelo app do cliente para gravar os arquivos de log.
         */
        const val GET_PARAM_CLIENT_LOGS_DIRECTORY = "GET_PARAM_CLIENT_LOGS_DIRECTORY"

        /**
         * Identificação do modelo do aparelho móvel.
         */
        const val GET_PARAM_COMM_UNIT_DEVICE_TYPE = "GET_PARAM_COMM_UNIT_DEVICE_TYPE"

        /**
         * Modo de comunicação corrente.
         * @see ParameterValues.COMM_MODE_NONE e outros.
         */
        const val GET_PARAM_CURRENT_COMM_MODE = "GET_PARAM_CURRENT_COMM_MODE"

        /**
         * Tipo de comunicação (meio físico) a ser utilizado com o dispositivo externo.
         * @see ParameterValues.ExternalDeviceCommunicationTypeValues
         * Alterar para 2 (ancoragem) para realizar o batismo.
         */
        const val GET_PARAM_EXT_DEV_COMM_TYPE = "GET_PARAM_EXT_DEV_COMM_TYPE"

        const val SET_PARAM_EXT_DEV_COMM_TYPE = "SET_PARAM_EXT_DEV_COMM_TYPE"

        /**
         * Status do processo de upload dos arquivos de log via FTP.
         */
        const val GET_PARAM_FTP_LOGS_STATUS = "GET_PARAM_FTP_LOGS_STATUS"

        /**
         * Indica se existe sinal celular (Este método só terá aplicabilidade para produtos que possuem comunicação celular). O retorno é baseado na troca de mensagens via rede celular nos últimos 5 minutos. Se houve troca de pacotes nos últimos 5 minutos, será considerado que existe sinal celular.

         */
        const val GET_PARAM_HAS_CELLULAR_SIGNAL = "GET_PARAM_HAS_CELLULAR_SIGNAL"

        /**
         * Indica se existe sinal de satélite.
         */
        const val GET_PARAM_HAS_SATELLITE_SIGNAL = "GET_PARAM_HAS_SATELLITE_SIGNAL"

        /**
         * Indica se existe alguma atualização da aplicação disponível para instalação (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
         */
        const val GET_PARAM_HAS_UPDATE_PENDING = "GET_PARAM_HAS_UPDATE_PENDING"

        /**
         * Indica as versões de firmware do conversor wi-fi serial homologados.
         */
        const val GET_PARAM_HOM_WIFI_SERIAL_DEV_FW_VERSIONLIST =
            "GET_PARAM_HOM_WIFI_SERIAL_DEV_FW_VERSIONLIST"

        /**
         * Controla se WiFi, Gprs, Gps, etc. não serão gerenciados (ligados/desligados).
         * @see ParameterValues.CELLULAR e outros.
         */
        const val GET_PARAM_HW_CONTROL_DISABLE = "GET_PARAM_HW_CONTROL_DISABLE"

        /**
         * Número do ICCID do chip inserido no slot 1 do dispositivo (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
         */
        const val GET_PARAM_ICCID1 = "GET_PARAM_ICCID1"

        /**
         * Indica o status da ignição reportada pelo dispositivo externo. (=0 Desligada, =1 Ligada).
         * @see ParameterValues.IGNITION_ON
         * @see ParameterValues.IGNITION_OFF
         */
        const val GET_PARAM_IGNITION_STATUS = "GET_PARAM_IGNITION_STATUS"

        /**
         * Número do IMEI principal do dispositivo (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
         */
        const val GET_PARAM_IMEI1 = "GET_PARAM_IMEI1"

        /**
         * Data/hora (GMT) da última comunicação via rede celular (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular). A data da última comunicação é baseada na última troca de mensagem ou posição.
         */
        const val GET_PARAM_LAST_CELL_COMM_TIME = "GET_PARAM_LAST_CELL_COMM_TIME"

        /**
         * Último status conhecido da conexão via rede celular.
         * @see ParameterValues
         * TODO: Values on Screen and Const
         */
        const val GET_PARAM_LAST_CELL_CONNECTION_STATUS = "GET_PARAM_LAST_CELL_CONNECTION_STATUS"

        /**
         * Data/hora (GMT) da última comunicação via rede satelital. A data da última comunicação é baseada na última troca de mensagem ou posição.
         */
        const val GET_PARAM_LAST_SAT_COMM_TIME = "GET_PARAM_LAST_SAT_COMM_TIME"

        /**
         * Último status conhecido da conexão via rede satelital.
         * @see ParameterValues.LOGICAL_CONNECTED e outros.
         */
        const val GET_PARAM_LAST_SAT_CONNECTION_STATUS = "GET_PARAM_LAST_SAT_CONNECTION_STATUS"

        /**
         * Indica se o uso da VPN pelo serviço de comunicação é permitido ou não.
         * O valor 1 desabilita o uso da VPN pelo serviço de comunicação e o valor 0 habilita.
         * Esta opção deve ser utilizada quando operações envolvendo o uso simultâneo do HotSpot WiFi e da conexão celular.
         * Logo que possível o uso da VPN deve ser habilitado (configurando o valor deste parâmetro como 0), caso seja desejável o seu uso.
         */
        const val GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION = "GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION"
        const val SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION = "SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION"

        /**
         * Indica se o uso do WiFi pelo serviço de comunicação é permitido ou não.
         * O valor 1 desabilita o uso do WiFi pelo serviço de comunicação e o valor 0 habilita.
         * Esta opção deve ser utilizada quando a aplicação do cliente precisa fazer uso da rede WiFi
         * e a unidade está batizada com um dispositivo de comunicação satelital utilizando a rede WiFi.
         * Logo que a aplicação finalizar o uso do WiFi, este parâmetro deve ser reconfigurado para o valor 0.
         */
        const val GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION = "GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION"
        const val SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION = "SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION"

        /**
         * Diretório base onde os arquivos de mensagens longas (Out Of Band) serão armazenados.
         * Os arquivos enviados estarão armazenados dentro de um subdiretório chamado "Sent" (PARAM_OUT_OF_BAND_MSG_PATH/Sent/).
         * Os arquivos baixados estarão armazenados dentro de um subdiretório chamado "Received" (PARAM_OUT_OF_BAND_MSG_PATH/Received/).
         * Para cada mensagem haverá ainda um subdiretório dentro deste, cujo nome será o código da mensagem no banco de dados onde todos os arquivos referentes à mensagem estarão armazenados.
         * Ex.: "PARAM_OUT_OF_BAND_MSG_PATH/Received/00055/".
         * Este parâmetro só deve conter o diretório base, os subdiretórios (Sent, Received, etc.) serão criados automaticamente.
         * ATENÇÃO: É importante que este diretório esteja acessível por qualquer aplicação (não dependa de permissão específica para seu acesso).
         */
        const val GET_PARAM_OUT_OF_BAND_MSG_PATH = "GET_PARAM_OUT_OF_BAND_MSG_PATH"
        const val SET_PARAM_OUT_OF_BAND_MSG_PATH = "SET_PARAM_OUT_OF_BAND_MSG_PATH"

        /**
         * Provedor do SIM Card: Vivo, Claro, Tim, etc. O valor deste parâmetro só deve ser alterado quando houver uma troca/detecção de SIM Card. Ele é utilizado para identificar quando o usuário trocou o SIM Card para um outro provedor.
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
         * (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
         * Obs.: Só é permitido configurar valores entre 10 e 30 segundos.
         * O valor recomendado é de 30 segundos.
         */
        const val GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG = "GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG"
        const val SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG = "SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG"

        /**
         * Endereço da UC (Unidade de Comunicação) (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação celular).
        O endereço de uma UC é um número único que identifica a unidade dentro do sistema de comunicação.
         */
        const val GET_PARAM_UC_ADDRESS = "GET_PARAM_UC_ADDRESS"

        /**
         * Status atualizado da Uc Móvel após a tentativa de estabelecimento da comunicação via celular com o servidor.
         * TODO: Search values and implement.
         */
        const val GET_PARAM_UC_STATUS = "GET_PARAM_UC_STATUS"

        /**
         * Subtipo da UC móvel.
         *@see ParameterValues.CELLULAR e outros.
         */
        const val GET_PARAM_UC_SUBTYPE = "GET_PARAM_UC_SUBTYPE"

        /**
         * Versão da Interface de Usuário do serviço Autotrac Mobile Communicator atualmente instalada, no formato "x.x.xx".
         */
        const val GET_PARAM_USER_INTERFACE_VERSION = "GET_PARAM_USER_INTERFACE_VERSION"

        /**
         * Indica o status atual da conexão VPN (=0 desconectada, =1 conectada).
         * @see ParameterValues.STATUS_ENABLED_VPN
         * @see ParameterValues.STATUS_DISABLED_VPN
         */
        const val GET_PARAM_VPN_CONNECTION_STATUS = "GET_PARAM_VPN_CONNECTION_STATUS"

        /**
        Serial Number do dispositivo Conversor Wi-Fi/Serial utilizado.
        (Este parâmetro só terá aplicabilidade para produtos que possuem comunicação satelital).
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




        val parametersList = listOf(
            GET_PARAM_IS_BAPTIZED,
            GET_PARAM_WIFI_SSID,
            SET_PARAM_WIFI_SSID,
            GET_PARAM_ACCOUNT_NUMBER,
            GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S,
            SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S,
            GET_PARAM_ALT_COMM_FIRMWARE_VERSION,
            GET_PARAM_ALTERNATIVE_COMM_DEVICE_ADDRESS,
            GET_PARAM_CELL_IP_ADDRESS,
            GET_PARAM_CLIENT_LOGS_DIRECTORY,
            GET_PARAM_COMM_UNIT_DEVICE_TYPE,
            GET_PARAM_CURRENT_COMM_MODE,
            GET_PARAM_EXT_DEV_COMM_TYPE,
            SET_PARAM_EXT_DEV_COMM_TYPE,
            GET_PARAM_FTP_LOGS_STATUS,
            GET_PARAM_HAS_CELLULAR_SIGNAL,
            GET_PARAM_HAS_SATELLITE_SIGNAL,
            GET_PARAM_HAS_UPDATE_PENDING,
            GET_PARAM_HOM_WIFI_SERIAL_DEV_FW_VERSIONLIST,
            GET_PARAM_HW_CONTROL_DISABLE,
            GET_PARAM_ICCID1,
            GET_PARAM_IGNITION_STATUS,
            GET_PARAM_IMEI1,
            GET_PARAM_LAST_CELL_COMM_TIME,
            GET_PARAM_LAST_CELL_CONNECTION_STATUS,
            GET_PARAM_LAST_SAT_COMM_TIME,
            GET_PARAM_LAST_SAT_CONNECTION_STATUS,
            GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION,
            SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION,
            GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION,
            SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION,
            GET_PARAM_OUT_OF_BAND_MSG_PATH,
            SET_PARAM_OUT_OF_BAND_MSG_PATH,
            GET_PARAM_PHONE_PROVIDER_NAME,
            GET_PARAM_PROXY_APPS_ON_WHITE_LIST,
            GET_PARAM_SERVER_IP1,
            GET_PARAM_SERVER_PORT1,
            GET_PARAM_SERVICE_VERSION,
            GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG,
            SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG,
            GET_PARAM_UC_ADDRESS,
            GET_PARAM_UC_STATUS,
            GET_PARAM_UC_SUBTYPE,
            GET_PARAM_USER_INTERFACE_VERSION,
            GET_PARAM_VPN_CONNECTION_STATUS,
            GET_PARAM_WIFI_CONVERTER_SERIAL_NUMBER,
            GET_PARAM_WIFI_IP_ADDRESS,
            GET_PARAM_WIFI_SERIAL_DEV_FIRMWARE_VERSION
        )

        val requestsListForObservables = setOf(
            REQ_MESSAGE_LIST,
            REQ_MESSAGE_LIST_INBOX,
            REQ_MESSAGE_LIST_OUTBOX,
            REQ_MESSAGE_COUNT,
            REQ_MESSAGE_DELETE,
            REQ_MESSAGE_SET_AS_READ,
            REQ_CONFIG_SERVICE_LOG,
            REQ_FILE_OPERATION,
            REQ_FORM_LIST,
            REQ_GET_CHECKLIST,
            REQ_CELL_SIGNAL,
            REQ_WIFI_SIGNAL,
            REQ_GET_CURRENT_DATE,
            REQ_GET_MCT_PARAMETERS,
            REQ_GET_POSITION_LAST,
            REQ_POSITION_HISTORY_COUNT,
            REQ_POSITION_HISTORY_LIST,
            REQ_RESET_DATABASE,
        ).toList()
        val requestsList = setOf(
            REQ_MESSAGE_LIST,
            REQ_MESSAGE_COUNT,
            REQ_MESSAGE_DELETE,
            REQ_MESSAGE_SET_AS_READ,
            REQ_CONFIG_SERVICE_LOG,
            REQ_FILE_OPERATION,
            REQ_FORM_LIST,
            REQ_GET_CHECKLIST,
            REQ_CELL_SIGNAL,
            REQ_WIFI_SIGNAL,
            REQ_GET_CURRENT_DATE,
            REQ_GET_MCT_PARAMETERS,
            REQ_GET_POSITION_LAST,
            REQ_POSITION_HISTORY_COUNT,
            REQ_POSITION_HISTORY_LIST,
            REQ_RESET_DATABASE,
        ).toList()
        val listOfFilteredRequests = setOf(
            REQ_MESSAGE_COUNT,
            REQ_MESSAGE_LIST,
            REQ_MESSAGE_DELETE,
            REQ_MESSAGE_SET_AS_READ,
            REQ_CONFIG_SERVICE_LOG,
            REQ_FILE_OPERATION,
            REQ_FORM_LIST,
            REQ_GET_POSITION_LAST,
            REQ_POSITION_HISTORY_COUNT,
            REQ_POSITION_HISTORY_LIST,
            SET_PARAM_EXT_DEV_COMM_TYPE,
            SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S,
            SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION,
            SET_PARAM_OUT_OF_BAND_MSG_PATH,
            SET_PARAM_WIFI_SSID,
            SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION,
            SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG,
            SEND_MESSAGE,
            SEND_FILE_MESSAGE
        ).toList()

    }
}