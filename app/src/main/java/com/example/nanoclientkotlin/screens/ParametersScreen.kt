package com.example.nanoclientkotlin.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nanoclientkotlin.common.CustomTextFieldWithButton
import com.example.nanoclientkotlin.common.DropDownToSet
import com.example.nanoclientkotlin.common.DropdownCard
import com.example.nanoclientkotlin.common.ModelRow
import com.example.nanoclientkotlin.common.SwitchParameter
import com.example.nanoclientkotlin.consts.ConstsCommSvc
import com.example.nanoclientkotlin.consts.ParameterValues
import com.example.nanoclientkotlin.handlers.ParameterHandler
import com.example.nanoclientkotlin.vm.ParametersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParametersScreen(
    popBackStack: () -> Unit,
    popUpToLogin: () -> Unit,
) {
    val viewModel: ParametersViewModel = viewModel()
    val parameterNamesGet = ConstsCommSvc.parametersList.filter { it.startsWith("GET") }
    val parameterValues = remember { mutableStateMapOf<String, String>() }
    parameterNamesGet.forEach { parameterName ->
        val parameterLiveData = viewModel.getParameterLiveData(parameterName)
        val parameterValue by parameterLiveData!!.observeAsState("")
        parameterValues[parameterName] = parameterValue ?: ""
    }

    var wifiSSIDText by rememberSaveable { mutableStateOf(parameterValues[ConstsCommSvc.GET_PARAM_WIFI_SSID]) }
    val enabled by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var altCommDevText by rememberSaveable {
        mutableStateOf(
            parameterValues[ConstsCommSvc.GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S]
        )
    }
    var enabledAltCommDevText by rememberSaveable { mutableStateOf(false) }
    val focusRequesterAltCommDevText = remember { FocusRequester() }
    var vpnDisableCommText by rememberSaveable {
        mutableStateOf(
            parameterValues[ConstsCommSvc.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]
        )
    }
    var enabledVpnDisableCommText by rememberSaveable { mutableStateOf(vpnDisableCommText != "0") }

    var wifiDisableCommText by rememberSaveable {
        mutableStateOf(
            parameterValues[ConstsCommSvc.GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]
        )
    }
    var enabledWifiDisableCommText by rememberSaveable { mutableStateOf(wifiDisableCommText != "0") }

    var extDevCommType by rememberSaveable {
        mutableStateOf( parameterValues[ConstsCommSvc.GET_PARAM_EXT_DEV_COMM_TYPE])
    }
    var outOfBandMsg by rememberSaveable {
        mutableStateOf( parameterValues[ConstsCommSvc.GET_PARAM_OUT_OF_BAND_MSG_PATH] )
    }
    var timeoutSendCellMsg by rememberSaveable {
        mutableStateOf( parameterValues[ConstsCommSvc.GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG] )
    }
    var isBaptizedValue by rememberSaveable {
        mutableStateOf( parameterValues[ConstsCommSvc.GET_PARAM_IS_BAPTIZED] )
    }

    LaunchedEffect(Unit) {
        viewModel.fetchParameters()
        if (enabled) {
            focusRequester.requestFocus()
        }
    }
    // Update wifiSSIDText when paramWifiSSID changes
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_WIFI_SSID]) {
        wifiSSIDText = parameterValues[ConstsCommSvc.GET_PARAM_WIFI_SSID]
    }
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S]) {
        altCommDevText = parameterValues[ConstsCommSvc.GET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S]
    }
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]) {
        vpnDisableCommText =
            parameterValues[ConstsCommSvc.GET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION]
    }
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]) {
        wifiDisableCommText =
            parameterValues[ConstsCommSvc.GET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION]
    }
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_EXT_DEV_COMM_TYPE]) {
        extDevCommType =
            parameterValues[ConstsCommSvc.GET_PARAM_EXT_DEV_COMM_TYPE]
    }
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_OUT_OF_BAND_MSG_PATH]) {
        outOfBandMsg =
            parameterValues[ConstsCommSvc.GET_PARAM_OUT_OF_BAND_MSG_PATH]
    }
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG]) {
        timeoutSendCellMsg =
            parameterValues[ConstsCommSvc.GET_PARAM_TIMEOUT_SEND_CELLULAR_MSG]
    }
    LaunchedEffect(parameterValues[ConstsCommSvc.GET_PARAM_IS_BAPTIZED]) {
        isBaptizedValue = parameterValues[ConstsCommSvc.GET_PARAM_IS_BAPTIZED]
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Parâmetros") },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = popUpToLogin) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Log Out")
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
//            verticalArrangement = Arrangement.spacedBy(2.dp)
        )
        {
            item {
                DropdownCard(title = "Batismo") {
                    Column {
                        ModelRow(
                            title = "Status: ",
                            status = ParameterHandler.convertIsBaptized(isBaptizedValue))

                        Spacer(modifier = Modifier.height(4.dp))
//                        BaptismView(wifiSSIDText, enabled, focusRequester, viewModel)
                        CustomTextFieldWithButton(
                            title = "Batizar",
                            text = wifiSSIDText,
                            onTextChange = { wifiSSIDText = it },
                            onSaveClick = {
                                viewModel.setParam(
                                    ConstsCommSvc.SET_PARAM_WIFI_SSID,
                                    wifiSSIDText ?: ""
                                )
                            }
                        )
                    }
                }
            }
            item {
                DropdownCard(title = "Account Number") {
                    if (parameterValues[ConstsCommSvc.GET_PARAM_ACCOUNT_NUMBER] != null) {
                        Text(
                            text = parameterValues[ConstsCommSvc.GET_PARAM_ACCOUNT_NUMBER].toString(),
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                }
            }
            item {
                DropdownCard(title = "Alternative Communication Device") {
                    Column {
                        CustomTextFieldWithButton(
                            title ="Intervalo de tempo, em segundos, entre cada ciclo de troca de pacotes com o Mct.",
                            text = altCommDevText,
                            onTextChange = { altCommDevText = it }
                        ) {
                            viewModel.setParam(
                                ConstsCommSvc.SET_PARAM_ALT_COMM_DEVICE_POLL_INTERVAL_S,
                                altCommDevText!!
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title = "Versão de Firmware:",
                            status = parameterValues[ConstsCommSvc.GET_PARAM_ALT_COMM_FIRMWARE_VERSION])

                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title = "Endereço do dispositivo de comunicação alternativo:",
                            status =parameterValues[ConstsCommSvc.GET_PARAM_ALTERNATIVE_COMM_DEVICE_ADDRESS] )
                    }
                }
            }
            item {
                DropdownCard(title = "Conexão Celular") {
                    ModelRow(
                        title ="Ip Local de Conexão Celular:" ,
                       status =parameterValues[ConstsCommSvc.GET_PARAM_CELL_IP_ADDRESS] )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Sinal Celular:",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_HAS_CELLULAR_SIGNAL] )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Última mensagem/posição trocada na rede celular: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_LAST_CELL_COMM_TIME])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Último status da conexão via rede celular:  ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_LAST_CELL_CONNECTION_STATUS])

                    Spacer(modifier = Modifier.height(4.dp))

                    CustomTextFieldWithButton(
                        title ="Intervalo de tempo para tentativas de envio em rede celular: " ,
                        text = timeoutSendCellMsg ,
                        onTextChange = { timeoutSendCellMsg = it }
                    ) {
                        viewModel.setParam(
                            ConstsCommSvc.SET_PARAM_TIMEOUT_SEND_CELLULAR_MSG,
                            wifiSSIDText ?: ""
                        )
                    }
                }
            }
            item {
                DropdownCard(title = "Conexão Satelital") {
                    ModelRow(
                        title = "Sinal do MCT: ",
                        status = ParameterHandler.convertMctSignal(parameterValues[ConstsCommSvc.GET_PARAM_HAS_SATELLITE_SIGNAL]))

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Última mensagem/posição trocada na rede satelital: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_LAST_SAT_COMM_TIME])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Último status da conexão via rede satelital: ",
                        status = ParameterHandler.convertConnectionTypes(parameterValues[ConstsCommSvc.GET_PARAM_LAST_SAT_CONNECTION_STATUS]))
                }
            }
            item {
                DropdownCard(title = "Diretórios") {
                    ModelRow(
                        title = "Diretório de Logs do Cliente:  ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_CLIENT_LOGS_DIRECTORY])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Status de envio de log: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_FTP_LOGS_STATUS])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Diretório de arquivos de mensagens longas: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_OUT_OF_BAND_MSG_PATH] )
                    CustomTextFieldWithButton(
                        title = "Diretório de arquivos de mensagens longas: ",
                        text = outOfBandMsg ,
                        onTextChange ={outOfBandMsg = it},
                        onSaveClick = {
                            viewModel.setParam(
                                ConstsCommSvc.SET_PARAM_OUT_OF_BAND_MSG_PATH,
                                outOfBandMsg ?: ""
                            )
                        }
                    )
                }
            }
            item {

                DropdownCard(title = "Identificação do aparelho móvel") {
                    ModelRow(
                        title = "Modelo do Aparelho Móvel: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_COMM_UNIT_DEVICE_TYPE])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Canal de comunicação atual: ",
                        status = ParameterHandler.convertCommMode(parameterValues[ConstsCommSvc.GET_PARAM_CURRENT_COMM_MODE]))

                    Spacer(modifier = Modifier.height(4.dp))
                    DropDownToSet(
                        title = "Tipo de comunicação a ser utilizado com o dispositivo externo: ",
                        previousText = extDevCommType,
                        onTextChange = {item->
                           extDevCommType  = item
                            viewModel.setParam(
                                ConstsCommSvc.SET_PARAM_EXT_DEV_COMM_TYPE,
                                item
                            )
                        },
                        textStatus = ParameterHandler.convertCommTypes(extDevCommType),
                        dropdownItems = ParameterHandler.listParamsCommTypes()


                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Número da UC: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_UC_ADDRESS])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Status da UC Móvel: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_UC_STATUS])
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Subtipo da UC Móvel: ",
                        status = ParameterHandler.convertUcSubtype(parameterValues[ConstsCommSvc.GET_PARAM_UC_SUBTYPE]))
                }
            }
            item {
                DropdownCard(title = "Software/Hardware") {
                    ModelRow(
                        title = "Status de Atualização de Software: ",
                        status = ParameterHandler.convertUpdateRequests(parameterValues[ConstsCommSvc.GET_PARAM_HAS_UPDATE_PENDING]))

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Firware de Conversores Homologados: " ,
                        status = parameterValues[ConstsCommSvc.GET_PARAM_HOM_WIFI_SERIAL_DEV_FW_VERSIONLIST])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title ="Controle de Wifi, GPRS, GPS e Modo Avião (Habilitados): " ,
                        status = ParameterHandler.getFormattedRadioOptions(parameterValues[ConstsCommSvc.GET_PARAM_HW_CONTROL_DISABLE]?.toLong()))

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title ="ICCID do SimCard1: " ,
                        status = parameterValues[ConstsCommSvc.GET_PARAM_ICCID1])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title ="Número Serial: " ,
                        status = parameterValues[ConstsCommSvc.GET_PARAM_IMEI1])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Provedor de SimCard: " ,
                        status = parameterValues[ConstsCommSvc.GET_PARAM_PHONE_PROVIDER_NAME] )

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Versão do Serviço: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_SERVICE_VERSION])
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Versão da Interface do Usuário: ",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_USER_INTERFACE_VERSION])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Serial do Conversor WIFI:",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_WIFI_CONVERTER_SERIAL_NUMBER])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Versão do Firmware do Conversor WIFI",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_WIFI_SERIAL_DEV_FIRMWARE_VERSION])
                }
            }
            item {
                DropdownCard(title = "Relatório de Dispositivo Externo") {
                    ModelRow(
                        title = "Status da ignição:" ,
                        status = ParameterHandler.convertIgnition(parameterValues[ConstsCommSvc.GET_PARAM_IGNITION_STATUS]))
                }
            }
            item {
                DropdownCard(title = "Habilita/Desabilita VPN e WIFI") {
                    Column {
                        SwitchParameter(
                            title = "Vpn Status: " ,
                            paramText = vpnDisableCommText,
                            isChecked = enabledVpnDisableCommText,
                            onTextChange = { newCheckedState ->
                                enabledVpnDisableCommText = newCheckedState
                                vpnDisableCommText = if (!newCheckedState) {
                                    viewModel.setParam(
                                        ConstsCommSvc.SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION,
                                        ParameterValues.DISABLE_VPN.toString()
                                    )
                                    ParameterValues.DISABLE_VPN.toString()
                                }else {
                                    viewModel.setParam(
                                        ConstsCommSvc.SET_PARAM_LOCAL_DISABLE_VPN_COMMUNICATION,
                                        ParameterValues.ENABLE_VPN.toString()
                                    )
                                    ParameterValues.ENABLE_VPN.toString()
                                }
                                                         },
                            textStatus = ParameterHandler.convertVPNStatus(vpnDisableCommText)
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title = "Status da Conexão da VPN",
                            status = ParameterHandler.convertVPNConnectionStatus(ConstsCommSvc.GET_PARAM_VPN_CONNECTION_STATUS) ?: "N/A" )


                        Spacer(modifier = Modifier.height(4.dp))
                        SwitchParameter(
                            title = "Wifi Status:" ,
                            paramText = wifiDisableCommText,
                            isChecked = enabledWifiDisableCommText,
                            onTextChange = { newCheckedState ->
                                enabledWifiDisableCommText = newCheckedState
                                wifiDisableCommText = if (!newCheckedState) {
                                    viewModel.setParam(
                                        ConstsCommSvc.SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION,
                                        ParameterValues.DISABLE_WIFI.toString()
                                    )
                                    ParameterValues.DISABLE_WIFI.toString()
                                }else {
                                    viewModel.setParam(
                                        ConstsCommSvc.SET_PARAM_LOCAL_DISABLE_WIFI_COMMUNICATION,
                                        ParameterValues.ENABLE_WIFI.toString()
                                    )
                                    ParameterValues.ENABLE_WIFI.toString()
                                }
                            },
                            textStatus = ParameterHandler.convertWifiStatus(wifiDisableCommText)
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        ModelRow(
                            title ="IP da rede WIFI:" ,
                            status = parameterValues[ConstsCommSvc.GET_PARAM_WIFI_IP_ADDRESS])
                    }
                }
            }
            item {
                DropdownCard(title = "Lista de Apps permitidos") {
                    ModelRow(
                        title = "",
                        status = parameterValues[ConstsCommSvc.GET_PARAM_PROXY_APPS_ON_WHITE_LIST])
                }
            }
            item {
                DropdownCard(title = "Servidor (AMH)") {
                    ModelRow(
                        title = "Endereço primário do servidor: " ,
                        status = parameterValues[ConstsCommSvc.GET_PARAM_SERVER_IP1])

                    Spacer(modifier = Modifier.height(4.dp))
                    ModelRow(
                        title = "Porta primária do servidor: " ,
                        status = parameterValues[ConstsCommSvc.GET_PARAM_SERVER_PORT1])
                }
            }
        }
    }
}



