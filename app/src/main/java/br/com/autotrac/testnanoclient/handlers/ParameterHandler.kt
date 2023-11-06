package br.com.autotrac.testnanoclient.handlers

import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.consts.ParameterValues
import java.math.BigInteger

/**
 * Trata e converte parametros em strings para serem exibidos nas telas.
 * @author Melina Minaya
 */
class ParameterHandler {
    companion object {

        private val radioOptions = arrayOf(
            Pair("GPRS", ParameterValues.HWControlDisableValues.CELLULAR),
            Pair("WIFI", ParameterValues.HWControlDisableValues.WIFI),
            Pair("GPS", ParameterValues.HWControlDisableValues.GNSS),
            Pair("AIR PLANE", ParameterValues.HWControlDisableValues.AIR_PLANE)
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
                strRadioFormatted.append(ParameterValues.HWControlDisableValues.NONE)
            }

            return strRadioFormatted.toString()
        }
        fun convertIsBaptized(value: String?): String? {
            if (value.isNullOrEmpty()) {
                return null
            }

            return try {
                val doubleValue = value.toDouble()

                when (doubleValue.toInt()) {
                    ActionValues.ValuesBaptismStatusParam1.NOT_BAPTIZED -> "Não batizada."
                    ActionValues.ValuesBaptismStatusParam1.BAPTIZED -> "Batizada"
                    ActionValues.ValuesBaptismStatusParam1.IN_BAPTISM_PROCESS -> "Processo de batismo em execução."
                    ActionValues.ValuesBaptismStatusParam1.WAITING_CONFIRMATION -> "Aguardando confirmação do batismo."
                    ActionValues.ValuesBaptismStatusParam1.MCT_ADDR_RESPONSE -> "Consulta número de dispositivo de comunicação alternativo."
                    ActionValues.ValuesBaptismStatusParam1.MCT_NOT_AUTHORIZED -> "MCT não autorizado a se comunicar."
                    ActionValues.ValuesBaptismStatusParam1.BAPTISM_TIMED_OUT -> "Houve timeout no processo de batismo."
                    else -> null
                }
            } catch (e: NumberFormatException) {
                // If parsing as a double or converting to int fails, the input is not a valid number
                null
            }

        }
        fun convertConnectionTypes(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.ValuesConnectionStates.PHYSICAL_DISCONNECTED -> "Desconectado Fisicamente"
                    ParameterValues.ValuesConnectionStates.PHYSICAL_CONNECTED -> "Estabelecido Fisicamente"
                    ParameterValues.ValuesConnectionStates.LOGICAL_DISCONNECTED -> "Desconectado Logicamente"
                    ParameterValues.ValuesConnectionStates.LOGICAL_CONNECTED -> "Estabelecido Logicamente"
                    else -> null
                }
            }
        }
        fun convertUpdateRequests(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.HasUpdatePendingValues.NO_REQUEST -> "Nenhuma solicitação"
                    ParameterValues.HasUpdatePendingValues.PENDING_REQUEST -> "Atualização pendente"
                    ParameterValues.HasUpdatePendingValues.DOWNLOADING -> "Baixando atualização"
                    ParameterValues.HasUpdatePendingValues.DOWNLOADED -> "Pronto para atualizar"
                    ParameterValues.HasUpdatePendingValues.IN_PROCESS -> "Atualização em processo"
                    ParameterValues.HasUpdatePendingValues.WAITING_BOOT -> "Aguardando o restart do aparelho"
                    else -> null
                }
            }
        }
        fun convertCommMode(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.ValuesNetworkTypes.NONE -> "Sem comunicação"
                    ParameterValues.ValuesNetworkTypes.CELLULAR -> "Canal Celular em uso"
                    ParameterValues.ValuesNetworkTypes.SATELLITE -> "Canal Satelital em uso"
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
                    ParameterValues.MctSignalValues.MCT_SIGNAL_OFF -> "Sem sinal"
                    ParameterValues.MctSignalValues.MCT_SIGNAL_ON -> "Com sinal"
                    else -> null
                }
            }
        }
        fun convertIgnition(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ActionValues.ValuesIgnitionStatusParam1.ON -> "Ligada"
                    ActionValues.ValuesIgnitionStatusParam1.OFF -> "Desligada"
                    else -> null
                }
            }
        }
        fun convertVPNStatus(value: Any?): String? {
            when (value) {
                is Int -> {
                    return value.let { intValue ->
                        when (intValue) {
                            ParameterValues.VpnDisableValues.ENABLE_VPN -> "Habilitada"
                            ParameterValues.VpnDisableValues.DISABLE_VPN -> "Desabilitada"
                            else -> null
                        }
                    }
                }

                is String -> {
                    return value.toIntOrNull().let { intValue ->
                        when (intValue) {
                            ParameterValues.VpnDisableValues.ENABLE_VPN -> "Habilitada"
                            ParameterValues.VpnDisableValues.DISABLE_VPN -> "Desabilitada"
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
                    ParameterValues.VpnConnectionStatusValues.VPN_CONNECTED-> "Conectada"
                    ParameterValues.VpnConnectionStatusValues.VPN_DISCONNECTED -> "Desconectada"
                    else -> "N/A"
                }
            }
        }
        fun convertWifiStatus(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.WifiDisableValues.ENABLE_WIFI -> "Habilitada"
                    ParameterValues.WifiDisableValues.DISABLE_WIFI -> "Desabilitada"
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
                            ActionValues.FileOperationOptions.COPY_FILES -> "Copia arquivos"
                            ActionValues.FileOperationOptions.ZIP_COMPRESSION -> "Compacta arquivos"
                            ActionValues.FileOperationOptions.NO_OPTIONS -> "Nenhuma opção selecionada"
                            else -> null
                        }
                    }
                }
                is String -> {
                return value.toIntOrNull().let { intValue ->
                    when (intValue) {
                        ActionValues.FileOperationOptions.COPY_FILES -> "Copia arquivos"
                        ActionValues.FileOperationOptions.ZIP_COMPRESSION -> "Compacta arquivos"
                        ActionValues.FileOperationOptions.NO_OPTIONS -> "Nenhuma opção selecionada"
                        else -> null
                    }
                }
            }
                else -> return null
            }
        }
        fun convertUcStatusValue(value: String?): String? {
            return value?.toIntOrNull()?.let { intValue ->
                when (intValue) {
                    ParameterValues.UcStatusValues.UC_ENABLED -> "UC Habilitada"
                    ParameterValues.UcStatusValues.UC_DISABLED -> "UC Desabilitada"
                    ParameterValues.UcStatusValues.INVALID_USER_OR_PASSWORD -> "UC habilitada mas com licença de uso Inativa"
                    ParameterValues.UcStatusValues.UNKNOWN -> "Status desconhecido"
                    else -> null
                }
            }
        }
        fun convertMctParamNumber(value: Int): String? {
            return value.let { intValue ->
                when (intValue) {
                    ParameterValues.MctM0M9Params.M0 -> "M0"
                    ParameterValues.MctM0M9Params.M1 -> "M1"
                    ParameterValues.MctM0M9Params.M2 -> "M2"
                    ParameterValues.MctM0M9Params.M3 -> "M3"
                    ParameterValues.MctM0M9Params.M4 -> "M4"
                    ParameterValues.MctM0M9Params.M5 -> "M5"
                    ParameterValues.MctM0M9Params.M6 -> "M6"
                    ParameterValues.MctM0M9Params.M7 -> "M7"
                    ParameterValues.MctM0M9Params.M8 -> "M8"
                    ParameterValues.MctM0M9Params.M9 -> "M9"
                    else -> null
                }
            }
        }
    }
}