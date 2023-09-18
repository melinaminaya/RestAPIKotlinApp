package com.example.nanoclientkotlin.handlers

import com.example.nanoclientkotlin.consts.ParameterValues
import java.math.BigInteger

class ParameterHandler {
    companion object {

        private val radioOptions = arrayOf(
            Pair("GPRS", ParameterValues.CELLULAR),
            Pair("WIFI", ParameterValues.WIFI),
            Pair("GPS", ParameterValues.GNSS),
            Pair("AIR PLANE", ParameterValues.AIR_PLANE)
        )

        fun getFormattedRadioOptions(hwControlDisabled: Long?): String {
            val strRadioFormatted = StringBuilder()

            for (i in radioOptions) {
                val optionValue = i.second
                val optionName = i.first
                if (!BigInteger.valueOf(hwControlDisabled ?: 0).testBit(optionValue)) {
                    strRadioFormatted.append(if (strRadioFormatted.isEmpty()) optionName else "|$optionName")
                }
            }

            if (strRadioFormatted.isEmpty()) {
                strRadioFormatted.append(ParameterValues.NONE)
            }

            return strRadioFormatted.toString()
        }
        fun convertIsBaptized(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.NOT_BAPTIZED -> "Não batizada."
                    ParameterValues.BAPTIZED -> "Batizada"
                    ParameterValues.IN_BAPTISM_PROCESS -> "Processo de batismo em execução."
                    ParameterValues.WAITING_CONFIRMATION -> "Aguardando confirmação do batismo."
                    ParameterValues.CONSULT_ALT_COMM_DEVICE_ADDR -> "Consulta número de dispositivo de comunicação alternativo."
                    ParameterValues.MCT_NOT_AUTHORIZED -> "MCT não autorizado a se comunicar."
                    ParameterValues.BAPTISM_TIMED_OUT -> "Houve timeout no processo de batismo."
                    else -> null
                }
            }
        }
        fun convertConnectionTypes(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.PHYSICAL_DISCONNECTED -> "Desconectado Fisicamente"
                    ParameterValues.PHYSICAL_CONNECTED -> "Estabelecido Fisicamente"
                    ParameterValues.LOGICAL_DISCONNECTED -> "Desconectado Logicamente"
                    ParameterValues.LOGICAL_CONNECTED -> "Estabelecido Logicamente"
                    else -> null
                }
            }
        }
        fun convertUpdateRequests(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.NO_REQUEST -> "Nenhuma solicitação"
                    ParameterValues.PENDING_REQUEST -> "Atualização pendente"
                    ParameterValues.DOWNLOADING -> "Baixando atualização"
                    ParameterValues.DOWNLOADED -> "Pronto para atualizar"
                    ParameterValues.IN_PROCESS -> "Atualização em processo"
                    ParameterValues.WAITING_BOOT -> "Aguardando o restart do aparelho"
                    else -> null
                }
            }
        }
        fun convertCommMode(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.COMM_MODE_NONE -> "Sem comunicação"
                    ParameterValues.COMM_MODE_CELLULAR -> "Canal Celular em uso"
                    ParameterValues.COMM_MODE_SATELLITE -> "Canal Satelital em uso"
                    else -> null
                }
            }
        }
        fun convertCommTypes(value: Any?): String? {
            when (value) {
                is Int -> {
                    return value.let { intValue ->
                        when (intValue) {
                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232 -> "serial RS232"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.SOCKET -> "socket, com acesso direto à rede (sem NetworkAccess)"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_HOTSPOT -> "socket, com acesso à rede via HotSpot WiFi"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET -> "serial RS232 ou socket, com acesso direto\n" +
                                    "à rede (sem NetworkAccess) de acordo com a demanda"

                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET_WIFI_HOTSPOT -> "serial RS232 ou socket, com acesso à\n" +
                                    " rede via HotSpot WiFi de acordo com a demanda"

                            ParameterValues.ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_CLIENT -> "socket, com acesso à rede via Cliente WiFi"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET_WIFI_CLIENT -> "serial RS232 ou socket, com acesso à\n" +
                                    "rede via Cliente WiFi de acordo com a demanda"
                            else -> null
                        }
                    }
                }
                is String -> {
                    return value.toIntOrNull().let { intValue ->
                        when (intValue) {
                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232 -> "serial RS232"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.SOCKET -> "socket, com acesso direto à rede (sem NetworkAccess)"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_HOTSPOT -> "socket, com acesso à rede via HotSpot WiFi"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET -> "serial RS232 ou socket, com acesso direto\n" +
                                    "à rede (sem NetworkAccess) de acordo com a demanda"

                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET_WIFI_HOTSPOT -> "serial RS232 ou socket, com acesso à\n" +
                                    " rede via HotSpot WiFi de acordo com a demanda"

                            ParameterValues.ExternalDeviceCommunicationTypeValues.SOCKET_WIFI_CLIENT -> "socket, com acesso à rede via Cliente WiFi"
                            ParameterValues.ExternalDeviceCommunicationTypeValues.RS232_AND_SOCKET_WIFI_CLIENT -> "serial RS232 ou socket, com acesso à\n" +
                                    "rede via Cliente WiFi de acordo com a demanda"
                            else -> null
                        }
                    }
                }
                else -> return null

            }
        }
        fun listParamsCommTypes(): List<String>{
            val listParametersCommType: ArrayList<String> = arrayListOf()
            for(item in ParameterValues.listExtDevCommType){
                val itemToAdd = convertCommTypes(item)
                if (itemToAdd != null) {
                    listParametersCommType.add(itemToAdd)
                }
            }
            return listParametersCommType
        }
        fun convertMctSignal(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.MCT_SIGNAL_OFF -> "Sem sinal"
                    ParameterValues.MCT_SIGNAL_ON -> "Com sinal"
                    else -> null
                }
            }
        }
        fun convertIgnition(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.IGNITION_ON -> "Ligada"
                    ParameterValues.IGNITION_OFF -> "Desligada"
                    else -> null
                }
            }
        }
        fun convertVPNStatus(value: Any?): String? {
            when (value) {
                is Int -> {
                    return value.let { intValue ->
                        when (intValue) {
                            ParameterValues.ENABLE_VPN -> "Habilitada"
                            ParameterValues.DISABLE_VPN -> "Desabilitada"
                            else -> null
                        }
                    }
                }

                is String -> {
                    return value.toIntOrNull().let { intValue ->
                        when (intValue) {
                            ParameterValues.ENABLE_VPN -> "Habilitada"
                            ParameterValues.DISABLE_VPN -> "Desabilitada"
                            else -> null
                        }
                    }
                }
                else -> return null
            }
        }
        fun convertVPNConnectionStatus(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.STATUS_ENABLED_VPN-> "Conectada"
                    ParameterValues.STATUS_DISABLED_VPN -> "Desconectada"
                    else -> "N/A"
                }
            }
        }
        fun convertWifiStatus(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.ENABLE_WIFI -> "Habilitada"
                    ParameterValues.DISABLE_WIFI -> "Desabilitada"
                    else -> "N/A"
                }
            }
        }
        fun convertUcSubtype(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.CmuSubtypeValues.MOBILE-> "Celular"
                    ParameterValues.CmuSubtypeValues.MOBILE_SATELLITE -> "Satelital"
                    ParameterValues.CmuSubtypeValues.PRIME_MOBILE -> "Prime Mobile"
                    else -> null
                }
            }
        }
        fun convertFileOperationParam2(value: Any?): String? {
            when (value) {
                is Int -> {
                    return value.let { intValue ->
                        when (intValue) {
                            ParameterValues.FileOperationParam2.COPY_FILES -> "Copia arquivos"
                            ParameterValues.FileOperationParam2.ZIP_COMPRESSION -> "Compacta arquivos"
                            ParameterValues.FileOperationParam2.NO_OPTIONS -> "Nenhuma opção selecionada"
                            else -> null
                        }
                    }
                }
                is String -> {
                return value.toIntOrNull().let { intValue ->
                    when (intValue) {
                        ParameterValues.FileOperationParam2.COPY_FILES -> "Copia arquivos"
                        ParameterValues.FileOperationParam2.ZIP_COMPRESSION -> "Compacta arquivos"
                        ParameterValues.FileOperationParam2.NO_OPTIONS -> "Nenhuma opção selecionada"
                        else -> null
                    }
                }
            }
                else -> return null
            }
        }
    }
}