package com.example.nanoclientkotlin.consts

class ParameterValues {
    companion object{

        /**
         * Valores possiveis para o parametro PARAM_BAPTISM_STATUS.
         */

            /** A Uc ainda não foi batizada.  */
            const val NOT_BAPTIZED = 0

            /** A Uc já está batizada.  */
            const val BAPTIZED = 1

            /** O processo de batismo está em execução.  */
            const val IN_BAPTISM_PROCESS = 2

            /** Aguardando pela confirmação do batismo pelo servidor.  */
            const val WAITING_CONFIRMATION = 3

            /** Apenas consulta o número do dispositivo de comunicação alternativo.  */
            const val CONSULT_ALT_COMM_DEVICE_ADDR = 4

        /** Tipos possíveis de conexão. */
            /** Conexão física no estado desconectado.  */
            const val PHYSICAL_DISCONNECTED = 0

            /** Conexão física estabelecida.  */
            const val PHYSICAL_CONNECTED = 1

            /** Conexão lógica no estado desconectado.  */
            const val LOGICAL_DISCONNECTED = 2

            /** Conexão lógica estabelecida (a troca de pacotes iniciada).  */
            const val LOGICAL_CONNECTED = 3

        /**
         * Valores possíveis para o parâmetro PARAM_COMMUNICATION_MODE.
         * */
            /** Sem comunicação no momento.  */
            const val COMM_MODE_NONE = 0

            /** O canal celular está sendo utilizado.  */
            const val COMM_MODE_CELLULAR = 1

            /** O canal satelital está sendo utilizado.  */
            const val COMM_MODE_SATELLITE = 2


        /**
         * Valores possíveis para o parâmetro PARAM_SOFTWARE_UPD_STATUS.
         * */

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

        /**
         * Possíveis valores para a propriedade HwControlDisable.
         * Mapa de bits. Pode ser uma combinação de um dos valores.
         * Um bit setado indica que o gerenciamento do dispositivo em questão
         * não deve ser executado.
         * */
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

        /** Indica se o Mct está com sinal ou não (=0 sem sinal, =1 com sinal). */
            const val MCT_SIGNAL_OFF = 0
            const val MCT_SIGNAL_ON = 1

        /**
        * Valores possiveis para o estado de ignicao do dispositivo externo.
        * */
            /** A ignicao do dispositivo externo esta desligada.  */
            const val IGNITION_OFF = 0

            /** A ignicao do dispositivo externo esta ligada.  */
            const val IGNITION_ON = 1
        /**
        * Indica se o uso da VPN pelo serviço de comunicação é permitido ou não.
        * O valor 1 desabilita o uso da VPN pelo serviço de comunicação e o valor 0 habilita.
        * Esta opção deve ser utilizada quando operações envolvendo o uso simultâneo do HotSpot WiFi e da conexão celular.
        * Logo que possível o uso da VPN deve ser habilitado (configurando o valor deste parâmetro como 0), caso seja desejável o seu uso.
        */
        const val ENABLE_VPN = 0
        const val DISABLE_VPN = 1
        /*
            Indica o status atual da conexão VPN (=0 desconectada, =1 conectada).
         */
        const val STATUS_ENABLED_VPN = 0
        const val STATUS_DISABLED_VPN = 1



        /**
         * Desabilita o uso da rede WiFi. (=0 habilita o uso, =1 desabilita o uso. Aplicações externas podem utilizar o
         * WiFi.)
         */
        const val ENABLE_WIFI = 0
        const val DISABLE_WIFI = 1

        val listExtDevCommType = listOf(
            ExternalDeviceCommunicationTypeValues.RS232,
            ExternalDeviceCommunicationTypeValues.SOCKET,
            ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_HOTSPOT,
            ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET,
            ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET_WIFI_HOTSPOT,
            ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_CLIENT
        )
    }


    /** Opções a serem utilizadas na copia de arquivos setados pelo Param2 da requisição [ConstsCommSvc.REQ_FILE_OPERATION].  */
    object FileOperationParam2 {
        /** Os arquivos devem ser compactados no destino com compressão zip.*/
        const val ZIP_COMPRESSION = 2
        /** Nenhuma opção selecionada.*/
        const val NO_OPTIONS = 0
        /** Os arquivos devem ser copiados.*/
        const val COPY_FILES = 1
    }
    /**
     * Valores possíveis para os tipos de comunicação (meio físico) a ser
     * utilizado com o dispositivo externo.
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
     * @see ConstsCommSvc.GET_PARAM_UC_SUBTYPE
     * @see ConstsCommSvc.SET_PARAM_WIFI_SSID
     * */
    object CmuSubtypeValues {

        /** Produto com comunicação exclusiva via satélite.  */
        const val MOBILE_SATELLITE = -1

        /** UC com comunicação celular.  */
        const val MOBILE = 10

        /** Uc com comunicação celular e satélite.  */
        const val PRIME_MOBILE = 11
    }

}