package br.com.autotrac.testnanoclient.consts
/**
 * Identificadores das ações de notificação.
 * @author Melina Minaya
 */
object ActionValues {


    /**
     * Um formulário foi recebido ou atualizado.
     * @param Param1 Código do formulário no banco de dados.
     * @param Param2 Não utilizado.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     * @see br.com.autotrac.testnanoclient.dataRemote.IntegrationForm.code
     */
    const val FORM_RECEIVED = 0x03

    /**
     * Um formulário foi removido.
     * @param Param1 Código do formulário no banco de dados.
     * @param Param2 Não utilizado.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     * @see br.com.autotrac.testnanoclient.dataRemote.IntegrationForm.code
     */
    const val FORM_DELETED = 0x04

    /**
     * A unidade se conectou ao servidor AMH.
     * @param Param1 Status do SignOn.
     * @param Param2 Não utilizado.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val SIGN_ON_STATUS = 0x05

    /**
     * Indica o status do processo de batismo.
     * @param Param1 Status do processo de batismo. Ver [ValuesBaptismStatusParam1]
     * @param Param2 Depende de Param1.
     * Se [ValuesBaptismStatusParam1.BAPTIZED],
     * [ValuesBaptismStatusParam1.WAITING_CONFIRMATION],
     * [ValuesBaptismStatusParam1.MCT_ADDR_RESPONSE] ou
     * [ValuesBaptismStatusParam1.MCT_NOT_AUTHORIZED], Param2 sera o número do Mct.
     * Se [ValuesBaptismStatusParam1.NOT_BAPTIZED],
     * [ValuesBaptismStatusParam1.IN_BAPTISM_PROCESS],
     * [ValuesBaptismStatusParam1.BAPTISM_TIMED_OUT], Param2 Não utilizado.
     * **Param3:** Não utilizado.
     * **Param4:** Não utilizado.
     */
    const val BAPTISM_STATUS = 0x07
    const val BAPTISM_STATUS_OBSERVABLE = "BAPTISM_STATUS"

    /**
     * Os arquivos de atualização foram baixados e estão prontos para iniciar
     * a atualização.
     * @param Param1 Não utilizado.
     * @param Param2 Não utilizado.
     * @param Param3 Indica a versão de atualização disponivel contida no nome do versionList.
     * @param Param4 Não utilizado.
     */
    const val READY_TO_UPDATE_SOFTWARE = 0x08

    /**
     * Status das conexões de rede.
     * @param Param1 Indica a rede. Ver [ParameterValues.ValuesNetworkTypes]
     * @param Param2 Indica o status da conexão. Ver [ParameterValues.ValuesConnectionStates].
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val NETWORK_CONNECTION_STATUS = 0x09

    /**
     * Mudanca no modo de comunicação.
     * @param Param1 Modo atual de comunicação. Ver [ParameterValues.ValuesNetworkTypes]
     * @param Param2 Não utilizado.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val COMMUNICATION_MODE_CHANGED = 0x0A

    /**
     * Mudanca no sinal do satélite.
     * @param Param1 Status do sinal.
     * @return 0: Sem sinal.
     *
     * 1: Com sinal.
     * @param Param2 Não utilizado.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val SATELLITE_SIGNAL_CHANGED = 0x0B


    /**
     * Informa que os parâmetros do Mct (M0..M9) foram atualizados no banco de dados e
     * traz o valor de dois parâmetros M.
     * @param Param1 Valor do parametro M0.
     * @param Param2 Valor do parametro M2.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val MCT_M0_M9_PARAMS_UPDATED = 0x0F



    /**
     * Notifica que a Data/Hora do serviço foi alterada.
     * @param Param1 Data/Hora atualizada, em segundos desde 01/01/1970 00:00:00 GMT.
     * @param Param2 Diferenca para a Data/Hora do sistema, em segundos. Pode ser um número
     * positivo ou negativo. O resultado de Param1 - Param2 é igual a Data/Hora do sistema.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val DATE_TIME_CHANGED = 0x12



    //TODO: implement
    /**
     * Indica o status de uma [ApiConstEndpoints.REQ_FILE_OPERATION] iniciada anteriormente.
     * @param Param1 Status da operação. Ver [ValuesFileOperationStatusParam1]
     * @param Param2 Não utilizado.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val FILE_OPERATION_STATUS = 0x15

    /**
     * Indica o status de uma mensagem no banco de dados. Esta ação pode ser utilizada quando uma nova mensagem
     * for recebida ou quando uma mensagem que foi postada para envio acabou de ser enviada, assim como outros
     * status. Uma mensagem não lida recebe o status [MessageStatusValues.NOT_READ]. Uma mensagem
     * que foi enviada recebe o status [MessageStatusValues.SENT].
     * @param Param1 Código da mensagem no banco de dados.
     * @param Param2 Status da mensagem.
     *
     * Este campo deve ser utilizado para distinguir entre os diferentes status
     * de uma mensagem. Ver [MessageStatusValues].
     *
     * Este campo deve ser tratado como um inteiro de 32bits com sinal (signed int).
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val MESSAGE_STATUS = 0x16

    /** Valores dos status de mensagens. */
    object MessageStatusValues {
        /** Nenhum status associado.  */
        const val NONE = 0

        /** A enviar. **Somente para mensagens de Envio.**  */
        const val TO_SEND = 1

        /** Enviada. **Somente para mensagens de Envio.**  */
        const val SENT = 2

        /** Mensagem recebida não lida. **Somente para mensagens de retorno.**  */
        const val NOT_READ = 3

        /** Mensagem recebida lida. **Somente para mensagens de retorno.**  */
        const val READ = 4

        /** Não processada. **Somente para mensagens de Envio.**  */
        const val NOT_PROCESSED = 5

        /**
         * Transmitida. Uma mensagem foi transmitida pela rede satelital.
         * Depois da confirmação de entrega pelo servidor, ela  recebe status de enviada.
         * **Somente para mensagens de Envio.**
         */
        const val TRANSMITTED = 6

        /**
         * Mensagem longa (Out Of Band) recebida e não processada (seus arquivos ainda não foram baixados).
         * Será processada e marcada como [MessageStatusValues.NOT_READ]
         * quando os arquivos forem baixados do servidor.
         * **Somente para mensagens de Retorno.**
         */
        const val NOT_READ_OUT_OF_BAND = 7

        /**
         * Em transmissão. A mensagem se encontra em transmissão pela rede satelital, seu envio se encontra em
         * progresso, a mensagem ainda não foi completamente transmitida pelo dispositivo.
         * Quando a mensagem for transmitida, ela recebe o status de [MessageStatusValues.TRANSMITTED].
         * **Somente para mensagens de Envio.**
         */
        const val TRANSMITTING = 8
        // Status acima de 1000 devem ser tratados como status informativos.
        /**
         * Sinaliza que uma mensagem do tipo Serial está bloqueando a fila de mensagens por ser muito grande para a
         * rede satelital.
         *
         * Uma mensagem do tipo Serial pode ser muito grande para ser enviada via satélite, mas pode ser enviada
         * via celular (quando configurada para ser enviada por qualquer rede disponivel). No entanto, caso não
         * haja rede celular disponivel no momento para envio, a mensagem bloqueia a fila.
         * A fila retorna ao normal quando a rede celular estiver disponivel novamente.
         *
         * **Status apenas informativo, não gravado no banco de dados.**
         */
        const val SERIAL_MESSAGE_TOO_BIG_WAITING = 1000

        /**
         * Sinaliza que uma mensagem do tipo Serial está bloqueando a fila de mensagens porque o tipo de rede
         * escolhido para o envio da mensagem não esta disponivel.
         *
         * Uma mensagem do tipo Serial que possui o tipo de canal de transmissão igual a 'Cellular', somente será enviada
         * quando houver rede celular disponivel.
         *
         * **Status apenas informativo, não gravado no banco de dados.**
         */
        const val SERIAL_MESSAGE_CELL_NET_UNAVAILABLE = 1001

        /** Mensagem duplicada/descartada.  */
        const val MESSAGE_DUPLICATE = -5514

        /** Mensagem muito grande para ser transmitida para o receptor. **Somente para mensagens de Envio.**  */
        const val MESSAGE_TOO_BIG = -5531

        /** Mensagem OutOfBand: Arquivo não encontrado.  */
        const val OUT_OF_BAND_FILE_NOT_FOUND = -5561

        /** Erro desconhecido.  */
        const val UNKNOWN_ERROR = -5599
    }
    /**
     * Indica o status de requisição de algum recurso do sistema. Esta ação pode ser utilizada para informar
     * status de requisições de recursos do sistema, mas geralmente é utilizada para informar quando algum recurso
     * do sistema que é necessario ao bom funcionamento da aplicação não pode ser obtido.
     * @param Param1 Tipo do recurso requisitado. Ver [ValuesSysResourceReqParam1].
     * @param Param2 Status da requisição. Ver [ValuesSysResourceStatusParam2].
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val SYSTEM_RESOURCE_REQ_STATUS = 0x17

    /**
     * Indica o status da ignição do dispositivo externo.
     * @param Param1 Status da ignição. Ver [ValuesIgnitionStatusParam1]
     * @param Param2 Não utilizado.
     * @param Param3 Não utilizado.
     * @param Param4 Não utilizado.
     */
    const val IGNITION_STATUS = 0x18



    /** Status retornados pelo Param1 de ação [ActionValues.BAPTISM_STATUS].
     * Utilizado também no retorno da [ApiConstEndpoints.GET_PARAM_IS_BAPTIZED] .  */
    object ValuesBaptismStatusParam1 {
        /** A Uc não está batizada.  */
        const val NOT_BAPTIZED = 0

        /** A Uc está batizada.  */
        const val BAPTIZED = 1

        /** O processo de batismo esta em execução.  */
        const val IN_BAPTISM_PROCESS = 2

        /** Aguardando pela confirmação do batismo pelo servidor.  */
        const val WAITING_CONFIRMATION = 3

        /** Resposta da consulta do número do Mct.  */
        const val MCT_ADDR_RESPONSE = 4

        /**
         * O Mct conectado não está autorizado a se comunicar.
         * A Uc está batizada com outro Mct.
         */
        const val MCT_NOT_AUTHORIZED = 5

        /** Houve timeout no processo de batismo.  */
        const val BAPTISM_TIMED_OUT = 6
    }



    /** Status posiveis retornados pelo Param1 da ação [ActionValues.FILE_OPERATION_STATUS].  */
    object ValuesFileOperationStatusParam1 {
        /** O arquivo foi transferido com sucesso.  */
        const val SUCCESS = 0

        /** Erro na transferência do arquivo.  */
        const val ERROR = 1
    }

    /** Status retornados pelo Param1 da ação [ActionValues.IGNITION_STATUS].
     * Utilizado também para [ApiConstEndpoints.GET_PARAM_IGNITION_STATUS]
     * */
    object ValuesIgnitionStatusParam1 {
        /** A ignição está desligada.  */
        const val OFF = 0

        /** A ignição está ligada.  */
        const val ON = 1
    }


    /** Possiveis recursos do sistema cujos status da solicitação podem ser informados.
     * @see SYSTEM_RESOURCE_REQ_STATUS
     * */
    object ValuesSysResourceReqParam1 {

        /** Configuração de Nomes de Pontos de Acesso.  */
        const val APN_CONFIGURATION = 1

        /** Configuração do GPS (inclui ligar/desligar/configurar posição precisa).  */
        const val GPS_CONFIGURATION = 2
    }

    /** Possiveis status dos recursos solicitados ao sistema.
     * @see SYSTEM_RESOURCE_REQ_STATUS
     * */
    object ValuesSysResourceStatusParam2 {
        /** Sucesso, nenhum erro.  */
        const val SUCCESS = 0

        /** Erro desconhecido.  */
        const val UNKNOWN_ERROR = 1

        /** Sem permissão para obter o recurso.  */
        const val PERMISSION_DENIED = 2

        /** Recurso não disponivel.  */
        const val NOT_AVAILABLE = 3
    }


    /** Opções a serem utilizadas na cópia de arquivos setados pelo Param2
     *  da requisição [ApiConstEndpoints.REQ_FILE_OPERATION].  */
    object FileOperationOptions {
        /** Os arquivos devem ser compactados no destino com compressão zip.*/
        const val ZIP_COMPRESSION = 2
        /** Nenhuma opção selecionada.*/
        const val NO_OPTIONS = 0
        /** Os arquivos devem ser copiados.*/
        const val COPY_FILES = 1
    }

    /**
     * Valores de arquivos utilizados no [ApiConstEndpoints.REQ_CONFIG_SERVICE_LOG]
     */
    object FileOperationFiles{
        /** Arquivo de log do serviço.*/
        const val SVC_LOG = 1
        /**Arquivo de log da API.*/
        const val API_LOG = 4
        /** Arquivo do banco de dados do serviço.*/
        const val SVC_DATABASE = 2
    }

    /**
     * Valores do tipo de formulário.
     * @see ApiConstEndpoints.REQ_FORM_LIST
     */
    object FormTypeValues{
        /** Filtro desabilitado*/
        const val FILTER_DISABLED = 0L

        /** Binário; da UC fixa para a UC móvel */
        const val FIXED_TO_MOBILE_BINARY = 4L

        /**Texto; da UC fixa para a UC móvel. */
        const val FIXED_TO_MOBILE_TXT = 1L

        /** Binário; da UC móvel para a UC fixa;*/
        const val MOBILE_TO_FIXED_BINARY = 8L

        /** Texto; da UC móvel para a UC fixa; */
        const val MOBILE_TO_FIXED_TXT = 2L

        /** Binário; envio e recebimento tanto por UC móvel quanto por fixa; */
        const val SEND_RECEIVE_BINARY = 12L

        /** Texto; envio e recebimento tanto por UC móvel quanto por fixa; */
        const val	SEND_RECEIVE_TXT = 3L

    }

    /**
     * Valores da requisição de última posição.
     * @see ApiConstEndpoints.REQ_GET_POSITION_LAST
     */
    object PositionSourceType {
        /** Posição do Gps interno.*/
        const val GPS = 0
        /** Posição do Mct.*/
        const val MCT = 1
    }
}