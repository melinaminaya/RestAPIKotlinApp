package com.example.nanoclientkotlin.consts
/**
 * Identificadores das ações de notificação.
 * @author Melina Minaya
 */
object ActionValues {

    /**
     *
     * Esta acao foi substituida pela acao [ActionValues.MESSAGE_STATUS] e
     * sera removida em versões futuras.
     *
     */
    @Deprecated("")
    val MESSAGE_RECEIVED = 0x00

    /**
     * Existe uma mensagem a ser enviada.
     * @param Param1: Codigo da mensagem no banco de dados.
     * @param Param2: Nao utilizado.
     * @param Param3: Nao utilizado.
     * @param Param4: Nao utilizado.
     */
    const val MESSAGE_TO_SEND = 0x01

    /**
     * Esta acao foi substituida pela acao [ActionValues.MESSAGE_STATUS] e
     * sera removida em versões futuras.
     */
    @Deprecated("")
    val MESSAGE_SENT = 0x02

    /**
     * Um formulario foi recebido ou atualizado.
     * @param Param1 Codigo do formulario no banco de dados.
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val FORM_RECEIVED = 0x03

    /**
     * Um formulario foi removido.
     * @param Param1 Codigo do formulario no banco de dados.
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val FORM_DELETED = 0x04

    /**
     * A unidade se conectou ao servidor AMH. Ver protocolo Aap.
     * @param Param1 Status do SignOn.
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val SIGN_ON_STATUS = 0x05

    /**
     * Acoes relacionadas ao processo de batismo.
     * @param Param1 Acao a ser executada. Ver [ValuesBaptismActionParam1]
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val BAPTISM_ACTION = 0x06

    /**
     * Indica o status do processo de batismo.
     * @param Param1 Status do processo de batismo. Ver [ValuesBaptismStatusParam1]
     * @param Param2 Depende de Param1.
     * Se [ValuesBaptismStatusParam1.BAPTIZED],
     * [ValuesBaptismStatusParam1.WAITING_CONFIRMATION],
     * [ValuesBaptismStatusParam1.MCT_ADDR_RESPONSE] ou
     * [ValuesBaptismStatusParam1.MCT_NOT_AUTORIZED], Param2 sera o numero do Mct.
     * Se [ValuesBaptismStatusParam1.NOT_BAPTIZED],
     * [ValuesBaptismStatusParam1.IN_BAPTISM_PROCESS],
     * [ValuesBaptismStatusParam1.BAPTISM_TIMED_OUT], Param2 nao utilizado.
     * **Param3:** Nao utilizado.
     * **Param4:** Nao utilizado.
     */
    const val BAPTISM_STATUS = 0x07
    const val BAPTISM_STATUS_OBSERVABLE = "BAPTISM_STATUS"

    /**
     * Os arquivos de atualizacao foram baixados e estao prontos para iniciar
     * a atualizacao.
     * @param Param1 Nao utilizado.
     * @param Param2 Nao utilizado.
     * @param Param3 Indica a versao de atualizacao disponivel contida no nome do versionList.
     * @param Param4 Nao utilizado.
     */
    const val READY_TO_UPDATE_SOFTWARE = 0x08

    /**
     * Status das conexoes de rede.
     * @param Param1 Indica a rede. Ver [AmcuipValuesNetworkTypes]
     * @param Param2 Indica o status da conexao. Ver [AmcuipValuesConnectionStates].
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val NETWORK_CONNECTION_STATUS = 0x09

    /**
     * Mudanca no modo de comunicacao.
     * @param Param1 Modo atual de comunicacao. Ver [AmcuipValuesNetworkTypes]
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val COMMUNICATION_MODE_CHANGED = 0x0A

    /**
     * Mudanca no sinal do satelite.
     * @param Param1 Status do sinal.
     * @return 0: Sem sinal.
     *
     * 1: Com sinal.
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val SATELLITE_SIGNAL_CHANGED = 0x0B

    /**
     * Indica que um parametro (propriedade) deve ser enviado ao servidor.
     * Este parametro deve ser do tipo [AmcuipValuesParameterType.EXTERNAL]
     * @param Param1 Numero do parametro a ser enviado.
     * @param Param2 Nao utilizado.
     * @param Param3 Pode conter o valor do parametro. Neste caso o valor do parametro sera atualizado
     * no banco de dados antes de ser enviado ao servidor. Se este parametro nao estiver presente, o
     * valor corrente do parametro no banco de dados sera enviado ao servidor.
     * @param Param4 Nao utilizado.
     */
    const val UPDATE_SERVER_PARAMETER = 0x0C

    /**
     * Indica o status do processo de ativacao.
     * @param Param1 Status do processo de ativacao. Ver [AmcuipValuesActivationStatusParam1]
     * @param Param2 Depende de Param1.
     * Se ACTIVATED ou IN_ACTIVATION_PROCESS, Param2 E o numero da Uc.
     * Se Param1 for qualquer outro valor, Param2 nao E utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val ACTIVATION_STATUS = 0x0D

    /**
     * Solicita que os parametros do Mct (M0..M9) sejam atualizados no banco de dados.
     * @param Param1 Indica o periodo, em segundos, em que a atualizacao deve acontecer.
     * Ex.: se igual a 10*60, indica que a atualizacao deve ocorrer por 10 minutos.
     * @param Param2 Indica o intervalo, em segundos, entre cada atualizacao. Ex.: se
     * igual a 60, indica que a atualizacao deve ocorrer a cada minuto.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val UPDATE_MCT_M0_M9_PARAMS = 0x0E

    /**
     * Informa que os parametros do Mct (M0..M9) foram atualizados no banco de dados e
     * traz o valor de dois parametros M.
     * @param Param1 Valor do parametro M0.
     * @param Param2 Valor do parametro M2.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val MCT_M0_M9_PARAMS_UPDATED = 0x0F

    /**
     * Habilita ou desabilita o uso da rede WiFi pelo servico.
     * @param Param1 Habilita/desabilita o uso do WiFi
     * @return 0 - Habilita o uso do WiFi pelo servico.
     *
     * 1 - Desabilita o uso do WiFi pelo servico (aplicac�es externas podem utilizar o WiFi).
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val DISABLE_WIFI_COMMUNICATION = 0x10

    /**
     * Inicia ou cancela o processo de ativacao;
     * @param Param1 Acao a ser executada: Ver [AmcuipValuesActivationActionParam1].
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val ACTIVATION_ACTION = 0x11

    /**
     * Notifica que a Data/Hora do servico foi alterada.
     * @param Param1 Data/Hora atualizada, em segundos desde 01/01/1970 00:00:00 GMT.
     * @param Param2 Diferenca para a Data/Hora do sistema, em segundos. Pode ser um numero
     * positivo ou negativo. O resultado de Param1 - Param2 E igual a Data/Hora do sistema.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val DATE_TIME_CHANGED = 0x12

    /**
     * Instrui o servico a recarregar o valor de um parametro do banco de dados.
     * @param Param1 Numero do parametro a ser recarregado.
     * @param Param2 Tipo do parametro a ser recarregado. Ver [AmcuipValuesRealoadParamFromDbParam2].
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val RELOAD_PARAMETER_FROM_DB = 0x13

    //TODO: implement
    /**
     * Acoes relacionadas a operacoes com arquivos.
     * @param Param1 Tipo de operacao a ser realizada. Ver [AmcuipValuesFileOperationActionParam1].
     * @param Param2 Tipo de compressao utilizada. Ver [AmcuipValuesFileOperationActionParam2].
     * @param Param3 Nomes dos arquivos de origem, separados por "|". Podem ser utilizados caracteres curingas
     * (wildcards: "*", "?") para indicar varios arquivos que seguem um padrao.
     * Ex.: "/mnt/sdcard/Teste*.txt|/mnt/sdcard/Banco_num?.sqlite".
     * @param Param4 Depende da operacao e tipo de compressao:
     *
     *  * **DestDirectory**: Quando a acao for [AmcuipValuesFileOperationActionParam1.COPY]
     * ou [AmcuipValuesFileOperationActionParam1.MOVE] e o tipo de compressao for
     * [AmcuipValuesFileOperationActionParam2.NO_COMPRESSION].
     *
     *  * **DestFilename**: Quando a acao for [AmcuipValuesFileOperationActionParam1.COPY]
     * ou [AmcuipValuesFileOperationActionParam1.MOVE] e o tipo de compressao for **diferente** de
     * [AmcuipValuesFileOperationActionParam2.NO_COMPRESSION], ou seja, quando algum tipo de compressao
     * for utilizado.
     *
     *  * **Nao utilizado**: quando a acao for [AmcuipValuesFileOperationActionParam1.DELETE].
     *
     *
     */
    const val FILE_OPERATION_ACTION = 0x14
    //TODO: implement
    /**
     * Indica o status de uma operacao com arquivos iniciada anteriormente.
     * @param Param1 Status da operacao com arquivos. Ver [AmcuipValuesFileOperationStatusParam1]
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val FILE_OPERATION_STATUS = 0x15

    /**
     * Indica o status de uma mensagem no banco de dados. Esta acao pode ser utilizada quando uma nova mensagem
     * for recebida ou quando uma mensagem que foi postada para envio acabou de ser enviada, alem de outros
     * status. Uma mensagem nao lida tera o status [MessageStatusValues.NOT_READ]. Uma mensagem
     * que foi enviada tera o status [MessageStatusValues.SENT].
     * @param Param1 Codigo da mensagem no banco de dados.
     * @param Param2 Status da mensagem.
     *
     * Este campo deve ser utilizado para distinguir entre os diferentes status
     * de uma mensagem. Ver [MessageStatusValues].
     *
     * Este campo deve ser tratado como um inteiro de 32bits com sinal (signed int).
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val MESSAGE_STATUS = 0x16

    /** Valores validos para status de mensagens. */
    object MessageStatusValues {
        /** Nenhum status associado.  */
        const val NONE = 0

        /** A enviar. **Valido somente para mensagens de Envio.**  */
        const val TO_SEND = 1

        /** Enviada. **Valido somente para mensagens de Envio.**  */
        const val SENT = 2

        /** Mensagem recebida nao lida. **Valido somente para mensagens de retorno.**  */
        const val NOT_READ = 3

        /** Mensagem recebida lida. **Valido somente para mensagens de retorno.**  */
        const val READ = 4

        /** Nao processada. **Valido somente para mensagens de Envio.**  */
        const val NOT_PROCESSED = 5

        /**
         * Transmitida. Acontece quando uma mensagem ja foi transmitida pela rede satelital.
         * Depois que ha a confirmacao de entrega pelo servidor, ela E marcada como enviada.
         * **Valido sómente para mensagens de Envio.**
         */
        const val TRANSMITTED = 6

        /**
         * Mensagem longa (Out Of Band) recebida nao processada (seus arquivos ainda nao foram baixados).
         * Sera processada e marcada como [AmcuipValuesMessageStatus.NOT_READ]
         * quando os arquivos forem baixados do servidor.
         * **Valido sómente para mensagens de Retorno.**
         */
        const val NOT_READ_OUT_OF_BAND = 7

        /**
         * Em transmissao. A mensagem esta em transmissao pela rede satelital, seu envio esta em
         * progresso, a mensagem ainda nao foi completamente transmitida pelo dispositivo.
         * Quando a mensagem for transmitida, ela sera marcada como [AmcuipValuesMessageStatus.TRANSMITTED].
         * **Valido sómente para mensagens de Envio.**
         */
        const val TRANSMITTING = 8
        // Status acima de 1000 serao tratados como status informativos.
        /**
         * Sinaliza que uma mensagem do tipo Serial esta bloqueando a fila de mensagens por ser muito grande para a
         * rede satelital.
         *
         * Uma mensagem do tipo Serial pode ser muito grande para ser enviada via satElite, mas pode ser enviada
         * via celular (quando configurada para ser enviada por qualquer rede disponivel). No entanto, caso nao
         * haja rede celular disponivel no momento para envio, a mensagem bloquea a fila.
         * A fila retorna ao normal quando a rede celular estiver disponivel novamente.
         *
         * **Status apenas informativo, nao sera gravado no banco de dados.**
         */
        const val SERIAL_MESSAGE_TOO_BIG_WAITING = 1000

        /**
         * Sinaliza que uma mensagem do tipo Serial esta bloqueando a fila de mensagens porque o tipo de rede
         * escolhido para o envio da mensagem nao esta disponivel.
         *
         * Uma mensagem do tipo Serial que possui o tipo de canal de transmissao igual a 'Cellular', somente será enviada
         * quando houver rede celular disponivel.
         *
         * **Status apenas informativo, nao sera gravado no banco de dados.**
         */
        const val SERIAL_MESSAGE_CELL_NET_UNAVAILABLE = 1001

        /** Mensagem duplicada/descartada.  */
        const val MESSAGE_DUPLICATE = -5514

        /** Mensagem muito grande para ser transmitida para o receptor. **Valido somente para mensagens de Envio.**  */
        const val MESSAGE_TOO_BIG = -5531

        /** Mensagem OutOfBand: Arquivo nao encontrado.  */
        const val OUT_OF_BAND_FILE_NOT_FOUND = -5561

        /** Erro desconhecido.  */
        const val UNKNOWN_ERROR = -5599
    }
    /**
     * Indica o status de requisicao de algum recurso do sistema. Esta acao pode ser utilizada para informar
     * status de requisicoes de recursos do sistema, mas geralmente E utilizada para informar quando algum recurso
     * do sistema que E necessario ao bom funcionamento da aplicacao nao pode ser obtido.
     * @param Param1 Tipo do recurso requisitado. Ver [AmcuipValuesSysResourceReqParam1].
     * @param Param2 Status da requisicao. Ver [AmcuipValuesSysResourceStatusParam2].
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val SYSTEM_RESOURCE_REQ_STATUS = 0x17

    /**
     * Indica o status da ignicao do dispositivo externo.
     * @param Param1 Status da ignicao. Ver [AmcuipValuesIgnitionStatusParam1]
     * @param Param2 Nao utilizado.
     * @param Param3 Nao utilizado.
     * @param Param4 Nao utilizado.
     */
    const val IGNITION_STATUS = 0x18

    /** Valores possiveis para o parametro Param2 da acao [ActionValues.RELOAD_PARAMETER_FROM_DB]. */
    object AmcuipValuesRealoadParamFromDbParam2 {
        /** Parametro exclusivo do servico de comunicacao.  */
        const val SERVICE = 0

        /** Parametro externo, armazenado no servidor.  */
        const val EXTERNAL = 1

        /** Parametro compartilhado entre o servico e a interface de usuario.  */
        const val SERVICE_INTERFACE = 2
    }

    /**
     * Operacoes possiveis de serem executadas na acao [ActionValues.BAPTISM_ACTION].
     */
    object ValuesBaptismActionParam1 {
        /** Inicia o processo de batismo.  */
        const val DO_BAPTISM = 0

        /** Consulta o numero do Mct.  */
        const val CONSULT_MCT_ADDR = 1

        /** Desfaz o processo de batismo.  */
        const val UNDO_BAPTISM = 2
    }

    /** Status possiveis retornados pelo Param1 de Acao [ActionValues.BAPTISM_STATUS].  */
    object ValuesBaptismStatusParam1 {
        /** A Uc ainda nao foi batizada.  */
        const val NOT_BAPTIZED = 0

        /** A Uc ja esta batizada.  */
        const val BAPTIZED = 1

        /** O processo de batismo esta em execucao.  */
        const val IN_BAPTISM_PROCESS = 2

        /** Aguardando pela confirmacao do batismo pelo servidor.  */
        const val WAITING_CONFIRMATION = 3

        /** Resposta da consulta do n�mero do Mct.  */
        const val MCT_ADDR_RESPONSE = 4

        /**
         * O Mct conectado nao esta autorizado a se comunicar.
         * A Uc esta batizada com outro Mct.
         */
        const val MCT_NOT_AUTORIZED = 5

        /** Houve timeout no processo de batismo.  */
        const val BAPTISM_TIMED_OUT = 6
    }

    /** Tipos de redes de comunicacao disponiveis.  */
    object AmcuipValuesNetworkTypes {
        /** Nenhuma/Sem conexao.  */
        const val NONE = 0

        /** Celular.  */
        const val CELLULAR = 1

        /** SatElite.  */
        const val SATELLITE = 2
    }

    /** Tipos possiveis de conexao.  */
    object AmcuipValuesConnectionStates {
        /** Conexao fisica no estado desconectado.  */
        const val PHYSICAL_DISCONNECTED = 0

        /** Conexao fisica estabelecida.  */
        const val PHYSICAL_CONNECTED = 1

        /** Conexao logica no estado desconectado.  */
        const val LOGICAL_DISCONNECTED = 2

        /** Conexao logica estabelecida (a troca de pacotes iniciada).  */
        const val LOGICAL_CONNECTED = 3
    }

    /**
     * Operacoes possiveis de serem executadas na acao [ActionValues.ACTIVATION_ACTION].
     */
    object AmcuipValuesActivationActionParam1 {
        /** Inicia o processo de ativacao.  */
        const val START_ACTIVATION = 0

        /** Cancela o processo de ativacao.  */
        const val CANCEL_ACTIVATION = 1
    }

    /** Status possiveis retornados pelo Param1 da acao [ActionValues.ACTIVATION_STATUS].  */
    object AmcuipValuesActivationStatusParam1 {
        /** A Uc ainda nao foi ativada.  */
        const val NOT_ACTIVATED = 0

        /** A Uc ja esta ativada.  */
        const val ACTIVATED = 1

        /** O processo de ativacao esta em execucao.  */
        const val IN_ACTIVATION_PROCESS = 2

        /** A chave de ativacao fornecida nao E valida.  */
        const val INVALID_ACTIVATION_KEY = 3

        /** Senha de ativacao, gerada a partir da chave de ativacao, invalida!  */
        const val INVALID_ACTIVATION_PASSOWRD = 4
    }

    /** Status posiveis retornados pelo Param1 da acao [ActionValues.FILE_OPERATION_STATUS].  */
    object AmcuipValuesFileOperationStatusParam1 {
        /** O arquivo foi transferido com sucesso.  */
        const val SUCCESS = 0

        /** Erro na transferencia do arquivo.  */
        const val ERROR = 1
    }

    /** Status posiveis retornados pelo Param1 da acao [ActionValues.IGNITION_STATUS].  */
    object AmcuipValuesIgnitionStatusParam1 {
        /** A ignicao esta desligada.  */
        const val OFF = 0

        /** A ignicao esta ligada.  */
        const val ON = 1
    }

    /** Status posiveis retornados pelo Param1 da acao [ActionValues.FILE_OPERATION_ACTION].  */
    object AmcuipValuesFileOperationActionParam1 {
        /** Indica que os arquivos deverao ser copiados.  */
        const val COPY = 0

        /** Indica que os arquivos deverao ser movidos.  */
        const val MOVE = 1

        /** Indica que os arquivos deverao ser apagados.  */
        const val DELETE = 2
    }

    /** Status posiveis retornados pelo Param2 da acao [ActionValues.FILE_OPERATION_ACTION].  */
    object AmcuipValuesFileOperationActionParam2 {
        /** Indica que os arquivos deverao ser copiados.  */
        const val NO_COMPRESSION = 0

        /** Indica que os arquivos deverao ser movidos.  */
        const val ZIP = 1
    }

    /** Possiveis recursos do sistema cujos status da solicitacao podem ser informados.  */
    object AmcuipValuesSysResourceReqParam1 {
        //      /** Alterar configurac�es de conexao/modem (ativar dados/dados em roaming, etc.). */
        //      public static final int MODEM_CONFIGURATION = 0;
        /** Configuracao de Nomes de Pontos de Acesso.  */
        const val APN_CONFIGURATION = 1

        /** Configuracao do GPS (inclui ligar/desligar/configurar posicao precisa).  */
        const val GPS_CONFIGURATION = 2 //      /** Configuracao de Hot Spot WiFi. */
        //      public static final int WIFI_HOTSPOT_CONFIGURATION = 3;
    }

    /** Possiveis status dos recursos solicitados ao sistema.  */
    object AmcuipValuesSysResourceStatusParam2 {
        /** Sucesso, nenhum erro.  */
        const val SUCCESS = 0

        /** Erro desconhecido.  */
        const val UNKNOWN_ERROR = 1

        /** Sem permissao para obter o recurso.  */
        const val PERMISSION_DENIED = 2

        /** Recurso nao disponivel.  */
        const val NOT_AVAILABLE = 3
    }
    object FileOperationFiles{
        /** Arquivo de log do serviço.*/
        const val SVC_LOG = 1
        /**Arquivo de log da API.*/
        const val API_LOG = 4
        /** Arquivo do banco de dados do serviço.*/
        const val SVC_DATABASE = 2
    }
    object FileOperationOptions{
        /** Os arquivos devem ser copiados.*/
        const val COPY_FILES = 1
        /** Nenhuma opçao selecionada.*/
        const val NO_OPTIONS = 0
        /** Os arquivos devem ser compactados no destino com compressao zip.*/
        const val ZIP_COMPRESSION = 2
    }
    object FormTypeValues{
        /** Filtro desabilitado*/
        const val FILTER_DISABLED = 0

        /** Binário; da UC fixa para a UC movel */
        const val FIXED_TO_MOBILE_BINARY = 4

        /**Texto; da UC fixa para a UC movel. */
        const val FIXED_TO_MOBILE_TXT = 1

        /** Binario; da UC movel para a UC fixa;*/
        const val MOBILE_TO_FIXED_BINARY = 8

        /** Texto; da UC movel para a UC fixa; */
        const val MOBILE_TO_FIXED_TXT = 2

        /** Binario; envio e recebimento tanto por UC movel quanto por fixa; */
        const val SEND_RECEIVE_BINARY = 12

        /** Texto; envio e recebimento tanto por UC movel quanto por fixa; */
        const val	SEND_RECEIVE_TXT = 3

    }
    object PositionSourceType {
        /** Posicao do Gps interno.*/
        const val GPS = 0
        /** Posicao do Mct.*/
        const val MCT = 1
    }
}