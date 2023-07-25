package com.example.nanoclientkotlin.consts
/**
 * Identificadores das ac�es.
 *
 * **S->I** significa envio no sentido Servico para Interface.
 * **I->S** significa envio no sentido Interface para Servico.
 *
 * @author rhobison.pereira
 */
object AmcuipValuesActionIds {
    /*
    * <b>S->I</b><br>
    * Uma nova mensagem foi recebida.<br>
    * <b>Param1:</b> C�digo da mensagem no banco de dados.<br>
    * <b>Param2:</b> Nao utilizado.<br>
    * <b>Param3:</b> Nao utilizado.<br>
    * <b>Param4:</b> Nao utilizado.<br>
    */
    /**
     *
     * Esta acao foi substituida pela acao [AmcuipValuesActionIds.MESSAGE_STATUS] e
     * sera removida em vers�es futuras.
     */
    @Deprecated("")
    val MESSAGE_RECEIVED = 0x00

    /**
     * **I->S**<br></br>
     * Existe uma mensagem a ser enviada.<br></br>
     * **Param1:** C�digo da mensagem no banco de dados.<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val MESSAGE_TO_SEND = 0x01
    /*
    * <b>S->I</b><br>
    * Uma mensagem foi enviada/transmitida.<br>
    * <b>Param1:</b> C�digo da mensagem no banco de dados.<br>
    * <b>Param2:</b> Nao utilizado.<br>
    * <b>Param3:</b> Nao utilizado.<br>
    * <b>Param4:</b> Nao utilizado.<br>
    */
    /**
     * Esta acao foi substituida pela acao [AmcuipValuesActionIds.MESSAGE_STATUS] e
     * sera removida em vers�es futuras.
     */
    @Deprecated("")
    val MESSAGE_SENT = 0x02

    /**
     * **S->I**<br></br>
     * Um formulario foi recebido ou atualizado.<br></br>
     * **Param1:** C�digo do formulario no banco de dados.<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val FORM_RECEIVED = 0x03

    /**
     * **S->I**<br></br>
     * Um formulario foi removido.<br></br>
     * **Param1:** C�digo do formulario no banco de dados.<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val FORM_DELETED = 0x04

    /**
     * **S->I**<br></br>
     * A unidade se conectou ao servidor AMH. Ver protocolo Aap.<br></br>
     * **Param1:** Status do SignOn.<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val SIGN_ON_STATUS = 0x05

    /**
     * **I->S**<br></br>
     * Ac�es relacionadas ao processo de batismo.<br></br>
     * **Param1:** Acao a ser executada. Ver [AmcuipValuesBaptismActionParam1]<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val BAPTISM_ACTION = 0x06

    /**
     * **S->I**<br></br>
     * Indica o status do processo de batismo.<br></br>
     * **Param1:** Status do processo de batismo. Ver [AmcuipValuesBaptismStatusParam1]<br></br>
     * **Param2:** Depende de Param1.<br></br>
     * Se [AmcuipValuesBaptismStatusParam1.BAPTIZED],
     * [AmcuipValuesBaptismStatusParam1.WAITING_CONFIRMATION],
     * [AmcuipValuesBaptismStatusParam1.MCT_ADDR_RESPONSE] ou
     * [AmcuipValuesBaptismStatusParam1.MCT_NOT_AUTORIZED], Param2 sera o n�mero do Mct.
     * Se [AmcuipValuesBaptismStatusParam1.NOT_BAPTIZED],
     * [AmcuipValuesBaptismStatusParam1.IN_BAPTISM_PROCESS],
     * [AmcuipValuesBaptismStatusParam1.BAPTISM_TIMED_OUT], Param2 nao E utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val BAPTISM_STATUS = 0x07

    /**
     * **S->I**<br></br>
     * Os arquivos de atualizacao foram baixados e estao prontos para iniciar
     * a atualizacao.<br></br>
     * **Param1:** Nao utilizado.<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Indica a versao de atualizacao disponivel contida no nome do versionList.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val READY_TO_UPDATE_SOFTWARE = 0x08

    /**
     * **S->I**<br></br>
     * Status das conex�es de rede.<br></br>
     * **Param1:** Indica a rede. Ver [AmcuipValuesNetworkTypes]<br></br>
     * **Param2:** Indica o status da conexao. Ver [AmcuipValuesConnectionStates].<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val NETWORK_CONNECTION_STATUS = 0x09

    /**
     * **S->I**<br></br>
     * Mudanca no modo de comunicacao.<br></br>
     * **Param1:** Modo atual de comunicacao. Ver [AmcuipValuesNetworkTypes]<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val COMMUNICATION_MODE_CHANGED = 0x0A

    /**
     * **S->I**<br></br>
     * Mudanca no sinal do satElite.<br></br>
     * **Param1:** Status do sinal<br></br>
     * 0 � Sem sinal.<br></br>
     * 1 � Com sinal.<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val SATELLITE_SIGNAL_CHANGED = 0x0B

    /**
     * **I->S**<br></br>
     * Indica que um parametro (propriedade) deve ser enviado ao servidor.
     * Este parametro deve ser do tipo [AmcuipValuesParameterType.EXTERNAL]<br></br>
     * **Param1:** N�mero do parametro a ser enviado.<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Pode conter o valor do parametro. Neste caso o valor do parametro sera atualizado
     * no banco de dados antes de ser enviado ao servidor. Se este parametro nao estiver presente, o
     * valor corrente do parametro no banco de dados sera enviado ao servidor.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val UPDATE_SERVER_PARAMETER = 0x0C

    /**
     * **S->I**<br></br>
     * Indica o status do processo de ativacao.<br></br>
     * **Param1:** Status do processo de ativacao. Ver [AmcuipValuesActivationStatusParam1]<br></br>
     * **Param2:** Depende de Param1.<br></br>
     * Se ACTIVATED ou IN_ACTIVATION_PROCESS, Param2 E o n�mero da Uc.
     * Se Param1 for qualquer outro valor, Param2 nao E utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val ACTIVATION_STATUS = 0x0D

    /**
     * **I->S**<br></br>
     * Solicita que os parametros do Mct (M0..M9) sejam atualizados no banco de dados.<br></br>
     * **Param1:** Indica o periodo, em segundos, em que a atualizacao deve acontecer.
     * Ex.: se igual a 10*60, indica que a atualizacao deve ocorrer por 10 minutos.<br></br>
     * **Param2:** Indica o intervalo, em segundos, entre cada atualizacao. Ex.: se
     * igual a 60, indica que a atualizacao deve ocorrer a cada minuto.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val UPDATE_MCT_M0_M9_PARAMS = 0x0E

    /**
     * **S->I**<br></br>
     * Informa que os parametros do Mct (M0..M9) foram atualizados no banco de dados e
     * traz o valor de dois parametros M.<br></br>
     * **Param1:** Valor do parametro M0.<br></br>
     * **Param2:** Valor do parametro M2.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val MCT_M0_M9_PARAMS_UPDATED = 0x0F

    /**
     * **I->S**<br></br>
     * Habilita ou desabilita o uso da rede WiFi pelo servico.<br></br>
     * **Param1:** Habilita/desabilita o uso do WiFi<br></br>
     * 0 - Habilita o uso do WiFi pelo servico.<br></br>
     * 1 - Desabilita o uso do WiFi pelo servico (aplicac�es externas podem utilizar o WiFi).<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val DISABLE_WIFI_COMMUNICATION = 0x10

    /**
     * **I->S**<br></br>
     * Inicia ou cancela o processo de ativacao;<br></br>
     * **Param1:** Acao a ser executada: Ver [AmcuipValuesActivationActionParam1].<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val ACTIVATION_ACTION = 0x11

    /**
     * **S->I**<br></br>
     * Notifica que a Data/Hora do servico foi alterada.<br></br>
     * **Param1:** Data/Hora atualizada, em segundos desde 01/01/1970 00:00:00 GMT.<br></br>
     * **Param2:** Diferenca para a Data/Hora do sistema, em segundos. Pode ser um n�mero
     * positivo ou negativo. O resultado de Param1 - Param2 E igual a Data/Hora do sistema.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val DATE_TIME_CHANGED = 0x12

    /**
     * **I->S** <br></br>
     * Instrui o servico a recarregar o valor de um parametro do banco de dados.<br></br>
     * **Param1:** N�mero do parametro a ser recarregado.
     * **Param2:** Tipo do parametro a ser recarregado. Ver [AmcuipValuesRealoadParamFromDbParam2]. <br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val RELOAD_PARAMETER_FROM_DB = 0x13

    /**
     * **I->S**<br></br>
     * Ac�es relacionadas a operac�es com arquivos.<br></br>
     * **Param1:** Tipo de operacao a ser realizada. Ver [AmcuipValuesFileOperationActionParam1]. <br></br>
     * **Param2:** Tipo de compressao utilizada. Ver [AmcuipValuesFileOperationActionParam2]. <br></br>
     * **Param3:** Nomes dos arquivos de origem, separados por "|". Podem ser utilizados caracteres curingas
     * (wildcards: "*", "?") para indicar varios arquivos que seguem um padrao.
     * Ex.: "/mnt/sdcard/Teste*.txt|/mnt/sdcard/Banco_num?.sqlite".<br></br>
     * **Param4:** Depende da operacao e tipo de compressao:<br></br>
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

    /**
     * **S->I**<br></br>
     * Indica o status de uma operacao com arquivos iniciada anteriormente. <br></br>
     * **Param1:** Status da operacao com arquivos. Ver [AmcuipValuesFileOperationStatusParam1]<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val FILE_OPERATION_STATUS = 0x15

    /**
     * **S->I**<br></br>
     * Indica o status de uma mensagem no banco de dados. Esta acao pode ser utilizada quando uma nova mensagem
     * for recebida ou quando uma mensagem que foi postada para envio acabou de ser enviada, alEm de outros
     * status. Uma mensagem nao lida tera o status [AmcuipValuesMessageStatus.NOT_READ]. Uma mensagem
     * que foi enviada tera o status [AmcuipValuesMessageStatus.SENT]. <br></br>
     * **Param1:** C�digo da mensagem no banco de dados.<br></br>
     * **Param2:** Status da mensagem. Este campo deve ser utilizado para distinguir entre os diferentes status
     * de uma mensagem. Ver [AmcuipValuesMessageStatus]. Este campo deve ser tratado como um inteiro de
     * 32bits com sinal (signed int).<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val MESSAGE_STATUS = 0x16

    /**
     * **S->I**<br></br>
     * Indica o status de requisicao de algum recurso do sistema. Esta acao pode ser utilizada para informar
     * status de requisic�es de recursos do sistema, mas geralmente E utilizada para informar quando algum recurso
     * do sistema que E necessario ao bom funcionamento da aplicacao nao pode ser obtido.<br></br>
     * **Param1:** Tipo do recurso requisitado. Ver [AmcuipValuesSysResourceReqParam1].<br></br>
     * **Param2:** Status da requisicao. Ver [AmcuipValuesSysResourceStatusParam2].<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val SYSTEM_RESOURCE_REQ_STATUS = 0x17

    /**
     * **S->I**<br></br>
     * Indica o status da ignicao do dispositivo externo. <br></br>
     * **Param1:** Status da ignicao. Ver [AmcuipValuesIgnitionStatusParam1]<br></br>
     * **Param2:** Nao utilizado.<br></br>
     * **Param3:** Nao utilizado.<br></br>
     * **Param4:** Nao utilizado.<br></br>
     */
    const val IGNITION_STATUS = 0x18

    /** Valores possiveis para o parametro Param2 da acao [AmcuipValuesActionIds.RELOAD_PARAMETER_FROM_DB]. */
    object AmcuipValuesRealoadParamFromDbParam2 {
        /** Parametro exclusivo do servico de comunicacao.  */
        const val SERVICE = 0

        /** Parametro externo, armazenado no servidor.  */
        const val EXTERNAL = 1

        /** Parametro compartilhado entre o servico e a interface de usuario.  */
        const val SERVICE_INTERFACE = 2
    }

    /**
     * Operac�es possiveis de serem executadas na acao [AmcuipValuesActionIds.BAPTISM_ACTION].
     */
    object AmcuipValuesBaptismActionParam1 {
        /** Inicia o processo de batismo.  */
        const val DO_BAPTISM = 0

        /** Consulta o n�mero do Mct.  */
        const val CONSULT_MCT_ADDR = 1

        /** Desfaz o processo de batismo.  */
        const val UNDO_BAPTISM = 2
    }

    /** Status possiveis retornados pelo Param1 de Acao [AmcuipValuesActionIds.BAPTISM_STATUS].  */
    object AmcuipValuesBaptismStatusParam1 {
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

        /** Conexao l�gica no estado desconectado.  */
        const val LOGICAL_DISCONNECTED = 2

        /** Conexao l�gica estabelecida (a troca de pacotes iniciada).  */
        const val LOGICAL_CONNECTED = 3
    }

    /**
     * Operac�es possiveis de serem executadas na acao [AmcuipValuesActionIds.ACTIVATION_ACTION].
     */
    object AmcuipValuesActivationActionParam1 {
        /** Inicia o processo de ativacao.  */
        const val START_ACTIVATION = 0

        /** Cancela o processo de ativacao.  */
        const val CANCEL_ACTIVATION = 1
    }

    /** Status possiveis retornados pelo Param1 da acao [AmcuipValuesActionIds.ACTIVATION_STATUS].  */
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

    /** Status posiveis retornados pelo Param1 da acao [AmcuipValuesActionIds.FILE_OPERATION_STATUS].  */
    object AmcuipValuesFileOperationStatusParam1 {
        /** O arquivo foi transferido com sucesso.  */
        const val SUCCESS = 0

        /** Erro na transfer�ncia do arquivo.  */
        const val ERROR = 1
    }

    /** Status posiveis retornados pelo Param1 da acao [AmcuipValuesActionIds.IGNITION_STATUS].  */
    object AmcuipValuesIgnitionStatusParam1 {
        /** A ignicao esta desligada.  */
        const val OFF = 0

        /** A ignicao esta ligada.  */
        const val ON = 1
    }

    /** Status posiveis retornados pelo Param1 da acao [AmcuipValuesActionIds.FILE_OPERATION_ACTION].  */
    object AmcuipValuesFileOperationActionParam1 {
        /** Indica que os arquivos deverao ser copiados.  */
        const val COPY = 0

        /** Indica que os arquivos deverao ser movidos.  */
        const val MOVE = 1

        /** Indica que os arquivos deverao ser apagados.  */
        const val DELETE = 2
    }

    /** Status posiveis retornados pelo Param2 da acao [AmcuipValuesActionIds.FILE_OPERATION_ACTION].  */
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
}