package br.com.autotrac.testnanoclient.consts

/**
 * Valores para parâmetros da integração.
 * Cada parâmetro pode retornar um valor específico.
 * @author Melina Minaya
 */
class ParameterValues {
    companion object{



        val listExtDevCommType = listOf(
            ExternalDeviceCommunicationTypeValues.RS232,
            ExternalDeviceCommunicationTypeValues.SOCKET,
            ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_HOTSPOT,
            ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET,
            ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET_WIFI_HOTSPOT,
            ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_CLIENT
        )
    }



    /**
     * Valores possíveis para os tipos de comunicação (meio físico) a ser
     * utilizado com o dispositivo externo.
     *@see [ApiEndpoints.GET_PARAM_EXT_DEV_COMM_TYPE]
     *@see [ApiEndpoints.SET_PARAM_EXT_DEV_COMM_TYPE]
     * */
    object ExternalDeviceCommunicationTypeValues {
        /** Utilizar comunicação via interface serial RS232.  */
        const val RS232 = 0

        /** Utilizar comunicação via socket, com acesso direto à rede (sem NetworkAccess).  */
        const val SOCKET = 1

        /** Utilizar comunicação via socket, com acesso à rede via HotSpot WiFi.  */
        const val SOCKET_WIFI_HOTSPOT = 2

        /**
         * Utilizar comunicação via interface serial RS232 ou comunicação via socket, com acesso direto
         * à rede (sem NetworkAccess) de acordo com a demanda (quando um falhar o outro será utilizado).
         */
        const val RS232_AND_SOCKET = 3

        /**
         * Utilizar comunicação via interface serial RS232 ou comunicação via socket, com acesso à
         * rede via HotSpot WiFi de acordo com a demanda (quando um falhar o outro será utilizado).
         */
        const val RS232_AND_SOCKET_WIFI_HOTSPOT = 4

        /** Utilizar comunicação via socket, com acesso à rede via Cliente WiFi.  */
        const val SOCKET_WIFI_CLIENT = 5

        /**
         * Utilizar comunicação via interface serial RS232 ou comunicação via socket, com acesso à
         * rede via Cliente WiFi de acordo com a demanda (quando um falhar o outro será utilizado).
         */
        const val RS232_AND_SOCKET_WIFI_CLIENT = 6
    }
    /**
     * Valores possíveis para o parâmetro:
     * @see ApiEndpoints.GET_PARAM_UC_SUBTYPE
     * @see ApiEndpoints.GET_PARAM_WIFI_SSID
     * @see ApiEndpoints.SET_PARAM_WIFI_SSID
     * */
    object CmuSubtypeValues {

        /** Produto com comunicação exclusiva via satélite.  */
        const val MOBILE_SATELLITE = -1

        /** UC com comunicação celular.  */
        const val MOBILE = 10

        /** Uc com comunicação celular e satélite.  */
        const val PRIME_MOBILE = 11
    }

    /** Tipos de redes de comunicação disponiveis.
     * @see ApiEndpoints.GET_PARAM_CURRENT_COMM_MODE
     * */
    object ValuesNetworkTypes {
        /** Nenhuma/Sem conexão.  */
        const val NONE = 0

        /** Celular.  */
        const val CELLULAR = 1

        /** Satélite.  */
        const val SATELLITE = 2
    }

    /** Tipos possiveis de conexão.
     * @see ApiEndpoints.GET_PARAM_LAST_SAT_CONNECTION_STATUS
     * */
    object ValuesConnectionStates {
        /** Conexão fisica no estado desconectado.  */
        const val PHYSICAL_DISCONNECTED = 0

        /** Conexão fisica estabelecida.  */
        const val PHYSICAL_CONNECTED = 1

        /** Conexão lógica no estado desconectado.  */
        const val LOGICAL_DISCONNECTED = 2

        /** Conexão lógica estabelecida (a troca de pacotes iniciada).  */
        const val LOGICAL_CONNECTED = 3
    }

    /**
     * Valores possíveis para o [ApiEndpoints.GET_PARAM_HAS_UPDATE_PENDING].
     * */
    object HasUpdatePendingValues {
        /** Nenhuma solicitação de atualização realizada.  */
        const val NO_REQUEST = 0

        /** Solicitação de atualização realizada.  */
        const val PENDING_REQUEST = 1

        /** Baixando atualização.  */
        const val DOWNLOADING = 2

        /** Atualização baixada e pronta para atualizar.  */
        const val DOWNLOADED = 3

        /** Em processo de atualização.  */
        const val IN_PROCESS = 4

        /** Atulização baixada e pronta para atulizar após o boot do aparelho.  */
        const val WAITING_BOOT = 5
    }
    /**
     * Possíveis valores para [ApiEndpoints.GET_PARAM_HW_CONTROL_DISABLE].
     * Mapa de bits. Pode ser uma combinação de um dos valores.
     * Um bit setado indica que o gerenciamento do dispositivo em questão
     * não deve ser executado.
     * */
    object HWControlDisableValues {
        /** Todos os gerenciamentos estão ativos (nenhum gerenciamento desativado).  */
        const val NONE = 0x00

        /** Desabilita o gerenciamento do Hardware Celular/APNs/Dados/Dados em roaming/Modo avi�o.  */
        const val CELLULAR = 0x01

        /** Desabilita o gerenciamento do Hardware WiFi/Hotspot.  */
        const val WIFI = 0x02

        /** Desabilita o gerenciamento do Hardware GPS/GNSS.  */
        const val GNSS = 0x04

        /** Desabilita o controle do modo avião.  */
        const val AIR_PLANE = 0x08
    }

    /** Indica se o Mct está com sinal ou não (=0 sem sinal, =1 com sinal).
     * @see [ApiEndpoints.GET_PARAM_HAS_SATELLITE_SIGNAL]
     * */
    object MctSignalValues {
        const val MCT_SIGNAL_OFF = 0
        const val MCT_SIGNAL_ON = 1

    }

    /**
     * Indica se o uso da VPN pelo serviço de comunicação é permitido ou não.
     *@see [ApiEndpoints.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]
     *@see [ApiEndpoints.SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]
     * O valor 1 desabilita o uso da VPN pelo serviço de comunicação e o valor 0 habilita.
     * Esta opção deve ser utilizada quando operações envolvendo o uso simultâneo do HotSpot WiFi e da conexão celular.
     * Logo que possível o uso da VPN deve ser habilitado (configurando o valor deste parâmetro como 0), caso seja desejável o seu uso.
     */
    object VpnDisableValues {
        const val ENABLE_VPN = 0
        const val DISABLE_VPN = 1
    }
    /**
     * Indica o status atual da conexão VPN (=0 desconectada, =1 conectada).
     *
     * @see [ApiEndpoints.GET_PARAM_VPN_CONNECTION_STATUS]
     */
    object VpnConnectionStatusValues {
        const val VPN_DISCONNECTED = 0
        const val VPN_CONNECTED = 1

    }

    /**
     * Desabilita o uso da rede WiFi. (=0 habilita o uso, =1 desabilita o uso. Aplicações externas podem utilizar o
     * WiFi.)
     *@see [ApiEndpoints.GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]
     *@see [ApiEndpoints.SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]
     */
    object WifiDisableValues {
        const val ENABLE_WIFI = 0
        const val DISABLE_WIFI = 1
    }

    /**
     * Valores possíveis para o status atualizado da Uc Móvel
     * após a tentativa de estabelecimento de comunicação via celular com o servidor.
     */
    object UcStatusValues{
        /**UC habilitada, mas com a licença de uso Inativa, porque a senha foi alterada no servidor.*/
        const val INVALID_USER_OR_PASSWORD = 2
        /** Uc desabilitada. */
        const val UC_DISABLED = 1
        /** Uc habilitada e com a licença de uso ativada. */
        const val UC_ENABLED = 0
        /** Status desconhecido. */
        const val UNKNOWN = 3
    }
}