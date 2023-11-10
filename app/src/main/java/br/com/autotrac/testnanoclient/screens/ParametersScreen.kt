package br.com.autotrac.testnanoclient.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.autotrac.testnanoclient.ObservableUtil.addPropertyChangeListener
import br.com.autotrac.testnanoclient.common.CustomTextFieldWithButton
import br.com.autotrac.testnanoclient.common.CustomTopAppBar
import br.com.autotrac.testnanoclient.common.DropDownToSet
import br.com.autotrac.testnanoclient.common.DropdownCard
import br.com.autotrac.testnanoclient.common.LoadingIcon
import br.com.autotrac.testnanoclient.common.ModelRow
import br.com.autotrac.testnanoclient.common.SwitchParameter
import br.com.autotrac.testnanoclient.consts.ActionValues
import br.com.autotrac.testnanoclient.consts.ApiEndpoints
import br.com.autotrac.testnanoclient.consts.ParameterValues
import br.com.autotrac.testnanoclient.handlers.EndpointsLists
import br.com.autotrac.testnanoclient.handlers.ParameterHandler
import br.com.autotrac.testnanoclient.handlers.ParseData
import br.com.autotrac.testnanoclient.vm.ParametersViewModel
import java.beans.PropertyChangeListener

@Composable
fun ParametersScreen(
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
    val viewModel: ParametersViewModel = viewModel()
    val parameterNamesGet = EndpointsLists.parametersList.filter { it.startsWith("GET") }
    val parameterValues = remember { mutableStateMapOf<String, String>() }
    parameterNamesGet.forEach { parameterName ->
        val parameterLiveData = viewModel.getParameterLiveData(parameterName)
        val parameterValue by parameterLiveData!!.observeAsState("")
        parameterValues[parameterName] = parameterValue ?: ""
    }

    var wifiSSIDText by rememberSaveable { mutableStateOf(parameterValues[ApiEndpoints.GET_PARAM_WIFI_SSID]) }
    val enabled by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var altCommDevText by rememberSaveable {
        mutableStateOf(
            parameterValues[ApiEndpoints.GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S]
        )
    }
    var vpnDisableCommText by rememberSaveable {
        mutableStateOf(
            parameterValues[ApiEndpoints.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]
        )
    }

    var wifiDisableCommText by rememberSaveable {
        mutableStateOf(
            parameterValues[ApiEndpoints.GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]
        )
    }

    var extDevCommType by rememberSaveable {
        mutableStateOf(parameterValues[ApiEndpoints.GET_PARAM_EXT_DEV_COMM_TYPE])
    }
    var outOfBandMsg by rememberSaveable {
        mutableStateOf(parameterValues[ApiEndpoints.GET_PARAM_OUT_OF_BAND_MSG_PATH])
    }
    var timeoutSendCellMsg by rememberSaveable {
        mutableStateOf(parameterValues[ApiEndpoints.GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG])
    }
    var isBaptizedValue by rememberSaveable {
        mutableStateOf(parameterValues[ApiEndpoints.GET_PARAM_IS_BAPTIZED])
    }
    var baptismStarted by rememberSaveable { mutableStateOf(false) }
//    val isBaptizedValueState by viewModel.isBaptizedValue.observeAsState(initial = isBaptizedValue)
//    val baptismStatusState by viewModel.observeBaptismStatus().collectAsState(initial = "")
    var baptismStatus by remember { mutableStateOf("") }
    var satelliteSignal by rememberSaveable {
        mutableStateOf(parameterValues[ApiEndpoints.GET_PARAM_HAS_SATELLITE_SIGNAL])
    }
// Create a custom PropertyChangeListener
    val propertyChangeListener = PropertyChangeListener { evt ->
        evt?.let {
            // Handle property changes here
            if (evt.propertyName == ActionValues.SATELLITE_SIGNAL_CHANGED_OBSERVABLE) {
                val newValue = evt.newValue.toString()
                // Update the satelliteSignal Composable property
                satelliteSignal = newValue.toDouble().toInt().toString()
            } else if (evt.propertyName == ActionValues.BAPTISM_STATUS_OBSERVABLE) {
                val newValue = evt.newValue.toString()
                // Update the baptismStatus Composable property
                isBaptizedValue = ParameterHandler.convertIsBaptized(newValue)
                if (isBaptizedValue != null) {
                    baptismStatus = isBaptizedValue as String
                }
            }

        }
    }
    addPropertyChangeListener(propertyChangeListener)



    LaunchedEffect(Unit) {
        viewModel.fetchParameters()
        if (enabled) {
            focusRequester.requestFocus()
        }
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_IS_BAPTIZED]) {
        isBaptizedValue =
            ParameterHandler.convertIsBaptized(parameterValues[ApiEndpoints.GET_PARAM_IS_BAPTIZED])
        if (isBaptizedValue != null) {
            baptismStatus = isBaptizedValue as String
        }
        Log.d("BAPTISM_STATUS", "BAPTISM_STATUS no PARAM: $baptismStatus")
    }
    // Update wifiSSIDText when paramWifiSSID changes
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_WIFI_SSID]) {
        wifiSSIDText = parameterValues[ApiEndpoints.GET_PARAM_WIFI_SSID]
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S]) {
        altCommDevText = parameterValues[ApiEndpoints.GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S]
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]) {
        vpnDisableCommText = parameterValues[ApiEndpoints.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]) {
        wifiDisableCommText =
            parameterValues[ApiEndpoints.GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_EXT_DEV_COMM_TYPE]) {
        extDevCommType = parameterValues[ApiEndpoints.GET_PARAM_EXT_DEV_COMM_TYPE]
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_OUT_OF_BAND_MSG_PATH]) {
        outOfBandMsg = parameterValues[ApiEndpoints.GET_PARAM_OUT_OF_BAND_MSG_PATH]
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG]) {
        timeoutSendCellMsg = parameterValues[ApiEndpoints.GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG]
    }
    LaunchedEffect(parameterValues[ApiEndpoints.GET_PARAM_HAS_SATELLITE_SIGNAL]) {
        satelliteSignal = parameterValues[ApiEndpoints.GET_PARAM_HAS_SATELLITE_SIGNAL]
    }


    Scaffold(topBar = {
        CustomTopAppBar(
            title = "Parâmetros",
            navigateToLogs = { },
            popUpToLogin = popUpToLogin,
            onBackClick = { popBackStack() },
            isSocketOn = null,
            apiIcon = true
        ) {}
    }) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
//            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            item {
                DropdownCard(title = "Batismo") {
                    Column {
                        ModelRow(
                            title = "Status: ", status = baptismStatus
                        )

                        Spacer(modifier = Modifier.height(4.dp))
//                        BaptismView(wifiSSIDText, enabled, focusRequester, viewModel)
                        BaptismTextFieldWithButtons(
                            title = "Batizar",
                            text = wifiSSIDText ?: "",
                            onTextChange = { wifiSSIDText = it },
                            onSaveClick = {
                                it
                                wifiSSIDText = it
                                viewModel.setParam(
                                    ApiEndpoints.SET_PARAM_WIFI_SSID, wifiSSIDText ?: ""
                                )
//                                baptismStarted = true
                            },
                            onCancelClick = {
                                wifiSSIDText = ""
                                viewModel.setParam(
                                    ApiEndpoints.SET_PARAM_WIFI_SSID, wifiSSIDText ?: ""
                                )
                            },
                            baptismStatus = baptismStatus
                        )
                    }
                }
            }
            item {
                DropdownCard(title = "Account Number") {
                    parameterValues[ApiEndpoints.GET_PARAM_ACCOUNT_NUMBER]?.let {
                        Text(
                            text = parameterValues[ApiEndpoints.GET_PARAM_ACCOUNT_NUMBER].toString(),
                            style = TextStyle(fontSize = 14.sp)
                        )
                    } ?: LoadingIcon(size = 25, null)
                }
            }
            item {
                DropdownCard(title = "Alternative Communication Device") {
                    Column {
                        CustomTextFieldWithButton(title = "Intervalo de tempo, em segundos, entre cada ciclo de troca de pacotes com o Mct.",
                            text = altCommDevText,
                            onTextChange = { altCommDevText = it }) {
                            viewModel.setParam(
                                ApiEndpoints.SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S,
                                altCommDevText!!
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title = "Versão de Firmware:",
                            status = parameterValues[ApiEndpoints.GET_PARAM_ALT_COMM_FIRMWARE_VERSION]
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title = "Endereço do dispositivo de comunicação alternativo:",
                            status = parameterValues[ApiEndpoints.GET_PARAM_ALTERNATIVE_COMM_DEVICE_ADDRESS]
                        )
                    }
                }
            }
            item {
                DropdownCard(title = "Conexão Celular") {
                    ModelRow(
                        title = "Ip Local de Conexão Celular:",
                        status = parameterValues[ApiEndpoints.GET_PARAM_CELL_IP_ADDRESS]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Sinal Celular:",
                        status = parameterValues[ApiEndpoints.GET_PARAM_HAS_CELLULAR_SIGNAL]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Última mensagem/posição trocada na rede celular: ",
                        status = ParseData.convertFromTimeStamp(
                            parameterValues[ApiEndpoints.GET_PARAM_LAST_CELL_COMM_TIME]
                        ).toString()
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Último status da conexão via rede celular:  ",
                        status = ParameterHandler.convertConnectionTypes(parameterValues[ApiEndpoints.GET_PARAM_LAST_CELL_CONNECTION_STATUS])
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    CustomTextFieldWithButton(title = "Intervalo de tempo para tentativas de envio em rede celular: ",
                        text = timeoutSendCellMsg,
                        onTextChange = { timeoutSendCellMsg = it }) {
                        viewModel.setParam(
                            ApiEndpoints.SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG,
                            timeoutSendCellMsg ?: ""
                        )
                    }
                }
            }
            item {
                DropdownCard(title = "Conexão Satelital") {
                    ModelRow(
                        title = "Sinal do MCT: ",
                        status = ParameterHandler.convertMctSignal(satelliteSignal)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Última mensagem/posição trocada na rede satelital: ",
                        status = ParseData.convertFromTimeStamp(
                            parameterValues[ApiEndpoints.GET_PARAM_LAST_SAT_COMM_TIME]
                        ).toString()
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Último status da conexão via rede satelital: ",
                        status = ParameterHandler.convertConnectionTypes(parameterValues[ApiEndpoints.GET_PARAM_LAST_SAT_CONNECTION_STATUS])
                    )
                }
            }
            item {
                DropdownCard(title = "Diretórios") {
                    ModelRow(
                        title = "Diretório de Logs do Cliente:  ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_CLIENT_LOGS_DIRECTORY]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Status de envio de log: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_FTP_LOGS_STATUS]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Diretório de arquivos de mensagens longas: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_OUT_OF_BAND_MSG_PATH]
                    )
                    CustomTextFieldWithButton(title = "Diretório de arquivos de mensagens longas: ",
                        text = outOfBandMsg,
                        onTextChange = { outOfBandMsg = it },
                        onSaveClick = {
                            viewModel.setParam(
                                ApiEndpoints.SET_PARAM_OUT_OF_BAND_MSG_PATH, outOfBandMsg ?: ""
                            )
                        })
                }
            }
            item {
                DropdownCard(title = "Identificação do aparelho móvel") {
                    ModelRow(
                        title = "Modelo do Aparelho Móvel: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_COMM_UNIT_DEVICE_TYPE]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Canal de comunicação atual: ",
                        status = ParameterHandler.convertCommMode(parameterValues[ApiEndpoints.GET_PARAM_CURRENT_COMM_MODE])
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    DropDownToSet(
                        title = "Tipo de comunicação a ser utilizado com o dispositivo externo: ",
                        previousText = extDevCommType,
                        onTextChange = { item ->
                            extDevCommType = item
                            viewModel.setParam(
                                ApiEndpoints.SET_PARAM_EXT_DEV_COMM_TYPE, item
                            )
                        },
                        textStatus = ParameterHandler.convertCommTypes(extDevCommType),
                        dropdownItems = ParameterHandler.listParamsCommTypes()


                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Número da UC: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_UC_ADDRESS]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Status da UC Móvel: ",
                        status = ParameterHandler.convertUcStatusValue(parameterValues[ApiEndpoints.GET_PARAM_UC_STATUS])
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Subtipo da UC Móvel: ",
                        status = ParameterHandler.convertUcSubtype(parameterValues[ApiEndpoints.GET_PARAM_UC_SUBTYPE])
                    )
                }
            }
            item {
                DropdownCard(title = "Software/Hardware") {
                    ModelRow(
                        title = "Status de Atualização de Software: ",
                        status = ParameterHandler.convertUpdateRequests(parameterValues[ApiEndpoints.GET_PARAM_HAS_UPDATE_PENDING])
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Firware de Conversores Homologados: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_HOM_WIFI_SERIAL_DEV_FW_VERSIONLIST]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Controle de Wifi, GPRS, GPS e Modo Avião (Habilitados): ",
                        status = ParameterHandler.getFormattedRadioOptions(parameterValues[ApiEndpoints.GET_PARAM_HW_CONTROL_DISABLE]?.toLong())
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "ICCID do SimCard1: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_ICCID1]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Número Serial: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_IMEI1]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Provedor de SimCard: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_PHONE_PROVIDER_NAME]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Versão do Serviço: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_SERVICE_VERSION]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Versão da Interface do Usuário: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_USER_INTERFACE_VERSION]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Serial do Conversor WIFI:",
                        status = parameterValues[ApiEndpoints.GET_PARAM_WIFI_CONVERTER_SERIAL_NUMBER]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Versão do Firmware do Conversor WIFI",
                        status = parameterValues[ApiEndpoints.GET_PARAM_WIFI_SERIAL_DEV_FIRMWARE_VERSION]
                    )
                }
            }
            item {
                DropdownCard(title = "Relatório de Dispositivo Externo") {
                    ModelRow(
                        title = "Status da ignição:",
                        status = ParameterHandler.convertIgnition(parameterValues[ApiEndpoints.GET_PARAM_IGNITION_STATUS])
                    )
                }
            }
            item {
                DropdownCard(title = "Habilita/Desabilita VPN e WIFI") {
                    Column {
                        SwitchParameter(
                            title = "Vpn Status: ",
                            paramText = vpnDisableCommText,
                            isChecked = vpnDisableCommText == "0",
                            onTextChange = { newCheckedState ->
//                                            enabledVpnDisableCommText = newCheckedState
                                vpnDisableCommText = if (!newCheckedState) {
                                    viewModel.setParam(
                                        ApiEndpoints.SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION,
                                        ParameterValues.VpnDisableValues.DISABLE_VPN.toString()
                                    )
                                    ParameterValues.VpnDisableValues.DISABLE_VPN.toString()
                                } else {
                                    viewModel.setParam(
                                        ApiEndpoints.SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION,
                                        ParameterValues.VpnDisableValues.ENABLE_VPN.toString()
                                    )
                                    ParameterValues.VpnDisableValues.ENABLE_VPN.toString()
                                }
                            },
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title = "Status da Conexão da VPN",
                            status = ParameterHandler.convertVPNConnectionStatus(ApiEndpoints.GET_PARAM_VPN_CONNECTION_STATUS)
                                ?: "N/A"
                        )


                        Spacer(modifier = Modifier.height(4.dp))
                        SwitchParameter(
                            title = "Wifi Status:",
                            paramText = wifiDisableCommText,
                            isChecked = wifiDisableCommText == "0",
                            onTextChange = { newCheckedState ->
//                                enabledWifiDisableCommText = newCheckedState
                                wifiDisableCommText = if (!newCheckedState) {
                                    viewModel.setParam(
                                        ApiEndpoints.SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION,
                                        ParameterValues.WifiDisableValues.DISABLE_WIFI.toString()
                                    )
                                    ParameterValues.WifiDisableValues.DISABLE_WIFI.toString()
                                } else {
                                    viewModel.setParam(
                                        ApiEndpoints.SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION,
                                        ParameterValues.WifiDisableValues.ENABLE_WIFI.toString()
                                    )
                                    ParameterValues.WifiDisableValues.ENABLE_WIFI.toString()
                                }
                            },
                        )


                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title = "IP da rede WIFI:",
                            status = parameterValues[ApiEndpoints.GET_PARAM_WIFI_IP_ADDRESS]
                        )
                    }
                }
            }
            item {
                DropdownCard(title = "Lista de Apps permitidos") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        parameterValues[ApiEndpoints.GET_PARAM_PROXY_APPS_ON_WHITE_LIST]?.let { listApps ->
                            Text(
                                text = listApps, style = TextStyle(fontSize = 14.sp)
                            )
                        } ?: LoadingIcon(size = 25, null)
                    }
                }
            }
            item {
                DropdownCard(title = "Servidor (AMH)") {
                    ModelRow(
                        title = "Endereço primário do servidor: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_SERVER_IP1]
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Porta primária do servidor: ",
                        status = parameterValues[ApiEndpoints.GET_PARAM_SERVER_PORT1]
                    )
                }
            }
        }
    }
}



